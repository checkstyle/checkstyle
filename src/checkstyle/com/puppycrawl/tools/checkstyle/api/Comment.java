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
package com.puppycrawl.tools.checkstyle.api;

/**
 * Representation of the comment block.
 *
 * @author o_sukhodolsky
 */
public class Comment implements TextBlock
{
    /** text of the comment. */
    private final String[] mText;

    /** number of first line of the comment. */
    private final int mFirstLine;

    /** number of last line of the comment. */
    private final int mLastLine;

    /** number of first column of the comment. */
    private final int mFirstCol;

    /** number of last column of the comment. */
    private final int mLastCol;

    /**
     * Creates new instance.
     * @param aText the lines that make up the comment.
     * @param aFirstCol number of the first column of the comment.
     * @param aLastLine number of the last line of the comment.
     * @param aLastCol number of the last column of the comment.
     */
    public Comment(final String[] aText, final int aFirstCol,
            final int aLastLine, final int aLastCol)
    {
        mText = new String[aText.length];
        System.arraycopy(aText, 0, mText, 0, mText.length);
        mFirstLine = aLastLine - mText.length + 1;
        mLastLine = aLastLine;
        mFirstCol = aFirstCol;
        mLastCol = aLastCol;
    }

    /** {@inheritDoc} */
    public final String[] getText()
    {
        return mText.clone();
    }

    /** {@inheritDoc} */
    public final int getStartLineNo()
    {
        return mFirstLine;
    }

    /** {@inheritDoc} */
    public final int getEndLineNo()
    {
        return mLastLine;
    }

    /** {@inheritDoc} */
    public int getStartColNo()
    {
        return mFirstCol;
    }

    /** {@inheritDoc} */
    public int getEndColNo()
    {
        return mLastCol;
    }

    /** {@inheritDoc} */
    public boolean intersects(int aStartLineNo, int aStartColNo,
                              int aEndLineNo, int aEndColNo)
    {
        // compute a single number for start and end
        // to simpify conditional logic
        final long multiplier = Integer.MAX_VALUE;
        final long thisStart = mFirstLine * multiplier + mFirstCol;
        final long thisEnd = mLastLine * multiplier + mLastCol;
        final long inStart = aStartLineNo * multiplier + aStartColNo;
        final long inEnd = aEndLineNo * multiplier + aEndColNo;

        return !((thisEnd < inStart) || (inEnd < thisStart));
    }

    @Override
    public String toString()
    {
        return "Comment[" + mFirstLine + ":" + mFirstCol + "-"
            + mLastLine + ":" + mLastCol + "]";
    }
}
