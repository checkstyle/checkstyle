////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.filters;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.checks.FileContentsHolder;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.beanutils.ConversionException;

/**
 * <p>
 * A filter that uses comments to suppress audit events.
 * </p>
 * <p>
 * Rationale:
 * Sometimes there are legitimate reasons for violating a check.  When
 * this is a matter of the code in question and not personal
 * preference, the best place to override the policy is in the code
 * itself.  Semi-structured comments can be associated with the check.
 * This is sometimes superior to a separate suppressions file, which
 * must be kept up-to-date as the source file is edited.
 * </p>
 * <p>
 * Usage:
 * This check only works in conjunction with the FileContentsHolder module
 * since that module makes the suppression comments in the .java
 * files available <i>sub rosa</i>.
 * </p>
 * @see FileContentsHolder
 * @author Mike McMahon
 * @author Rick Giles
 */
public class SuppressionCommentFilter
    extends AutomaticBean
    implements Filter
{
    /**
     * A Tag holds a suppression comment and its location, and determines
     * whether the supression turns checkstyle reporting on or off.
     * @author Rick Giles
     */
    public class Tag
        implements Comparable<Tag>
    {
        /** The text of the tag. */
        private final String mText;

        /** The line number of the tag. */
        private final int mLine;

        /** The column number of the tag. */
        private final int mColumn;

        /** Determines whether the suppression turns checkstyle reporting on. */
        private final boolean mOn;

        /** The parsed check regexp, expanded for the text of this tag. */
        private Pattern mTagCheckRegexp;

        /** The parsed message regexp, expanded for the text of this tag. */
        private Pattern mTagMessageRegexp;

        /**
         * Constructs a tag.
         * @param aLine the line number.
         * @param aColumn the column number.
         * @param aText the text of the suppression.
         * @param aOn <code>true</code> if the tag turns checkstyle reporting.
         * @throws ConversionException if unable to parse expanded aText.
         * on.
         */
        public Tag(int aLine, int aColumn, String aText, boolean aOn)
            throws ConversionException
        {
            mLine = aLine;
            mColumn = aColumn;
            mText = aText;
            mOn = aOn;

            mTagCheckRegexp = mCheckRegexp;
            //Expand regexp for check and message
            //Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                if (aOn) {
                    format =
                        expandFromComment(aText, mCheckFormat, mOnRegexp);
                    mTagCheckRegexp = Pattern.compile(format);
                    if (mMessageFormat != null) {
                        format =
                            expandFromComment(aText, mMessageFormat, mOnRegexp);
                        mTagMessageRegexp = Pattern.compile(format);
                    }
                }
                else {
                    format =
                        expandFromComment(aText, mCheckFormat, mOffRegexp);
                    mTagCheckRegexp = Pattern.compile(format);
                    if (mMessageFormat != null) {
                        format =
                            expandFromComment(
                                aText,
                                mMessageFormat,
                                mOffRegexp);
                        mTagMessageRegexp = Pattern.compile(format);
                    }
                }
            }
            catch (final PatternSyntaxException e) {
                throw new ConversionException(
                    "unable to parse expanded comment " + format,
                    e);
            }
        }

        /** @return the text of the tag. */
        public String getText()
        {
            return mText;
        }

        /** @return the line number of the tag in the source file. */
        public int getLine()
        {
            return mLine;
        }

        /**
         * Determines the column number of the tag in the source file.
         * Will be 0 for all lines of multiline comment, except the
         * first line.
         * @return the column number of the tag in the source file.
         */
        public int getColumn()
        {
            return mColumn;
        }

        /**
         * Determines whether the suppression turns checkstyle reporting on or
         * off.
         * @return <code>true</code>if the suppression turns reporting on.
         */
        public boolean isOn()
        {
            return mOn;
        }

        /**
         * Compares the position of this tag in the file
         * with the position of another tag.
         * @param aObject the tag to compare with this one.
         * @return a negative number if this tag is before the other tag,
         * 0 if they are at the same position, and a positive number if this
         * tag is after the other tag.
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(Tag aObject)
        {
            if (mLine == aObject.mLine) {
                return mColumn - aObject.mColumn;
            }

            return (mLine - aObject.mLine);
        }

        /**
         * Determines whether the source of an audit event
         * matches the text of this tag.
         * @param aEvent the <code>AuditEvent</code> to check.
         * @return true if the source of aEvent matches the text of this tag.
         */
        public boolean isMatch(AuditEvent aEvent)
        {
            final Matcher tagMatcher =
                mTagCheckRegexp.matcher(aEvent.getSourceName());
            if (tagMatcher.find()) {
                return true;
            }
            if (mTagMessageRegexp != null) {
                final Matcher messageMatcher =
                    mTagMessageRegexp.matcher(aEvent.getMessage());
                return messageMatcher.find();
            }
            return false;
        }

        /**
         * Expand based on a matching comment.
         * @param aComment the comment.
         * @param aString the string to expand.
         * @param aRegexp the parsed expander.
         * @return the expanded string
         */
        private String expandFromComment(
            String aComment,
            String aString,
            Pattern aRegexp)
        {
            final Matcher matcher = aRegexp.matcher(aComment);
            // Match primarily for effect.
            if (!matcher.find()) {
                ///CLOVER:OFF
                return aString;
                ///CLOVER:ON
            }
            String result = aString;
            for (int i = 0; i <= matcher.groupCount(); i++) {
                // $n expands comment match like in Pattern.subst().
                result = result.replaceAll("\\$" + i, matcher.group(i));
            }
            return result;
        }

        @Override
        public final String toString()
        {
            return "Tag[line=" + getLine() + "; col=" + getColumn()
                + "; on=" + isOn() + "; text='" + getText() + "']";
        }
    }

    /** Turns checkstyle reporting off. */
    private static final String DEFAULT_OFF_FORMAT = "CHECKSTYLE\\:OFF";

    /** Turns checkstyle reporting on. */
    private static final String DEFAULT_ON_FORMAT = "CHECKSTYLE\\:ON";

    /** Control all checks */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

    /** Whether to look in comments of the C type. */
    private boolean mCheckC = true;

    /** Whether to look in comments of the C++ type. */
    private boolean mCheckCPP = true;

    /** Parsed comment regexp that turns checkstyle reporting off. */
    private Pattern mOffRegexp;

    /** Parsed comment regexp that turns checkstyle reporting on. */
    private Pattern mOnRegexp;

    /** The check format to suppress. */
    private String mCheckFormat;

    /** The parsed check regexp. */
    private Pattern mCheckRegexp;

    /** The message format to suppress. */
    private String mMessageFormat;

    //TODO: Investigate performance improvement with array
    /** Tagged comments */
    private final List<Tag> mTags = Lists.newArrayList();

    /**
     * References the current FileContents for this filter.
     * Since this is a weak reference to the FileContents, the FileContents
     * can be reclaimed as soon as the strong references in TreeWalker
     * and FileContentsHolder are reassigned to the next FileContents,
     * at which time filtering for the current FileContents is finished.
     */
    private WeakReference<FileContents> mFileContentsReference =
        new WeakReference<FileContents>(null);

    /**
     * Constructs a SuppressionCommentFilter.
     * Initializes comment on, comment off, and check formats
     * to defaults.
     */
    public SuppressionCommentFilter()
    {
        setOnCommentFormat(DEFAULT_ON_FORMAT);
        setOffCommentFormat(DEFAULT_OFF_FORMAT);
        setCheckFormat(DEFAULT_CHECK_FORMAT);
    }

    /**
     * Set the format for a comment that turns off reporting.
     * @param aFormat a <code>String</code> value.
     * @throws ConversionException unable to parse aFormat.
     */
    public void setOffCommentFormat(String aFormat)
        throws ConversionException
    {
        try {
            mOffRegexp = Utils.getPattern(aFormat);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /**
     * Set the format for a comment that turns on reporting.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setOnCommentFormat(String aFormat)
        throws ConversionException
    {
        try {
            mOnRegexp = Utils.getPattern(aFormat);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /** @return the FileContents for this filter. */
    public FileContents getFileContents()
    {
        return mFileContentsReference.get();
    }

    /**
     * Set the FileContents for this filter.
     * @param aFileContents the FileContents for this filter.
     */
    public void setFileContents(FileContents aFileContents)
    {
        mFileContentsReference = new WeakReference<FileContents>(aFileContents);
    }

    /**
     * Set the format for a check.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setCheckFormat(String aFormat)
        throws ConversionException
    {
        try {
            mCheckRegexp = Utils.getPattern(aFormat);
            mCheckFormat = aFormat;
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
    }

    /**
     * Set the format for a message.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setMessageFormat(String aFormat)
        throws ConversionException
    {
        // check that aFormat parses
        try {
            Utils.getPattern(aFormat);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
        mMessageFormat = aFormat;
    }


    /**
     * Set whether to look in C++ comments.
     * @param aCheckCPP <code>true</code> if C++ comments are checked.
     */
    public void setCheckCPP(boolean aCheckCPP)
    {
        mCheckCPP = aCheckCPP;
    }

    /**
     * Set whether to look in C comments.
     * @param aCheckC <code>true</code> if C comments are checked.
     */
    public void setCheckC(boolean aCheckC)
    {
        mCheckC = aCheckC;
    }

    /** {@inheritDoc} */
    public boolean accept(AuditEvent aEvent)
    {
        if (aEvent.getLocalizedMessage() == null) {
            return true;        // A special event.
        }

        // Lazy update. If the first event for the current file, update file
        // contents and tag suppressions
        final FileContents currentContents = FileContentsHolder.getContents();
        if (currentContents == null) {
            // we have no contents, so we can not filter.
            // TODO: perhaps we should notify user somehow?
            return true;
        }
        if (getFileContents() != currentContents) {
            setFileContents(currentContents);
            tagSuppressions();
        }
        final Tag matchTag = findNearestMatch(aEvent);
        if ((matchTag != null) && !matchTag.isOn()) {
            return false;
        }
        return true;
    }

    /**
     * Finds the nearest comment text tag that matches an audit event.
     * The nearest tag is before the line and column of the event.
     * @param aEvent the <code>AuditEvent</code> to match.
     * @return The <code>Tag</code> nearest aEvent.
     */
    private Tag findNearestMatch(AuditEvent aEvent)
    {
        Tag result = null;
        // TODO: try binary search if sequential search becomes a performance
        // problem.
        for (Tag tag : mTags) {
            if ((tag.getLine() > aEvent.getLine())
                || ((tag.getLine() == aEvent.getLine())
                    && (tag.getColumn() > aEvent.getColumn())))
            {
                break;
            }
            if (tag.isMatch(aEvent)) {
                result = tag;
            }
        };
        return result;
    }

    /**
     * Collects all the suppression tags for all comments into a list and
     * sorts the list.
     */
    private void tagSuppressions()
    {
        mTags.clear();
        final FileContents contents = getFileContents();
        if (mCheckCPP) {
            tagSuppressions(contents.getCppComments().values());
        }
        if (mCheckC) {
            final Collection<List<TextBlock>> cComments = contents
                    .getCComments().values();
            for (List<TextBlock> element : cComments) {
                tagSuppressions(element);
            }
        }
        Collections.sort(mTags);
    }

    /**
     * Appends the suppressions in a collection of comments to the full
     * set of suppression tags.
     * @param aComments the set of comments.
     */
    private void tagSuppressions(Collection<TextBlock> aComments)
    {
        for (TextBlock comment : aComments) {
            final int startLineNo = comment.getStartLineNo();
            final String[] text = comment.getText();
            tagCommentLine(text[0], startLineNo, comment.getStartColNo());
            for (int i = 1; i < text.length; i++) {
                tagCommentLine(text[i], startLineNo + i, 0);
            }
        }
    }

    /**
     * Tags a string if it matches the format for turning
     * checkstyle reporting on or the format for turning reporting off.
     * @param aText the string to tag.
     * @param aLine the line number of aText.
     * @param aColumn the column number of aText.
     */
    private void tagCommentLine(String aText, int aLine, int aColumn)
    {
        final Matcher offMatcher = mOffRegexp.matcher(aText);
        if (offMatcher.find()) {
            addTag(offMatcher.group(0), aLine, aColumn, false);
        }
        else {
            final Matcher onMatcher = mOnRegexp.matcher(aText);
            if (onMatcher.find()) {
                addTag(onMatcher.group(0), aLine, aColumn, true);
            }
        }
    }

    /**
     * Adds a <code>Tag</code> to the list of all tags.
     * @param aText the text of the tag.
     * @param aLine the line number of the tag.
     * @param aColumn the column number of the tag.
     * @param aOn <code>true</code> if the tag turns checkstyle reporting on.
     */
    private void addTag(String aText, int aLine, int aColumn, boolean aOn)
    {
        final Tag tag = new Tag(aLine, aColumn, aText, aOn);
        mTags.add(tag);
    }
}
