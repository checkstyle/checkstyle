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

/**
 * Used to keep track of a tag and the text that follows it.
 *
 * @author Chris Stillwell
 */
class HtmlTag
{
    /** The maximum length of text to display with this tag. */
    private static final int MAX_TEXT_LEN = 60;

    /** The HTML tag name. */
    private final String mId;

    /** The line number in the source file where this tag was found. */
    private final int mLineNo;

    /** The position within the line where this tag was found. */
    private final int mPosition;

    /** The comment line of text where this tag appears. */
    private final String mText;

    /**
     * Construct the HtmlTag.
     * @param aId the HTML tag name.
     * @param aLineNo the source line number of this tag.
     * @param aPosition the position within the text of this tag.
     * @param aText the line of comment text for this tag.
     */
    HtmlTag(String aId, int aLineNo, int aPosition, String aText)
    {
        mId = (aId.charAt(0) == '/') ? aId.substring(1) : aId;
        mLineNo = aLineNo;
        mPosition = aPosition;
        mText = aText;
    }

    /**
     * Returns the id (name) of this tag.
     * @return a String id.
     */
    public String getId()
    {
        return mId;
    }

    /**
     * Indicates if this tag is a close (end) tag.
     * @return <code>true</code> is this is a close tag.
     */
    public boolean isCloseTag()
    {
        return (mText.charAt(mPosition + 1) == '/');
    }

    /**
     * Returns the source line number where this tag was found.
     * Used for displaying a Checkstyle error.
     * @return an int line number.
     */
    public int getLineno()
    {
        return mLineNo;
    }

    /**
     * Returns the position with in the comment line where this tag
     * was found.  Used for displaying a Checkstyle error.
     * @return an int relative to zero.
     */
    public int getPosition()
    {
        return mPosition;
    }

    /**
     * Returns this HTML tag and trailing text.
     * Used for displaying a Checkstyle error.
     * @return the String text of this tag.
     */
    public String toString()
    {
        final int startOfText = mPosition;
        final int endOfText =
            Math.min(startOfText + HtmlTag.MAX_TEXT_LEN, mText.length());
        return mText.substring(startOfText, endOfText);
    }
}
