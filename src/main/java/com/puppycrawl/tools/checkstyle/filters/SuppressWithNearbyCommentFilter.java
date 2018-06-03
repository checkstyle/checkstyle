////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.puppycrawl.tools.checkstyle.TreeWalkerAuditEvent;
import com.puppycrawl.tools.checkstyle.TreeWalkerFilter;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

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
 *   } catch (RuntimeException ex) {
 *     // ALLOW ILLEGAL CATCH BECAUSE third party API wraps everything
 *     // in RuntimeExceptions.
 *     ...
 *   }
 * </pre>
 *
 * <p>See {@link SuppressionCommentFilter} for usage notes.
 *
 */
public class SuppressWithNearbyCommentFilter
    extends AutomaticBean
    implements TreeWalkerFilter {

    /** Format to turns checkstyle reporting off. */
    private static final String DEFAULT_COMMENT_FORMAT =
        "SUPPRESS CHECKSTYLE (\\w+)";

    /** Default regex for checks that should be suppressed. */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

    /** Default regex for lines that should be suppressed. */
    private static final String DEFAULT_INFLUENCE_FORMAT = "0";

    /** Tagged comments. */
    private final List<Tag> tags = new ArrayList<>();

    /** Whether to look for trigger in C-style comments. */
    private boolean checkC = true;

    /** Whether to look for trigger in C++-style comments. */
    // -@cs[AbbreviationAsWordInName] We can not change it as,
    // check's property is a part of API (used in configurations).
    private boolean checkCPP = true;

    /** Parsed comment regexp that marks checkstyle suppression region. */
    private Pattern commentFormat = Pattern.compile(DEFAULT_COMMENT_FORMAT);

    /** The comment pattern that triggers suppression. */
    private String checkFormat = DEFAULT_CHECK_FORMAT;

    /** The message format to suppress. */
    private String messageFormat;

    /** The influence of the suppression comment. */
    private String influenceFormat = DEFAULT_INFLUENCE_FORMAT;

    /**
     * References the current FileContents for this filter.
     * Since this is a weak reference to the FileContents, the FileContents
     * can be reclaimed as soon as the strong references in TreeWalker
     * are reassigned to the next FileContents, at which time filtering for
     * the current FileContents is finished.
     */
    private WeakReference<FileContents> fileContentsReference = new WeakReference<>(null);

    /**
     * Set the format for a comment that turns off reporting.
     * @param pattern a pattern.
     */
    public final void setCommentFormat(Pattern pattern) {
        commentFormat = pattern;
    }

    /**
     * Returns FileContents for this filter.
     * @return the FileContents for this filter.
     */
    private FileContents getFileContents() {
        return fileContentsReference.get();
    }

    /**
     * Set the FileContents for this filter.
     * @param fileContents the FileContents for this filter.
     * @noinspection WeakerAccess
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
     * @param checkCpp {@code true} if C++ comments are checked.
     */
    // -@cs[AbbreviationAsWordInName] We can not change it as,
    // check's property is a part of API (used in configurations).
    public void setCheckCPP(boolean checkCpp) {
        checkCPP = checkCpp;
    }

    /**
     * Set whether to look in C comments.
     * @param checkC {@code true} if C comments are checked.
     */
    public void setCheckC(boolean checkC) {
        this.checkC = checkC;
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    @Override
    public boolean accept(TreeWalkerAuditEvent event) {
        boolean accepted = true;

        if (event.getLocalizedMessage() != null) {
            // Lazy update. If the first event for the current file, update file
            // contents and tag suppressions
            final FileContents currentContents = event.getFileContents();

            if (getFileContents() != currentContents) {
                setFileContents(currentContents);
                tagSuppressions();
            }
            if (matchesTag(event)) {
                accepted = false;
            }
        }
        return accepted;
    }

    /**
     * Whether current event matches any tag from {@link #tags}.
     * @param event TreeWalkerAuditEvent to test match on {@link #tags}.
     * @return true if event matches any tag from {@link #tags}, false otherwise.
     */
    private boolean matchesTag(TreeWalkerAuditEvent event) {
        boolean result = false;
        for (final Tag tag : tags) {
            if (tag.isMatch(event)) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Collects all the suppression tags for all comments into a list and
     * sorts the list.
     */
    private void tagSuppressions() {
        tags.clear();
        final FileContents contents = getFileContents();
        if (checkCPP) {
            tagSuppressions(contents.getSingleLineComments().values());
        }
        if (checkC) {
            final Collection<List<TextBlock>> cComments =
                contents.getBlockComments().values();
            cComments.forEach(this::tagSuppressions);
        }
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
        final Matcher matcher = commentFormat.matcher(text);
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
    public static class Tag {

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
         * @throws IllegalArgumentException if unable to parse expanded text.
         */
        public Tag(String text, int line, SuppressWithNearbyCommentFilter filter) {
            this.text = text;

            //Expand regexp for check and message
            //Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                format = CommonUtil.fillTemplateWithStringsByRegexp(
                        filter.checkFormat, text, filter.commentFormat);
                tagCheckRegexp = Pattern.compile(format);
                if (filter.messageFormat == null) {
                    tagMessageRegexp = null;
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.messageFormat, text, filter.commentFormat);
                    tagMessageRegexp = Pattern.compile(format);
                }
                format = CommonUtil.fillTemplateWithStringsByRegexp(
                        filter.influenceFormat, text, filter.commentFormat);

                if (CommonUtil.startsWithChar(format, '+')) {
                    format = format.substring(1);
                }
                final int influence = parseInfluence(format, filter.influenceFormat, text);

                if (influence >= 1) {
                    firstLine = line;
                    lastLine = line + influence;
                }
                else {
                    firstLine = line + influence;
                    lastLine = line;
                }
            }
            catch (final PatternSyntaxException ex) {
                throw new IllegalArgumentException(
                    "unable to parse expanded comment " + format, ex);
            }
        }

        /**
         * Gets influence from suppress filter influence format param.
         *
         * @param format          influence format to parse
         * @param influenceFormat raw influence format
         * @param text            text of the suppression
         * @return parsed influence
         */
        private static int parseInfluence(String format, String influenceFormat, String text) {
            try {
                return Integer.parseInt(format);
            }
            catch (final NumberFormatException ex) {
                throw new IllegalArgumentException("unable to parse influence from '" + text
                        + "' using " + influenceFormat, ex);
            }
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
         * @param event the {@code TreeWalkerAuditEvent} to check.
         * @return true if the source of event matches the text of this tag.
         */
        public boolean isMatch(TreeWalkerAuditEvent event) {
            final int line = event.getLine();
            boolean match = false;

            if (line >= firstLine && line <= lastLine) {
                final Matcher tagMatcher = tagCheckRegexp.matcher(event.getSourceName());

                if (tagMatcher.find()) {
                    match = true;
                }
                else if (tagMessageRegexp == null) {
                    if (event.getModuleId() != null) {
                        final Matcher idMatcher = tagCheckRegexp.matcher(event.getModuleId());
                        match = idMatcher.find();
                    }
                }
                else {
                    final Matcher messageMatcher = tagMessageRegexp.matcher(event.getMessage());
                    match = messageMatcher.find();
                }
            }
            return match;
        }

        @Override
        public String toString() {
            return "Tag[text='" + text + '\''
                    + ", firstLine=" + firstLine
                    + ", lastLine=" + lastLine
                    + ", tagCheckRegexp=" + tagCheckRegexp
                    + ", tagMessageRegexp=" + tagMessageRegexp
                    + ']';
        }

    }

}
