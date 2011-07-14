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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Restricts nested try-catch-finally blocks to a specified depth (default = 1).
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public final class NestedTryDepthCheck extends AbstractNestedDepthCheck
{
    /** default allowed nesting depth */
    private static final int DEFAULT_MAX = 1;

    /** Creates new check instance with default allowed nesting depth. */
    public NestedTryDepthCheck()
    {
        super(DEFAULT_MAX);
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_TRY};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.LITERAL_TRY:
            visitLiteralTry(aAST);
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.LITERAL_TRY:
            leaveLiteralTry();
            break;
        default:
            throw new IllegalStateException(aAST.toString());
        }
    }

    /**
     * Increases current nesting depth.
     * @param aTry node for try.
     */
    private void visitLiteralTry(DetailAST aTry)
    {
        nestIn(aTry, "nested.try.depth");
    }

    /** Decreases current nesting depth */
    private void leaveLiteralTry()
    {
        nestOut();
    }
}
