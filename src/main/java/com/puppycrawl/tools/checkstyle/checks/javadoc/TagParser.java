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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * <p>
 * Helper class used to parse HTML tags or generic type identifiers
 * from a single line of text. Just the beginning of the HTML tag
 * is located.  No attempt is made to parse out the complete tag,
 * particularly since some of the tag parameters could be located
 * on the following line of text.  The {@code hasNextTag} and
 * {@code nextTag} methods are used to iterate through the HTML
 * tags or generic type identifiers that were found on the line of text.
 * </p>
 *
 * <p>
 * This class isn't really specific to HTML tags. Currently the only HTML
 * tag that this class looks specifically for is the HTML comment tag.
 * This class helps figure out if a tag exists and if it is well-formed.
 * It does not know whether it is valid HTML.  This class is also used for
 * generics types which looks like opening HTML tags ex: {@code <T>, <E>, <V>,
 * <MY_FOO_TYPE>}, etc. According to this class they are valid tags.
 * </p>
 *
 * @author Chris Stillwell
 */
class TagParser {
    /** List of HtmlTags found on the input line of text. */
    private final List<HtmlTag> tags = Lists.newLinkedList();

    /**
     * Constructs a TagParser and finds the first tag if any.
     * @param text the line of text to parse.
     * @param lineNo the source line number.
     */
    TagParser(String[] text, int lineNo) {
        parseTags(text, lineNo);
    }

    /**
     * Returns the next available HtmlTag.
     * @return a HtmlTag or {@code null} if none available.
     * @throws IndexOutOfBoundsException if there are no HtmlTags
     *         left to return.
     */
    public HtmlTag nextTag() {
        return tags.remove(0);
    }

    /**
     * Indicates if there are any more HtmlTag to retrieve.
     * @return {@code true} if there are more tags.
     */
    public boolean hasNextTag() {
        return !tags.isEmpty();
    }

    /**
     * Performs lazy initialization on the internal tags List
     * and adds the tag.
     * @param tag the HtmlTag to add.
     */
    private void add(HtmlTag tag) {
        tags.add(tag);
    }

    /**
     * Parses the text line for any HTML tags and adds them to the internal
     * List of tags.
     * @param text the source line to parse.
     * @param lineNo the source line number.
     */
    private void parseTags(String[] text, int lineNo) {
        final int nLines = text.length;
        Point position = new Point(0, 0);

        position = findChar(text, '<', position);
        while (position.getLineNo() < nLines) {
            // if this is html comment then skip it
            if (isCommentTag(text, position)) {
                position = skipHtmlComment(text, position);
            }
            else if (!isTag(text, position)) {
                position = getNextCharPos(text, position);
            }
            else {
                // find end of tag
                final Point endTag = findChar(text, '>', position);
                final boolean incompleteTag = endTag.getLineNo() >= nLines;
                // get tag id (one word)
                final String tagId;

                if (incompleteTag) {
                    tagId = "";
                }
                else {
                    tagId = getTagId(text, position);
                }
                // is this closed tag
                final boolean closedTag =
                        endTag.getLineNo() < nLines
                         && text[endTag.getLineNo()]
                         .charAt(endTag.getColumnNo() - 1) == '/';
                // add new tag
                add(new HtmlTag(tagId,
                                position.getLineNo() + lineNo,
                                position.getColumnNo(),
                                closedTag,
                                incompleteTag,
                                text[position.getLineNo()]));
                position = endTag;
            }
            position = findChar(text, '<', position);
        }
    }

    /**
     * Checks if the given position is start one for HTML tag.
     * @param javadocText text of javadoc comments.
     * @param pos position to check.
     * @return {@code true} some HTML tag starts from given position.
     */
    private static boolean isTag(String[] javadocText, Point pos) {
        final int column = pos.getColumnNo() + 1;
        final String text = javadocText[pos.getLineNo()];

        //Character.isJavidentifier... may not be a valid HTML
        //identifier but is valid for generics
        return column < text.length()
                && (Character.isJavaIdentifierStart(text.charAt(column))
                    || text.charAt(column) == '/')
                || column >= text.length();
    }

    /**
     * Parse tag id.
     * @param javadocText text of javadoc comments.
     * @param tagStart start position of the tag
     * @return id for given tag
     */
    private static String getTagId(String[] javadocText, Point tagStart) {
        int column = tagStart.getColumnNo() + 1;
        String text = javadocText[tagStart.getLineNo()];
        if (column >= text.length()) {
            return "";
        }

        if (text.charAt(column) == '/') {
            column++;
        }

        text = text.substring(column).trim();
        column = 0;

        //Character.isJavidentifier... may not be a valid HTML
        //identifier but is valid for generics
        while (column < text.length()
            && (Character.isJavaIdentifierStart(text.charAt(column))
                || Character.isJavaIdentifierPart(text.charAt(column)))) {
            column++;
        }

        return text.substring(0, column);
    }

    /**
     * If this is a HTML-comments.
     * @param text text of javadoc comments
     * @param pos position to check
     * @return {@code true} if HTML-comments
     *         starts form given position.
     */
    private static boolean isCommentTag(String[] text, Point pos) {
        return text[pos.getLineNo()].startsWith("<!--", pos.getColumnNo());
    }

    /**
     * Skips HTML comments.
     * @param text text of javadoc comments.
     * @param from start position of HTML-comments
     * @return position after HTML-comments
     */
    private static Point skipHtmlComment(String[] text, Point from) {
        Point to = from;
        to = findChar(text, '>', to);
        while (!text[to.getLineNo()]
               .substring(0, to.getColumnNo() + 1).endsWith("-->")) {
            to = findChar(text, '>', getNextCharPos(text, to));
        }
        return to;
    }

    /**
     * Finds next occurrence of given character.
     * @param text text to search
     * @param character character to search
     * @param from position to start search
     * @return position of next occurrence of given character
     */
    private static Point findChar(String[] text, char character, Point from) {
        Point curr = new Point(from.getLineNo(), from.getColumnNo());
        while (curr.getLineNo() < text.length
               && text[curr.getLineNo()].charAt(curr.getColumnNo()) != character) {
            curr = getNextCharPos(text, curr);
        }

        return curr;
    }

    /**
     * Returns position of next comment character, skips
     * whitespaces and asterisks.
     * @param text to search.
     * @param from location to search from
     * @return location of the next character.
     */
    private static Point getNextCharPos(String[] text, Point from) {
        int line = from.getLineNo();
        int column = from.getColumnNo() + 1;
        while (line < text.length && column >= text[line].length()) {
            // go to the next line
            line++;
            column = 0;
            if (line < text.length) {
                //skip beginning spaces and stars
                final String currentLine = text[line];
                while (column < currentLine.length()
                       && (Character.isWhitespace(currentLine.charAt(column))
                           || currentLine.charAt(column) == '*')) {
                    column++;
                    if (column < currentLine.length()
                        && currentLine.charAt(column - 1) == '*'
                        && currentLine.charAt(column) == '/') {
                        // this is end of comment
                        column = currentLine.length();
                    }
                }
            }
        }

        return new Point(line, column);
    }

    /**
     * Represents current position in the text.
     * @author o_sukholsky
     */
    private static final class Point {
        /** line number. */
        private final int line;
        /** column number.*/
        private final int column;

        /**
         * Creates new {@code Point} instance.
         * @param lineNo line number
         * @param columnNo column number
         */
        Point(int lineNo, int columnNo) {
            line = lineNo;
            column = columnNo;
        }

        /**
         * Getter for line number.
         * @return line number of the position.
         */
        public int getLineNo() {
            return line;
        }

        /**
         * Getter for column number.
         * @return column number of the position.
         */
        public int getColumnNo() {
            return column;
        }
    }
}
