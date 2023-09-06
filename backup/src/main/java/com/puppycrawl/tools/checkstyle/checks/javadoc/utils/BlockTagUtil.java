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
 * Tools for parsing block tags from a Javadoc comment.
 *
 */
public final class BlockTagUtil {

    /** Block tag pattern for a first line. */
    private static final Pattern BLOCK_TAG_PATTERN_FIRST_LINE = Pattern.compile(
        "/\\*{2,}\\s*@(\\p{Alpha}+)\\s");

    /** Block tag pattern. */
    private static final Pattern BLOCK_TAG_PATTERN = Pattern.compile(
        "^\\s*\\**\\s*@(\\p{Alpha}+)\\s");

    /** Closing tag. */
    private static final String JAVADOC_CLOSING_TAG = "*/";

    /** Prevent instantiation. */
    private BlockTagUtil() {
    }

    /**
     * Extract the block tags from a Javadoc comment.
     *
     * @param lines The text of the comment, as separate lines.
     * @return The tags extracted from the block.
     */
    public static List<TagInfo> extractBlockTags(String... lines) {
        final List<TagInfo> tags = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            // Starting lines of a comment have a different first line pattern.
            final boolean isFirstLine = i == 0;
            final Pattern pattern;
            if (isFirstLine) {
                pattern = BLOCK_TAG_PATTERN_FIRST_LINE;
            }
            else {
                pattern = BLOCK_TAG_PATTERN;
            }

            final String line = lines[i];
            final Matcher tagMatcher = pattern.matcher(line);

            if (tagMatcher.find()) {
                final String tagName = tagMatcher.group(1);

                // offset of one for the @ character
                final int colNum = tagMatcher.start(1) - 1;
                final int lineNum = i + 1;

                final String remainder = line.substring(tagMatcher.end(1));
                String tagValue = remainder.trim();

                // Handle the case where we're on the last line of a Javadoc comment.
                if (tagValue.endsWith(JAVADOC_CLOSING_TAG)) {
                    final int endIndex = tagValue.length() - JAVADOC_CLOSING_TAG.length();
                    tagValue = tagValue.substring(0, endIndex).trim();
                }

                final LineColumn position = new LineColumn(lineNum, colNum);
                tags.add(new TagInfo(tagName, tagValue, position));
            }
        }

        return tags;
    }

}
