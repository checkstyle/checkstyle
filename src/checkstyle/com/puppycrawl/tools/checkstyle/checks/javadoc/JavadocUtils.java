////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.javadoc;

import com.google.common.collect.Lists;
import com.puppycrawl.tools.checkstyle.api.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.api.TextBlock;
import com.puppycrawl.tools.checkstyle.api.Utils;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains utility methods for working with Javadoc.
 */
public final class JavadocUtils
{
    ///CLOVER:OFF
    /** prevent instantiation */
    private JavadocUtils()
    {
    }
    ///CLOVER:ON

    /**
     * Gets validTags from a given piece of Javadoc.
     * @param aCmt the Javadoc comment to process.
     * @param tagType the type of validTags we're interested in
     * @return all standalone validTags from the given javadoc.
     */
    public static JavadocTags getJavadocTags(TextBlock aCmt,
                                             JavadocTagType tagType)
    {
        final String[] text = aCmt.getText();
        final List<JavadocTag> tags = Lists.newArrayList();
        final List<InvalidJavadocTag> invalidTags = Lists.newArrayList();
        Pattern blockTagPattern = Utils.getPattern("/\\*{2,}\\s*@(\\p{Alpha}+)\\s");
        for (int i = 0; i < text.length; i++) {
            final String s = text[i];
            final Matcher blockTagMatcher = blockTagPattern.matcher(s);
            if ((tagType.equals(JavadocTagType.ALL) ||
                  tagType.equals(JavadocTagType.BLOCK))
                && blockTagMatcher.find()) {
                final String tagName = blockTagMatcher.group(1);
                String content = s.substring(blockTagMatcher.end(1));
                if (content.endsWith("*/")) {
                    content = content.substring(0, content.length() - 2);
                }
                int line = aCmt.getStartLineNo() + i;
                int col = blockTagMatcher.start(1) - 1;
                if (i == 0) {
                    col += aCmt.getStartColNo();
                }
                if (JavadocTagInfo.isValidName(tagName)) {
                    tags.add(new JavadocTag(line, col, tagName, content.trim()));
                }
                else {
                    invalidTags.add(new InvalidJavadocTag(line, col, tagName));
                }
            }
            // No block tag, so look for inline validTags
            else if (tagType.equals(JavadocTagType.ALL) ||
                     tagType.equals(JavadocTagType.INLINE)) {
                // Match JavaDoc text after comment characters
                Pattern commentPattern = Utils.getPattern("^\\s*(?:/\\*{2,}|\\*+)\\s*(.*)");
                Matcher commentMatcher = commentPattern.matcher(s);
                final String commentContents;
                final int commentOffset; // offset including comment characters
                if (!commentMatcher.find()) {
                    commentContents = s; // No leading asterisks, still valid
                    commentOffset = 0;
                }
                else {
                    commentContents = commentMatcher.group(1);
                    commentOffset = commentMatcher.start(1) - 1;
                }
                Pattern tagPattern = Utils.getPattern(".*?\\{@(\\p{Alpha}+)\\s+(.*?)\\}");
                Matcher tagMatcher = tagPattern.matcher(commentContents);
                while (tagMatcher.find()) {
                    if (tagMatcher.groupCount() == 2) {
                        String tagName = tagMatcher.group(1);
                        String tagValue = tagMatcher.group(2).trim();
                        int line = aCmt.getStartLineNo() + i;
                        int col = commentOffset + (tagMatcher.start(1) - 1);
                        if (i == 0) {
                            col += aCmt.getStartColNo();
                        }
                        if (JavadocTagInfo.isValidName(tagName)) {
                            tags.add(new JavadocTag(
                                line, col, tagName, tagValue));
                        }
                        else {
                            invalidTags.add(new InvalidJavadocTag(
                                line, col, tagName));
                        }
                    }
                    //else Error: Unexpected match count for inline JavaDoc tag!
                }
            }
            blockTagPattern = Utils.getPattern("^\\s*\\**\\s*@(\\p{Alpha}+)\\s");
        }
        return new JavadocTags(tags, invalidTags);
    }

    /**
     * The type of Javadoc tag we want returned.
     */
    public enum JavadocTagType
    {
        /** block type. */
        BLOCK,
        /** inline type. */
        INLINE,
        /** all validTags. */
        ALL;
    }

    /**
     * Value object for combining the list of valid validTags with information
     * about invalid validTags encountered in a certain Javadoc comment.
     */
    public static final class JavadocTags {
        /** Valid validTags. */
        public final List<JavadocTag> validTags;
        /** Invalid validTags. */
        public final List<InvalidJavadocTag> invalidTags;

        public JavadocTags(List<JavadocTag> aTags,
                                List<InvalidJavadocTag> aInvalidTags) {
            validTags = aTags;
            invalidTags = aInvalidTags;
        }
    }

    /**
     * Value object for storing data about an invalid Javadoc validTags.
     */
    public static final class InvalidJavadocTag {
        /** The line in which the invalid tag occurs. */
        public final int line;
        /** The column in which the invalid tag occurs. */
        public final int col;
        /** The name of the invalid tag. */
        public final String name;

        public InvalidJavadocTag(int aLine, int aCol, String aName) {
            line = aLine;
            col = aCol;
            name = aName;
        }
    }
}
