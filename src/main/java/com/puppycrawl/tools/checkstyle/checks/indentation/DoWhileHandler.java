////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for do...while blocks.
 *
 * @author jrichard
 */
public class DoWhileHandler extends BlockParentHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public DoWhileHandler(IndentationCheck aIndentCheck,
            DetailAST aAst, ExpressionHandler aParent)
    {
        super(aIndentCheck, "do..while", aAst, aParent);
    }

    /**
     * Check the indentation level of the conditional expression.
     */
    private void checkCondExpr()
    {
        final DetailAST condAst = getMainAst()
            .findFirstToken(TokenTypes.LPAREN).getNextSibling();
        checkExpressionSubtree(condAst, getLevel(), false, false);
    }

    @Override
    public void checkIndentation()
    {
        super.checkIndentation();
        checkCondExpr();
    }
}
