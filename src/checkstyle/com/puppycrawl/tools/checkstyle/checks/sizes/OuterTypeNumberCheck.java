////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Check;

/**
 * Checks for the number of defined types at the "outer" level.
 * @author oliverb
 */
public class OuterTypeNumberCheck extends Check
{
    /** The maximum allowed number of outer types. */
    private int mMax = 1;
    /** Tracks the current depth in types. */
    private int mCurrentDepth;
    /** Tracks the number of outer types found. */
    private int mOuterNum;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF, TokenTypes.ANNOTATION_DEF, };
    }

    @Override
    public void beginTree(DetailAST aAst)
    {
        mCurrentDepth = 0;
        mOuterNum = 0;
    }

    @Override
    public void finishTree(DetailAST aAst)
    {
        if (mMax < mOuterNum) {
            log(aAst, "maxOuterTypes", mOuterNum, mMax);
        }
    }

    @Override
    public void visitToken(DetailAST aAst)
    {
        if (0 == mCurrentDepth) {
            mOuterNum++;
        }
        mCurrentDepth++;
    }

    @Override
    public void leaveToken(DetailAST aAst)
    {
        mCurrentDepth--;
    }

    /**
     * Sets the maximum allowed number of outer types.
     * @param aTo the new number.
     */
    public void setMax(int aTo)
    {
        mMax = aTo;
    }
}
