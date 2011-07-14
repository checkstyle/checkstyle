////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
import java.util.List;

/**
 * <p>
 * Helper class used to parse HTML tags or generic type identifiers
 * from a single line of text. Just the beginning of the HTML tag
 * is located.  No attempt is made to parse out the complete tag,
 * particularly since some of the tag parameters could be located
 * on the following line of text.  The <code>hasNextTag</code> and
 * <code>nextTag</code> methods are used to iterate through the HTML
 * tags or generic type identifiers that were found on the line of text.
 * </p>
 *
 * <p>
 * This class isn't really specific to HTML tags. Currently the only HTML
 * tag that this class looks specifically for is the HTML comment tag.
 * This class helps figure out if a tag exists and if it is well-formed.
 * It does not know whether it is valid HTML.  This class is also used for
 * generics types which looks like opening HTML tags ex: <T>, <E>, <V>,
 * <MY_FOO_TYPE>, etc. According to this class they are valid tags.
 * </p>
 * @author Chris Stillwell
 */
class TagParser
{
    /** List of HtmlTags found on the input line of text. */
    private final List<HtmlTag> mTags = Lists.newLinkedList();

    /**
     * Constructs a TagParser and finds the first tag if any.
     * @param aText the line of text to parse.
     * @param aLineNo the source line number.
     */
    public TagParser(String[] aText, int aLineNo)
    {
        parseTags(aText, aLineNo);
    }

    /**
     * Returns the next available HtmlTag.
     * @return a HtmlTag or <code>null</code> if none available.
     * @throws IndexOutOfBoundsException if there are no HtmlTags
     *         left to return.
     */
    public HtmlTag nextTag()
    {
        return mTags.remove(0);
    }

    /**
     * Indicates if there are any more HtmlTag to retrieve.
     * @return <code>true</code> if there are more tags.
     */
    public boolean hasNextTag()
    {
        return (mTags.size() > 0);
    }

    /**
     * Performs lazy initialization on the internal tags List
     * and adds the tag.
     * @param aTag the HtmlTag to add.
     */
    private void add(HtmlTag aTag)
    {
        mTags.add(aTag);
    }

    /**
     * Parses the text line for any HTML tags and adds them to the internal
     * List of tags.
     * @param aText the source line to parse.
     * @param aLineNo the source line number.
     */
    private void parseTags(String[] aText, int aLineNo)
    {
        final int nLines = aText.length;
        Point position = new Point(0, 0);

        position = findChar(aText, '<', position);
        while (position.getLineNo() < nLines) {
            // if this is html comment then skip it
            if (isCommentTag(aText, position)) {
                position = skipHtmlComment(aText, position);
            }
            else if (!isTag(aText, position)) {
                position = getNextCharPos(aText, position);
            }
            else {
                // find end of tag
                final Point endTag = findChar(aText, '>', position);
                final boolean incompleteTag = (endTag.getLineNo() >= nLines);
                // get tag id (one word)
                final String tagId =
                    (incompleteTag ? "" : getTagId(aText, position));
                // is this closed tag
                final boolean closedTag =
                    ((endTag.getLineNo() < nLines) && (endTag.getColumnNo() > 0)
                     && (aText[endTag.getLineNo()]
                     .charAt(endTag.getColumnNo() - 1) == '/'));
                // add new tag
                add(new HtmlTag(tagId,
                                position.getLineNo() + aLineNo,
                                position.getColumnNo(),
                                closedTag,
                                incompleteTag,
                                aText[position.getLineNo()]));
                position = endTag;
            }
            position = findChar(aText, '<', position);
        }
    }

    /**
     * Checks if the given position is start one for HTML tag.
     * @param aText text of javadoc comments.
     * @param aPos position to check.
     * @return <code>true</code> some HTML tag starts from given position.
     */
    private boolean isTag(String[] aText, Point aPos)
    {
        final int column = aPos.getColumnNo() + 1;
        final String text = aText[aPos.getLineNo()];

        //Character.isJavaIdentifier... may not be a valid HTML
        //identifier but is valid for generics
        return ((column < text.length())
                && (Character.isJavaIdentifierStart(text.charAt(column))
                    || Character.isJavaIdentifierPart(text.charAt(column))
                    || text.charAt(column) == '/')
                || (column >= text.length()));
    }

    /**
     * Parse tag id.
     * @param aText text of javadoc comments.
     * @param aTagStart start position of the tag
     * @return id for given tag
     */
    private String getTagId(String[] aText, Point aTagStart)
    {
        int column = aTagStart.getColumnNo() + 1;
        String text = aText[aTagStart.getLineNo()];
        if (column >= text.length()) {
            return "";
        }

        if (text.charAt(column) == '/') {
            column++;
        }

        text = text.substring(column).trim();
        column = 0;

        //Character.isJavaIdentifier... may not be a valid HTML
        //identifier but is valid for generics
        while (column < text.length()
            && (Character.isJavaIdentifierStart(text.charAt(column))
                || Character.isJavaIdentifierPart(text.charAt(column))))
        {
            column++;
        }

        return text.substring(0, column);
    }

    /**
     * If this is a HTML-comments.
     * @param aText text of javadoc comments
     * @param aPos position to check
     * @return <code>true</code> if HTML-comments
     *         starts form given position.
     */
    private boolean isCommentTag(String[] aText, Point aPos)
    {
        return aText[aPos.getLineNo()].startsWith("<!--", aPos.getColumnNo());
    }

    /**
     * Skips HTML comments.
     * @param aText text of javadoc comments.
     * @param aFrom start position of HTML-comments
     * @return position after HTML-comments
     */
    private Point skipHtmlComment(String[] aText, Point aFrom)
    {
        Point to = aFrom;
        to = findChar(aText, '>', to);
        while ((to.getLineNo() < aText.length)
               && !aText[to.getLineNo()]
               .substring(0, to.getColumnNo()).endsWith("-->"))
        {
            to = findChar(aText, '>', getNextCharPos(aText, to));
        }
        return to;
    }

    /**
     * Finds next occurrence of given character.
     * @param aText text to search
     * @param aChar character to search
     * @param aFrom position to start search
     * @return position of next occurrence of given character
     */
    private Point findChar(String[] aText, char aChar, Point aFrom)
    {
        Point curr = new Point(aFrom.getLineNo(), aFrom.getColumnNo());
        while ((curr.getLineNo() < aText.length)
               && (aText[curr.getLineNo()].charAt(curr.getColumnNo()) != aChar))
        {
            curr = getNextCharPos(aText, curr);
        }

        return curr;
    }

    /**
     * Returns position of next comment character, skips
     * whitespaces and asterisks.
     * @param aText to search.
     * @param aFrom location to search from
     * @return location of the next character.
     */
    private Point getNextCharPos(String[] aText, Point aFrom)
    {
        int line = aFrom.getLineNo();
        int column = aFrom.getColumnNo() + 1;
        while ((line < aText.length) && (column >= aText[line].length())) {
            // go to the next line
            line++;
            column = 0;
            if (line < aText.length) {
                //skip beginning spaces and stars
                final String currentLine = aText[line];
                while ((column < currentLine.length())
                       && (Character.isWhitespace(currentLine.charAt(column))
                           || (currentLine.charAt(column) == '*')))
                {
                    column++;
                    if ((column < currentLine.length())
                        && (currentLine.charAt(column - 1) == '*')
                        && (currentLine.charAt(column) == '/'))
                    {
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
    private static final class Point
    {
        /** line number. */
        private final int mLine;
        /** column number.*/
        private final int mColumn;

        /**
         * Creates new <code>Point</code> instance.
         * @param aLineNo line number
         * @param aColumnNo column number
         */
        public Point(int aLineNo, int aColumnNo)
        {
            mLine = aLineNo;
            mColumn = aColumnNo;
        }

        /**
         * Getter for line number.
         * @return line number of the position.
         */
        public int getLineNo()
        {
            return mLine;
        }

        /**
         * Getter for column number.
         * @return column number of the position.
         */
        public int getColumnNo()
        {
            return mColumn;
        }
    }
}
