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
package com.puppycrawl.tools.checkstyle.api;

/**
 * Representation of the comment block.
 *
 * @author o_sukhodolsky
 */
class Comment implements TextBlock
{
    /** text of the comment. */
    private final String[] mText;
    /** number of first line of the commen. */
    private final int mFirstLine;
    /** number of last line of the commen. */
    private final int mLastLine;

    /**
     * Creates new instance.
     * @param aText the lines that make up the comment.
     * @param aLastLine number of the last line of the comment.
     */
    public Comment(final String[] aText, final int aLastLine)
    {
        mText = new String[aText.length];
        for (int i = 0; i < mText.length; i++) {
            mText[i] = aText[i];
        }
        mFirstLine = aLastLine - mText.length + 1;
        mLastLine = aLastLine;
    }

    /**
     * Returns line that make up the comment.
     * @return comment text.
     */
    public final String[] getText()
    {
        return (String[]) mText.clone();
    }

    /**
     * Returns number of fist line of the comment.
     * @return number of fist line of the comment
     */
    public final int getStartLineNo()
    {
        return mFirstLine;
    }

    /**
     * Returns number of last line of the comment.
     * @return number of last line of the comment
     */
    public final int getEndLineNo()
    {
        return mLastLine;
    }
}
