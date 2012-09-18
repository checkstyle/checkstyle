////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.commons.beanutils.ConversionException;

/**
 * <p>
 * A filter that uses nearby comments to suppress audit events.
 * </p>
 * <p>
 * This check is philosophically similar to {@link SuppressionCommentFilter}.
 * Unlike {@link SuppressionCommentFilter}, this filter does not require
 * pairs of comments.  This check may be used to suppress warnings in the
 * current line:
 * <pre>
 *    offendingLine(for, whatever, reason); // SUPPRESS ParameterNumberCheck
 * </pre>
 * or it may be configured to span multiple lines, either forward:
 * <pre>
 *    // PERMIT MultipleVariableDeclarations NEXT 3 LINES
 *    double x1 = 1.0, y1 = 0.0, z1 = 0.0;
 *    double x2 = 0.0, y2 = 1.0, z2 = 0.0;
 *    double x3 = 0.0, y3 = 0.0, z3 = 1.0;
 * </pre>
 * or reverse:
 * <pre>
 *   try {
 *     thirdPartyLibrary.method();
 *   } catch (RuntimeException e) {
 *     // ALLOW ILLEGAL CATCH BECAUSE third party API wraps everything
 *     // in RuntimeExceptions.
 *     ...
 *   }
 * </pre>
 * </p>
 * <p>
 * See {@link SuppressionCommentFilter} for usage notes.
 * </p>
 *
 * @author Mick Killianey
 */
public class SuppressWithNearbyCommentFilter
    extends AutomaticBean
    implements Filter
{
    /**
     * A Tag holds a suppression comment and its location.
     */
    public class Tag implements Comparable<Tag>
    {
        /** The text of the tag. */
        private final String mText;

        /** The first line where warnings may be suppressed. */
        private int mFirstLine;

        /** The last line where warnings may be suppressed. */
        private int mLastLine;

        /** The parsed check regexp, expanded for the text of this tag. */
        private Pattern mTagCheckRegexp;

        /** The parsed message regexp, expanded for the text of this tag. */
        private Pattern mTagMessageRegexp;

        /**
         * Constructs a tag.
         * @param aText the text of the suppression.
         * @param aLine the line number.
         * @throws ConversionException if unable to parse expanded aText.
         * on.
         */
        public Tag(String aText, int aLine)
            throws ConversionException
        {
            mText = aText;

            mTagCheckRegexp = mCheckRegexp;
            //Expand regexp for check and message
            //Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                format = expandFromComment(aText, mCheckFormat, mCommentRegexp);
                mTagCheckRegexp = Pattern.compile(format);
                if (mMessageFormat != null) {
                    format = expandFromComment(
                         aText, mMessageFormat, mCommentRegexp);
                    mTagMessageRegexp = Pattern.compile(format);
                }
                int influence = 0;
                if (mInfluenceFormat != null) {
                    format = expandFromComment(
                        aText, mInfluenceFormat, mCommentRegexp);
                    try {
                        if (format.startsWith("+")) {
                            format = format.substring(1);
                        }
                        influence = Integer.parseInt(format);
                    }
                    catch (final NumberFormatException e) {
                        throw new ConversionException(
                            "unable to parse influence from '" + aText
                                + "' using " + mInfluenceFormat, e);
                    }
                }
                if (influence >= 0) {
                    mFirstLine = aLine;
                    mLastLine = aLine + influence;
                }
                else {
                    mFirstLine = aLine + influence;
                    mLastLine = aLine;
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

        /** @return the line number of the first suppressed line. */
        public int getFirstLine()
        {
            return mFirstLine;
        }

        /** @return the line number of the last suppressed line. */
        public int getLastLine()
        {
            return mLastLine;
        }

        /**
         * Compares the position of this tag in the file
         * with the position of another tag.
         * @param aOther the tag to compare with this one.
         * @return a negative number if this tag is before the other tag,
         * 0 if they are at the same position, and a positive number if this
         * tag is after the other tag.
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(Tag aOther)
        {
            if (mFirstLine == aOther.mFirstLine) {
                return mLastLine - aOther.mLastLine;
            }

            return (mFirstLine - aOther.mFirstLine);
        }

        /**
         * Determines whether the source of an audit event
         * matches the text of this tag.
         * @param aEvent the <code>AuditEvent</code> to check.
         * @return true if the source of aEvent matches the text of this tag.
         */
        public boolean isMatch(AuditEvent aEvent)
        {
            final int line = aEvent.getLine();
            if (line < mFirstLine) {
                return false;
            }
            if (line > mLastLine) {
                return false;
            }
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

        /** {@inheritDoc} */
        @Override
        public final String toString()
        {
            return "Tag[lines=[" + getFirstLine() + " to " + getLastLine()
                + "]; text='" + getText() + "']";
        }
    }

    /** Format to turns checkstyle reporting off. */
    private static final String DEFAULT_COMMENT_FORMAT =
        "SUPPRESS CHECKSTYLE (\\w+)";

    /** Default regex for checks that should be suppressed. */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

    /** Default regex for messages that should be suppressed. */
    private static final String DEFAULT_MESSAGE_FORMAT = null;

    /** Default regex for lines that should be suppressed. */
    private static final String DEFAULT_INFLUENCE_FORMAT = "0";

    /** Whether to look for trigger in C-style comments. */
    private boolean mCheckC = true;

    /** Whether to look for trigger in C++-style comments. */
    private boolean mCheckCPP = true;

    /** Parsed comment regexp that marks checkstyle suppression region. */
    private Pattern mCommentRegexp;

    /** The comment pattern that triggers suppression. */
    private String mCheckFormat;

    /** The parsed check regexp. */
    private Pattern mCheckRegexp;

    /** The message format to suppress. */
    private String mMessageFormat;

    /** The influence of the suppression comment. */
    private String mInfluenceFormat;


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
    public SuppressWithNearbyCommentFilter()
    {
        if (DEFAULT_COMMENT_FORMAT != null) {
            setCommentFormat(DEFAULT_COMMENT_FORMAT);
        }
        if (DEFAULT_CHECK_FORMAT != null) {
            setCheckFormat(DEFAULT_CHECK_FORMAT);
        }
        if (DEFAULT_MESSAGE_FORMAT != null) {
            setMessageFormat(DEFAULT_MESSAGE_FORMAT);
        }
        if (DEFAULT_INFLUENCE_FORMAT != null) {
            setInfluenceFormat(DEFAULT_INFLUENCE_FORMAT);
        }
    }

    /**
     * Set the format for a comment that turns off reporting.
     * @param aFormat a <code>String</code> value.
     * @throws ConversionException unable to parse aFormat.
     */
    public void setCommentFormat(String aFormat)
        throws ConversionException
    {
        try {
            mCommentRegexp = Utils.getPattern(aFormat);
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
     * Set the format for the influence of this check.
     * @param aFormat a <code>String</code> value
     * @throws ConversionException unable to parse aFormat
     */
    public void setInfluenceFormat(String aFormat)
        throws ConversionException
    {
        // check that aFormat parses
        try {
            Utils.getPattern(aFormat);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + aFormat, e);
        }
        mInfluenceFormat = aFormat;
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
        for (final Iterator<Tag> iter = mTags.iterator(); iter.hasNext();) {
            final Tag tag = iter.next();
            if (tag.isMatch(aEvent)) {
                return false;
            }
        }
        return true;
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
            final Collection<List<TextBlock>> cComments =
                contents.getCComments().values();
            for (final List<TextBlock> element : cComments) {
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
        for (final TextBlock comment : aComments) {
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
        final Matcher matcher = mCommentRegexp.matcher(aText);
        if (matcher.find()) {
            addTag(matcher.group(0), aLine);
        }
    }

    /**
     * Adds a comment suppression <code>Tag</code> to the list of all tags.
     * @param aText the text of the tag.
     * @param aLine the line number of the tag.
     */
    private void addTag(String aText, int aLine)
    {
        final Tag tag = new Tag(aText, aLine);
        mTags.add(tag);
    }
}
