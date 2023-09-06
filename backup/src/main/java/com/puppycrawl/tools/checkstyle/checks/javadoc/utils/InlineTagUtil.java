///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.javadoc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppycrawl.tools.checkstyle.api.LineColumn;

/**
 * Tools for extracting inline tags from Javadoc comments.
 *
 */
public final class InlineTagUtil {

    /**
     * Inline tag pattern.
     */
    private static final Pattern INLINE_TAG_PATTERN = Pattern.compile(
            "\\{@(\\p{Alpha}+)\\b(.*?)}", Pattern.DOTALL);

    /** Pattern to recognize leading "*" characters in Javadoc. */
    private static final Pattern JAVADOC_PREFIX_PATTERN = Pattern.compile(
        "^\\s*\\*", Pattern.MULTILINE);

    /** Pattern matching whitespace, used by {@link InlineTagUtil#collapseWhitespace(String)}. */
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    /** Pattern matching a newline. */
    private static final Pattern NEWLINE_PATTERN = Pattern.compile("\\n");

    /** Line feed character. */
    private static final char LINE_FEED = '\n';

    /** Carriage return character. */
    private static final char CARRIAGE_RETURN = '\r';

    /** Prevent instantiation. */
    private InlineTagUtil() {
    }

    /**
     * Extract inline Javadoc tags from the given comment.
     *
     * @param lines The Javadoc comment (as lines).
     * @return The extracted inline Javadoc tags.
     * @throws IllegalArgumentException when comment lines contain newlines
     */
    public static List<TagInfo> extractInlineTags(String... lines) {
        for (String line : lines) {
            if (line.indexOf(LINE_FEED) != -1 || line.indexOf(CARRIAGE_RETURN) != -1) {
                throw new IllegalArgumentException("comment lines cannot contain newlines");
            }
        }

        final String commentText = convertLinesToString(lines);
        final Matcher inlineTagMatcher = INLINE_TAG_PATTERN.matcher(commentText);

        final List<TagInfo> tags = new ArrayList<>();

        while (inlineTagMatcher.find()) {
            final String tagName = inlineTagMatcher.group(1);

            // Remove the leading asterisks (in case the tag spans a line) and collapse
            // the whitespace.
            String matchedTagValue = inlineTagMatcher.group(2);
            matchedTagValue = removeLeadingJavaDoc(matchedTagValue);
            matchedTagValue = collapseWhitespace(matchedTagValue);

            final String tagValue = matchedTagValue;

            final int startIndex = inlineTagMatcher.start(1);
            final LineColumn position = getLineColumnOfIndex(commentText,
                // correct start index offset
                startIndex - 1);

            tags.add(new TagInfo(tagName, tagValue, position));
        }

        return tags;
    }

    /**
     * Convert array of string to single String.
     *
     * @param lines A number of lines, in order.
     * @return The lines, joined together with newlines, as a single string.
     */
    private static String convertLinesToString(String... lines) {
        final StringBuilder builder = new StringBuilder(1024);
        for (String line : lines) {
            builder.append(line);
            builder.append(LINE_FEED);
        }
        return builder.toString();
    }

    /**
     * Get LineColumn from string till index.
     *
     * @param source Source string.
     * @param index An index into the string.
     * @return A position in the source representing what line and column that index appears on.
     */
    private static LineColumn getLineColumnOfIndex(String source, int index) {
        final String precedingText = source.subSequence(0, index).toString();
        final String[] precedingLines = NEWLINE_PATTERN.split(precedingText);
        final String lastLine = precedingLines[precedingLines.length - 1];
        return new LineColumn(precedingLines.length, lastLine.length());
    }

    /**
     * Collapse whitespaces.
     *
     * @param str Source string.
     * @return The given string with all whitespace collapsed.
     */
    private static String collapseWhitespace(String str) {
        final Matcher matcher = WHITESPACE_PATTERN.matcher(str);
        return matcher.replaceAll(" ").trim();
    }

    /**
     * Remove leading JavaDoc.
     *
     * @param source A string to remove leading Javadoc from.
     * @return The given string with leading Javadoc "*" characters from each line removed.
     */
    private static String removeLeadingJavaDoc(String source) {
        final Matcher matcher = JAVADOC_PREFIX_PATTERN.matcher(source);
        return matcher.replaceAll("");
    }

}
