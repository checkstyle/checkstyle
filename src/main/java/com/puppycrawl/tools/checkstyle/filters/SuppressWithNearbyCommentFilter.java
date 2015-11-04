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

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.ConversionException;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.Filter;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.checks.FileContentsHolder;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * A filter that uses nearby comments to suppress audit events.
 * </p>
 *
 * <p>This check is philosophically similar to {@link SuppressionCommentFilter}.
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
 * <p>See {@link SuppressionCommentFilter} for usage notes.
 *
 * @author Mick Killianey
 */
public class SuppressWithNearbyCommentFilter
    extends AutomaticBean
    implements Filter {

    /** Format to turns checkstyle reporting off. */
    private static final String DEFAULT_COMMENT_FORMAT =
        "SUPPRESS CHECKSTYLE (\\w+)";

    /** Default regex for checks that should be suppressed. */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

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

    /** The message format to suppress. */
    private String messageFormat;

    /** The influence of the suppression comment. */
    private String influenceFormat;

    /** Tagged comments. */
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
    public SuppressWithNearbyCommentFilter() {
        setCommentFormat(DEFAULT_COMMENT_FORMAT);
        checkFormat = DEFAULT_CHECK_FORMAT;
        influenceFormat = DEFAULT_INFLUENCE_FORMAT;
    }

    /**
     * Set the format for a comment that turns off reporting.
     * @param format a {@code String} value.
     * @throws ConversionException if unable to create Pattern object.
     */
    public final void setCommentFormat(String format) {
        commentRegexp = CommonUtils.createPattern(format);
    }

    /**
     * @return the FileContents for this filter.
     */
    public FileContents getFileContents() {
        return fileContentsReference.get();
    }

    /**
     * Set the FileContents for this filter.
     * @param fileContents the FileContents for this filter.
     */
    public void setFileContents(FileContents fileContents) {
        fileContentsReference = new WeakReference<>(fileContents);
    }

    /**
     * Set the format for a check.
     * @param format a {@code String} value
     */
    public final void setCheckFormat(String format) {
        checkFormat = format;
    }

    /**
     * Set the format for a message.
     * @param format a {@code String} value
     */
    public void setMessageFormat(String format) {
        messageFormat = format;
    }

    /**
     * Set the format for the influence of this check.
     * @param format a {@code String} value
     */
    public final void setInfluenceFormat(String format) {
        influenceFormat = format;
    }

    /**
     * Set whether to look in C++ comments.
     * @param checkCPP {@code true} if C++ comments are checked.
     */
    public void setCheckCPP(boolean checkCPP) {
        this.checkCPP = checkCPP;
    }

    /**
     * Set whether to look in C comments.
     * @param checkC {@code true} if C comments are checked.
     */
    public void setCheckC(boolean checkC) {
        this.checkC = checkC;
    }

    @Override
    public boolean accept(AuditEvent event) {
        boolean accepted = true;

        if (event.getLocalizedMessage() != null) {
            // Lazy update. If the first event for the current file, update file
            // contents and tag suppressions
            final FileContents currentContents = FileContentsHolder.getContents();

            if (currentContents != null) {
                if (getFileContents() != currentContents) {
                    setFileContents(currentContents);
                    tagSuppressions();
                }
                if (matchesTag(event)) {
                    accepted = false;
                }
            }
        }
        return accepted;
    }

    /**
     * Whether current event matches any tag from {@link #tags}.
     * @param event AuditEvent to test match on {@link #tags}.
     * @return true if event matches any tag from {@link #tags}, false otherwise.
     */
    private boolean matchesTag(AuditEvent event) {
        for (final Tag tag : tags) {
            if (tag.isMatch(event)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Collects all the suppression tags for all comments into a list and
     * sorts the list.
     */
    private void tagSuppressions() {
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
    private void tagSuppressions(Collection<TextBlock> comments) {
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
    private void tagCommentLine(String text, int line) {
        final Matcher matcher = commentRegexp.matcher(text);
        if (matcher.find()) {
            addTag(matcher.group(0), line);
        }
    }

    /**
     * Adds a comment suppression {@code Tag} to the list of all tags.
     * @param text the text of the tag.
     * @param line the line number of the tag.
     */
    private void addTag(String text, int line) {
        final Tag tag = new Tag(text, line, this);
        tags.add(tag);
    }

    /**
     * A Tag holds a suppression comment and its location.
     */
    public static class Tag implements Comparable<Tag> {
        /** The text of the tag. */
        private final String text;

        /** The first line where warnings may be suppressed. */
        private final int firstLine;

        /** The last line where warnings may be suppressed. */
        private final int lastLine;

        /** The parsed check regexp, expanded for the text of this tag. */
        private final Pattern tagCheckRegexp;

        /** The parsed message regexp, expanded for the text of this tag. */
        private final Pattern tagMessageRegexp;

        /**
         * Constructs a tag.
         * @param text the text of the suppression.
         * @param line the line number.
         * @param filter the {@code SuppressWithNearbyCommentFilter} with the context
         * @throws ConversionException if unable to parse expanded text.
         */
        public Tag(String text, int line, SuppressWithNearbyCommentFilter filter) {
            this.text = text;

            //Expand regexp for check and message
            //Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                format = CommonUtils.fillTemplateWithStringsByRegexp(
                        filter.checkFormat, text, filter.commentRegexp);
                tagCheckRegexp = Pattern.compile(format);
                if (filter.messageFormat == null) {
                    tagMessageRegexp = null;
                }
                else {
                    format = CommonUtils.fillTemplateWithStringsByRegexp(
                            filter.messageFormat, text, filter.commentRegexp);
                    tagMessageRegexp = Pattern.compile(format);
                }
                format = CommonUtils.fillTemplateWithStringsByRegexp(
                        filter.influenceFormat, text, filter.commentRegexp);
                final int influence;
                try {
                    if (CommonUtils.startsWithChar(format, '+')) {
                        format = format.substring(1);
                    }
                    influence = Integer.parseInt(format);
                }
                catch (final NumberFormatException e) {
                    throw new ConversionException(
                        "unable to parse influence from '" + text
                            + "' using " + filter.influenceFormat, e);
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

        /**
         * Compares the position of this tag in the file
         * with the position of another tag.
         * @param other the tag to compare with this one.
         * @return a negative number if this tag is before the other tag,
         *     0 if they are at the same position, and a positive number if this
         *     tag is after the other tag.
         */
        @Override
        public int compareTo(Tag other) {
            if (firstLine == other.firstLine) {
                return Integer.compare(lastLine, other.lastLine);
            }

            return Integer.compare(firstLine, other.firstLine);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            final Tag tag = (Tag) other;
            return Objects.equals(firstLine, tag.firstLine)
                    && Objects.equals(lastLine, tag.lastLine)
                    && Objects.equals(text, tag.text)
                    && Objects.equals(tagCheckRegexp, tag.tagCheckRegexp)
                    && Objects.equals(tagMessageRegexp, tag.tagMessageRegexp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text, firstLine, lastLine, tagCheckRegexp, tagMessageRegexp);
        }

        /**
         * Determines whether the source of an audit event
         * matches the text of this tag.
         * @param event the {@code AuditEvent} to check.
         * @return true if the source of event matches the text of this tag.
         */
        public boolean isMatch(AuditEvent event) {
            final int line = event.getLine();
            boolean match = false;

            if (line >= firstLine && line <= lastLine) {
                final Matcher tagMatcher = tagCheckRegexp.matcher(event.getSourceName());

                if (tagMatcher.find()) {
                    match = true;
                }
                else if (tagMessageRegexp != null) {
                    final Matcher messageMatcher = tagMessageRegexp.matcher(event.getMessage());
                    match = messageMatcher.find();
                }
            }
            return match;
        }

        @Override
        public final String toString() {
            return "Tag[lines=[" + firstLine + " to " + lastLine
                + "]; text='" + text + "']";
        }
    }
}
