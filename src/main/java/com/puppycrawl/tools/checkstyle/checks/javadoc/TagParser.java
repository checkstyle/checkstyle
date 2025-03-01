///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import java.util.LinkedList;
import java.util.List;

/**
 * <div>
 * Helper class used to parse HTML tags or generic type identifiers
 * from a single-line of text. Just the beginning of the HTML tag
 * is located.  No attempt is made to parse out the complete tag,
 * particularly since some of the tag parameters could be located
 * on the following line of text.  The {@code hasNextTag} and
 * {@code nextTag} methods are used to iterate through the HTML
 * tags or generic type identifiers that were found on the line of text.
 * </div>
 *
 * <p>
 * This class isn't really specific to HTML tags. Currently, the only HTML
 * tag that this class looks specifically for is the HTML comment tag.
 * This class helps figure out if a tag exists and if it is well-formed.
 * It does not know whether it is valid HTML.  This class is also used for
 * generics types which looks like opening HTML tags ex: {@code <T>, <E>, <V>,
 * <MY_FOO_TYPE>}, etc. According to this class they are valid tags.
 * </p>
 *
 */
class TagParser {

    /** HtmlTags found on the input line of text. */
    private final List<HtmlTag> tags = new LinkedList<>();

    /**
     * Constructs a TagParser and finds the first tag if any.
     *
     * @param text the line of text to parse.
     * @param lineNo the source line number.
     */
    /* package */ TagParser(String[] text, int lineNo) {
        parseTags(text, lineNo);
    }

    /**
     * Returns the next available HtmlTag.
     *
     * @return a HtmlTag or {@code null} if none available.
     * @throws IndexOutOfBoundsException if there are no HtmlTags
     *         left to return.
     */
    public HtmlTag nextTag() {
        return tags.remove(0);
    }

    /**
     * Indicates if there are any more HtmlTag to retrieve.
     *
     * @return {@code true} if there are more tags.
     */
    public boolean hasNextTag() {
        return !tags.isEmpty();
    }

    /**
     * Performs lazy initialization on the internal tags List
     * and adds the tag.
     *
     * @param tag the HtmlTag to add.
     */
    private void add(HtmlTag tag) {
        tags.add(tag);
    }

    /**
     * Parses the text line for any HTML tags and adds them to the internal
     * List of tags.
     *
     * @param text the source line to parse.
     * @param lineNo the source line number.
     */
    private void parseTags(String[] text, int lineNo) {
        final int nLines = text.length;
        Point position = new Point(0, 0);
        while (position.getLineNo() < nLines) {
            // if this is html comment then skip it
            if (isCommentTag(text, position)) {
                position = skipHtmlComment(text, position);
            }
            else if (isTag(text, position)) {
                position = parseTag(text, lineNo, nLines, position);
            }
            else {
                position = getNextPoint(text, position);
            }
            position = findChar(text, '<', position);
        }
    }

    /**
     * Parses the tag and return position after it.
     *
     * @param text the source line to parse.
     * @param lineNo the source line number.
     * @param nLines line length
     * @param position start position for parsing
     * @return position after tag
     */
    private Point parseTag(String[] text, int lineNo, final int nLines, Point position) {
        // find end of tag
        final Point endTag = findChar(text, '>', position);
        final boolean incompleteTag = endTag.getLineNo() >= nLines;
        // get tag id (one word)
        final String tagId = getTagId(text, position);
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
        return endTag;
    }

    /**
     * Checks if the given position is start one for HTML tag.
     *
     * @param javadocText text of javadoc comments.
     * @param pos position to check.
     * @return {@code true} some HTML tag starts from given position.
     */
    private static boolean isTag(String[] javadocText, Point pos) {
        final int column = pos.getColumnNo() + 1;
        final String text = javadocText[pos.getLineNo()];

        // Character.isJavaIdentifier... may not be a valid HTML
        // identifier but is valid for generics
        return column >= text.length()
                || Character.isJavaIdentifierStart(text.charAt(column))
                    || text.charAt(column) == '/';
    }

    /**
     * Parse tag id.
     *
     * @param javadocText text of javadoc comments.
     * @param tagStart start position of the tag
     * @return id for given tag
     */
    private static String getTagId(String[] javadocText, Point tagStart) {
        String tagId = "";
        int column = tagStart.getColumnNo() + 1;
        String text = javadocText[tagStart.getLineNo()];
        if (column < text.length()) {
            if (text.charAt(column) == '/') {
                column++;
            }
            text = text.substring(column);
            int position = 0;

            // Character.isJavaIdentifier... may not be a valid HTML
            // identifier but is valid for generics
            while (position < text.length()
                    && Character.isJavaIdentifierPart(text.charAt(position))) {
                position++;
            }

            tagId = text.substring(0, position);
        }
        return tagId;
    }

    /**
     * If this is a HTML-comments.
     *
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
     *
     * @param text text of javadoc comments.
     * @param fromPoint start position of HTML-comments
     * @return position after HTML-comments
     */
    private static Point skipHtmlComment(String[] text, Point fromPoint) {
        Point toPoint = fromPoint;
        while (toPoint.getLineNo() < text.length && !text[toPoint.getLineNo()]
                .substring(0, toPoint.getColumnNo() + 1).endsWith("-->")) {
            toPoint = getNextPoint(text, toPoint); // Mutated code
        }
        return toPoint;
    }

    /**
     * Finds next occurrence of given character.
     *
     * @param text text to search
     * @param character character to search
     * @param from position to start search
     * @return position of next occurrence of given character
     */
    private static Point findChar(String[] text, char character, Point from) {
        Point curr = new Point(from.getLineNo(), from.getColumnNo());
        while (curr.getLineNo() < text.length
               && text[curr.getLineNo()].charAt(curr.getColumnNo()) != character) {
            curr = getNextPoint(text, curr);
        }

        return curr;
    }

    /**
     * Increments column number to be examined, moves onto the next line when no
     * more characters are available.
     *
     * @param text to search.
     * @param from location to search from
     * @return next point to be examined
     */
    private static Point getNextPoint(String[] text, Point from) {
        int line = from.getLineNo();
        int column = from.getColumnNo() + 1;
        while (line < text.length && column >= text[line].length()) {
            // go to the next line
            line++;
            column = 0;
        }
        return new Point(line, column);
    }

    /**
     * Represents current position in the text.
     */
    private static final class Point {

        /** Line number. */
        private final int lineNo;
        /** Column number.*/
        private final int columnNo;

        /**
         * Creates new {@code Point} instance.
         *
         * @param lineNo line number
         * @param columnNo column number
         */
        private Point(int lineNo, int columnNo) {
            this.lineNo = lineNo;
            this.columnNo = columnNo;
        }

        /**
         * Getter for line number.
         *
         * @return line number of the position.
         */
        public int getLineNo() {
            return lineNo;
        }

        /**
         * Getter for column number.
         *
         * @return column number of the position.
         */
        public int getColumnNo() {
            return columnNo;
        }

    }

}
