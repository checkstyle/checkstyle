////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.Utils;
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
 *
 * <p>
 * See {@link SuppressionCommentFilter} for usage notes.
 *
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
        private final String text;

        /** The first line where warnings may be suppressed. */
        private int firstLine;

        /** The last line where warnings may be suppressed. */
        private int lastLine;

        /** The parsed check regexp, expanded for the text of this tag. */
        private Pattern tagCheckRegexp;

        /** The parsed message regexp, expanded for the text of this tag. */
        private Pattern tagMessageRegexp;

        /**
         * Constructs a tag.
         * @param text the text of the suppression.
         * @param line the line number.
         * @throws ConversionException if unable to parse expanded text.
         * on.
         */
        public Tag(String text, int line)
            throws ConversionException
        {
            this.text = text;

            tagCheckRegexp = checkRegexp;
            //Expand regexp for check and message
            //Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                format = expandFrocomment(text, checkFormat, commentRegexp);
                tagCheckRegexp = Pattern.compile(format);
                if (messageFormat != null) {
                    format = expandFrocomment(
                         text, messageFormat, commentRegexp);
                    tagMessageRegexp = Pattern.compile(format);
                }
                int influence = 0;
                if (influenceFormat != null) {
                    format = expandFrocomment(
                        text, influenceFormat, commentRegexp);
                    try {
                        if (format.startsWith("+")) {
                            format = format.substring(1);
                        }
                        influence = Integer.parseInt(format);
                    }
                    catch (final NumberFormatException e) {
                        throw new ConversionException(
                            "unable to parse influence from '" + text
                                + "' using " + influenceFormat, e);
                    }
                }
                if (influence >= 0) {
                    firstLine = line;
                    lastLine = line + influence;
                }
                else {
                    firstLine = line + influence;
                    lastLine = line;
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
            return text;
        }

        /** @return the line number of the first suppressed line. */
        public int getFirstLine()
        {
            return firstLine;
        }

        /** @return the line number of the last suppressed line. */
        public int getLastLine()
        {
            return lastLine;
        }

        /**
         * Compares the position of this tag in the file
         * with the position of another tag.
         * @param other the tag to compare with this one.
         * @return a negative number if this tag is before the other tag,
         * 0 if they are at the same position, and a positive number if this
         * tag is after the other tag.
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        @Override
        public int compareTo(Tag other)
        {
            if (firstLine == other.firstLine) {
                return lastLine - other.lastLine;
            }

            return firstLine - other.firstLine;
        }

        /**
         * Determines whether the source of an audit event
         * matches the text of this tag.
         * @param event the <code>AuditEvent</code> to check.
         * @return true if the source of event matches the text of this tag.
         */
        public boolean isMatch(AuditEvent event)
        {
            final int line = event.getLine();
            if (line < firstLine) {
                return false;
            }
            if (line > lastLine) {
                return false;
            }
            final Matcher tagMatcher =
                tagCheckRegexp.matcher(event.getSourceName());
            if (tagMatcher.find()) {
                return true;
            }
            if (tagMessageRegexp != null) {
                final Matcher messageMatcher =
                    tagMessageRegexp.matcher(event.getMessage());
                return messageMatcher.find();
            }
            return false;
        }

        /**
         * Expand based on a matching comment.
         * @param comment the comment.
         * @param string the string to expand.
         * @param regexp the parsed expander.
         * @return the expanded string
         */
        private String expandFrocomment(
            String comment,
            String string,
            Pattern regexp)
        {
            final Matcher matcher = regexp.matcher(comment);
            // Match primarily for effect.
            if (!matcher.find()) {
                ///CLOVER:OFF
                return string;
                ///CLOVER:ON
            }
            String result = string;
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
    private boolean checkC = true;

    /** Whether to look for trigger in C++-style comments. */
    private boolean checkCPP = true;

    /** Parsed comment regexp that marks checkstyle suppression region. */
    private Pattern commentRegexp;

    /** The comment pattern that triggers suppression. */
    private String checkFormat;

    /** The parsed check regexp. */
    private Pattern checkRegexp;

    /** The message format to suppress. */
    private String messageFormat;

    /** The influence of the suppression comment. */
    private String influenceFormat;


    //TODO: Investigate performance improvement with array
    /** Tagged comments */
    private final List<Tag> tags = Lists.newArrayList();

    /**
     * References the current FileContents for this filter.
     * Since this is a weak reference to the FileContents, the FileContents
     * can be reclaimed as soon as the strong references in TreeWalker
     * and FileContentsHolder are reassigned to the next FileContents,
     * at which time filtering for the current FileContents is finished.
     */
    private WeakReference<FileContents> fileContentsReference = new WeakReference<>(null);

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
     * @param format a <code>String</code> value.
     * @throws ConversionException unable to parse format.
     */
    public void setCommentFormat(String format)
        throws ConversionException
    {
        try {
            commentRegexp = Utils.getPattern(format);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + format, e);
        }
    }

    /** @return the FileContents for this filter. */
    public FileContents getFileContents()
    {
        return fileContentsReference.get();
    }

    /**
     * Set the FileContents for this filter.
     * @param fileContents the FileContents for this filter.
     */
    public void setFileContents(FileContents fileContents)
    {
        fileContentsReference = new WeakReference<>(fileContents);
    }

    /**
     * Set the format for a check.
     * @param format a <code>String</code> value
     * @throws ConversionException unable to parse format
     */
    public void setCheckFormat(String format)
        throws ConversionException
    {
        try {
            checkRegexp = Utils.getPattern(format);
            checkFormat = format;
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + format, e);
        }
    }

    /**
     * Set the format for a message.
     * @param format a <code>String</code> value
     * @throws ConversionException unable to parse format
     */
    public void setMessageFormat(String format)
        throws ConversionException
    {
        // check that format parses
        try {
            Utils.getPattern(format);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + format, e);
        }
        messageFormat = format;
    }

    /**
     * Set the format for the influence of this check.
     * @param format a <code>String</code> value
     * @throws ConversionException unable to parse format
     */
    public void setInfluenceFormat(String format)
        throws ConversionException
    {
        // check that format parses
        try {
            Utils.getPattern(format);
        }
        catch (final PatternSyntaxException e) {
            throw new ConversionException("unable to parse " + format, e);
        }
        influenceFormat = format;
    }


    /**
     * Set whether to look in C++ comments.
     * @param checkCPP <code>true</code> if C++ comments are checked.
     */
    public void setCheckCPP(boolean checkCPP)
    {
        this.checkCPP = checkCPP;
    }

    /**
     * Set whether to look in C comments.
     * @param checkC <code>true</code> if C comments are checked.
     */
    public void setCheckC(boolean checkC)
    {
        this.checkC = checkC;
    }

    /** {@inheritDoc} */
    @Override
    public boolean accept(AuditEvent event)
    {
        if (event.getLocalizedMessage() == null) {
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
        for (final Iterator<Tag> iter = tags.iterator(); iter.hasNext();) {
            final Tag tag = iter.next();
            if (tag.isMatch(event)) {
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
        tags.clear();
        final FileContents contents = getFileContents();
        if (checkCPP) {
            tagSuppressions(contents.getCppComments().values());
        }
        if (checkC) {
            final Collection<List<TextBlock>> cComments =
                contents.getCComments().values();
            for (final List<TextBlock> element : cComments) {
                tagSuppressions(element);
            }
        }
        Collections.sort(tags);
    }

    /**
     * Appends the suppressions in a collection of comments to the full
     * set of suppression tags.
     * @param comments the set of comments.
     */
    private void tagSuppressions(Collection<TextBlock> comments)
    {
        for (final TextBlock comment : comments) {
            final int startLineNo = comment.getStartLineNo();
            final String[] text = comment.getText();
            tagCommentLine(text[0], startLineNo);
            for (int i = 1; i < text.length; i++) {
                tagCommentLine(text[i], startLineNo + i);
            }
        }
    }

    /**
     * Tags a string if it matches the format for turning
     * checkstyle reporting on or the format for turning reporting off.
     * @param text the string to tag.
     * @param line the line number of text.
     */
    private void tagCommentLine(String text, int line)
    {
        final Matcher matcher = commentRegexp.matcher(text);
        if (matcher.find()) {
            addTag(matcher.group(0), line);
        }
    }

    /**
     * Adds a comment suppression <code>Tag</code> to the list of all tags.
     * @param text the text of the tag.
     * @param line the line number of the tag.
     */
    private void addTag(String text, int line)
    {
        final Tag tag = new Tag(text, line);
        tags.add(tag);
    }
}
