////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Helper class used to parse HTML tags from a single line of text.
 * Just the beginning of the HTML tag is located.  No attempt is made to
 * parse out the complete tag, particularly since some of the tag
 * parameters could be located on the following line of text.  The
 * <code>hasNextTag</code> and <code>nextTag</code> methods are used
 * to iterate through the HTML tags that were found on the line of text.
 *
 * @author Chris Stillwell
 */
class TagParser
{
    /** List of HtmlTags found on the input line of text. */
    private final List mTags = new LinkedList();

    /**
     * Constructs a TagParser and finds the first tag if any.
     * @param aText the line of text to parse.
     * @param aLineNo the source line number.
     */
    public TagParser(String aText, int aLineNo)
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
        return (HtmlTag) mTags.remove(0);
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
    private void parseTags(String aText, int aLineNo)
    {
        int position = 0;
        final StringTokenizer tokenizer =
            new StringTokenizer(aText, " \t\n\r\f<>", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            position += token.length();

            if (token.equals("<")) {
                token = tokenizer.nextToken();
                position += token.length();

                if (((token.charAt(0) >= 'A')
                     && (token.charAt(0) <= 'Z'))
                    || ((token.charAt(0) >= 'a')
                        && (token.charAt(0) <= 'z'))
                    || (token.charAt(0) == '/'))
                {
                    // If a character or / follows the < sign,
                    // then assume its html.
                    // The non-html version is "&lt;"
                    // Point before the angle bracket
                    final int startOfTag = position - token.length() - 1;
                    add(new HtmlTag(token, aLineNo, startOfTag, aText));
                }
            }
        }
    }
}
