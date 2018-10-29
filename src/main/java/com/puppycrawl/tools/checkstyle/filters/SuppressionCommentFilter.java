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
import java.util.Collections;
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
 */
public class SuppressionCommentFilter
    extends AutomaticBean
    implements TreeWalkerFilter {

    /**
     * Enum to be used for switching checkstyle reporting for tags.
     */
    public enum TagType {

        /**
         * Switch reporting on.
         */
        ON,
        /**
         * Switch reporting off.
         */
        OFF,

    }

    /** Turns checkstyle reporting off. */
    private static final String DEFAULT_OFF_FORMAT = "CHECKSTYLE:OFF";

    /** Turns checkstyle reporting on. */
    private static final String DEFAULT_ON_FORMAT = "CHECKSTYLE:ON";

    /** Control all checks. */
    private static final String DEFAULT_CHECK_FORMAT = ".*";

    /** Tagged comments. */
    private final List<Tag> tags = new ArrayList<>();

    /** Whether to look in comments of the C type. */
    private boolean checkC = true;

    /** Whether to look in comments of the C++ type. */
    // -@cs[AbbreviationAsWordInName] we can not change it as,
    // Check property is a part of API (used in configurations)
    private boolean checkCPP = true;

    /** Parsed comment regexp that turns checkstyle reporting off. */
    private Pattern offCommentFormat = Pattern.compile(DEFAULT_OFF_FORMAT);

    /** Parsed comment regexp that turns checkstyle reporting on. */
    private Pattern onCommentFormat = Pattern.compile(DEFAULT_ON_FORMAT);

    /** The check format to suppress. */
    private String checkFormat = DEFAULT_CHECK_FORMAT;

    /** The message format to suppress. */
    private String messageFormat;

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
    public final void setOffCommentFormat(Pattern pattern) {
        offCommentFormat = pattern;
    }

    /**
     * Set the format for a comment that turns on reporting.
     * @param pattern a pattern.
     */
    public final void setOnCommentFormat(Pattern pattern) {
        onCommentFormat = pattern;
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
            final Tag matchTag = findNearestMatch(event);
            accepted = matchTag == null || matchTag.getTagType() == TagType.ON;
        }
        return accepted;
    }

    /**
     * Finds the nearest comment text tag that matches an audit event.
     * The nearest tag is before the line and column of the event.
     * @param event the {@code TreeWalkerAuditEvent} to match.
     * @return The {@code Tag} nearest event.
     */
    private Tag findNearestMatch(TreeWalkerAuditEvent event) {
        Tag result = null;
        for (Tag tag : tags) {
            if (tag.getLine() > event.getLine()
                || tag.getLine() == event.getLine()
                    && tag.getColumn() > event.getColumn()) {
                break;
            }
            if (tag.isMatch(event)) {
                result = tag;
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
            final Collection<List<TextBlock>> cComments = contents
                    .getBlockComments().values();
            cComments.forEach(this::tagSuppressions);
        }
        Collections.sort(tags);
    }

    /**
     * Appends the suppressions in a collection of comments to the full
     * set of suppression tags.
     * @param comments the set of comments.
     */
    private void tagSuppressions(Collection<TextBlock> comments) {
        for (TextBlock comment : comments) {
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
     * @param text the string to tag.
     * @param line the line number of text.
     * @param column the column number of text.
     */
    private void tagCommentLine(String text, int line, int column) {
        final Matcher offMatcher = offCommentFormat.matcher(text);
        if (offMatcher.find()) {
            addTag(offMatcher.group(0), line, column, TagType.OFF);
        }
        else {
            final Matcher onMatcher = onCommentFormat.matcher(text);
            if (onMatcher.find()) {
                addTag(onMatcher.group(0), line, column, TagType.ON);
            }
        }
    }

    /**
     * Adds a {@code Tag} to the list of all tags.
     * @param text the text of the tag.
     * @param line the line number of the tag.
     * @param column the column number of the tag.
     * @param reportingOn {@code true} if the tag turns checkstyle reporting on.
     */
    private void addTag(String text, int line, int column, TagType reportingOn) {
        final Tag tag = new Tag(line, column, text, reportingOn, this);
        tags.add(tag);
    }

    /**
     * A Tag holds a suppression comment and its location, and determines
     * whether the suppression turns checkstyle reporting on or off.
     */
    public static class Tag
        implements Comparable<Tag> {

        /** The text of the tag. */
        private final String text;

        /** The line number of the tag. */
        private final int line;

        /** The column number of the tag. */
        private final int column;

        /** Determines whether the suppression turns checkstyle reporting on. */
        private final TagType tagType;

        /** The parsed check regexp, expanded for the text of this tag. */
        private final Pattern tagCheckRegexp;

        /** The parsed message regexp, expanded for the text of this tag. */
        private final Pattern tagMessageRegexp;

        /**
         * Constructs a tag.
         * @param line the line number.
         * @param column the column number.
         * @param text the text of the suppression.
         * @param tagType {@code ON} if the tag turns checkstyle reporting.
         * @param filter the {@code SuppressionCommentFilter} with the context
         * @throws IllegalArgumentException if unable to parse expanded text.
         */
        public Tag(int line, int column, String text, TagType tagType,
                   SuppressionCommentFilter filter) {
            this.line = line;
            this.column = column;
            this.text = text;
            this.tagType = tagType;

            //Expand regexp for check and message
            //Does not intern Patterns with Utils.getPattern()
            String format = "";
            try {
                if (this.tagType == TagType.ON) {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.checkFormat, text, filter.onCommentFormat);
                    tagCheckRegexp = Pattern.compile(format);
                    if (filter.messageFormat == null) {
                        tagMessageRegexp = null;
                    }
                    else {
                        format = CommonUtil.fillTemplateWithStringsByRegexp(
                                filter.messageFormat, text, filter.onCommentFormat);
                        tagMessageRegexp = Pattern.compile(format);
                    }
                }
                else {
                    format = CommonUtil.fillTemplateWithStringsByRegexp(
                            filter.checkFormat, text, filter.offCommentFormat);
                    tagCheckRegexp = Pattern.compile(format);
                    if (filter.messageFormat == null) {
                        tagMessageRegexp = null;
                    }
                    else {
                        format = CommonUtil.fillTemplateWithStringsByRegexp(
                                filter.messageFormat, text, filter.offCommentFormat);
                        tagMessageRegexp = Pattern.compile(format);
                    }
                }
            }
            catch (final PatternSyntaxException ex) {
                throw new IllegalArgumentException(
                    "unable to parse expanded comment " + format, ex);
            }
        }

        /**
         * Returns line number of the tag in the source file.
         * @return the line number of the tag in the source file.
         */
        public int getLine() {
            return line;
        }

        /**
         * Determines the column number of the tag in the source file.
         * Will be 0 for all lines of multiline comment, except the
         * first line.
         * @return the column number of the tag in the source file.
         */
        public int getColumn() {
            return column;
        }

        /**
         * Determines whether the suppression turns checkstyle reporting on or
         * off.
         * @return {@code ON} if the suppression turns reporting on.
         */
        public TagType getTagType() {
            return tagType;
        }

        /**
         * Compares the position of this tag in the file
         * with the position of another tag.
         * @param object the tag to compare with this one.
         * @return a negative number if this tag is before the other tag,
         *     0 if they are at the same position, and a positive number if this
         *     tag is after the other tag.
         */
        @Override
        public int compareTo(Tag object) {
            final int result;
            if (line == object.line) {
                result = Integer.compare(column, object.column);
            }
            else {
                result = Integer.compare(line, object.line);
            }
            return result;
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
            return Objects.equals(line, tag.line)
                    && Objects.equals(column, tag.column)
                    && Objects.equals(tagType, tag.tagType)
                    && Objects.equals(text, tag.text)
                    && Objects.equals(tagCheckRegexp, tag.tagCheckRegexp)
                    && Objects.equals(tagMessageRegexp, tag.tagMessageRegexp);
        }

        @Override
        public int hashCode() {
            return Objects.hash(text, line, column, tagType, tagCheckRegexp, tagMessageRegexp);
        }

        /**
         * Determines whether the source of an audit event
         * matches the text of this tag.
         * @param event the {@code TreeWalkerAuditEvent} to check.
         * @return true if the source of event matches the text of this tag.
         */
        public boolean isMatch(TreeWalkerAuditEvent event) {
            boolean match = false;
            final Matcher tagMatcher = tagCheckRegexp.matcher(event.getSourceName());
            if (tagMatcher.find()) {
                if (tagMessageRegexp == null) {
                    match = true;
                }
                else {
                    final Matcher messageMatcher = tagMessageRegexp.matcher(event.getMessage());
                    match = messageMatcher.find();
                }
            }
            else if (event.getModuleId() != null) {
                final Matcher idMatcher = tagCheckRegexp.matcher(event.getModuleId());
                match = idMatcher.find();
            }
            return match;
        }

        @Override
        public String toString() {
            return "Tag[text='" + text + '\''
                    + ", line=" + line
                    + ", column=" + column
                    + ", type=" + tagType
                    + ", tagCheckRegexp=" + tagCheckRegexp
                    + ", tagMessageRegexp=" + tagMessageRegexp + ']';
        }

    }

}
