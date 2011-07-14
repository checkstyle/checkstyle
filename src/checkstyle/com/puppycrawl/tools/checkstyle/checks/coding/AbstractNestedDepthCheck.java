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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Abstract class which provides helpers functionality for nestedchecks.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public abstract class AbstractNestedDepthCheck extends Check
{
    /** maximum allowed nesting depth */
    private int mMax;
    /** curren nesting depth */
    private int mDepth;

    /**
     * Creates new instance of checks.
     * @param aMax default allowed nesting depth.
     */
    public AbstractNestedDepthCheck(int aMax)
    {
        setMax(aMax);
    }

    @Override
    public final int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        mDepth = 0;
    }

    /**
     * Getter for maximum allowed nesting depth.
     * @return maximum allowed nesting depth.
     */
    public final int getMax()
    {
        return mMax;
    }

    /**
     * Setter for maximum allowed nesting depth.
     * @param aMax maximum allowed nesting depth.
     */
    public final void setMax(int aMax)
    {
        mMax = aMax;
    }

    /**
     * Increasing current nesting depth.
     * @param aAST note which increases nesting.
     * @param aMessageId message id for logging error.
     */
    protected final void nestIn(DetailAST aAST, String aMessageId)
    {
        if (mDepth > mMax) {
            log(aAST, aMessageId, mDepth, mMax);
        }
        ++mDepth;
    }

    /** Decreasing current nesting depth */
    protected final void nestOut()
    {
        --mDepth;
    }
}
