////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
 * Handler for switch statements.
 *
 * @author jrichard
 */
public class SwitchHandler extends BlockParentHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public SwitchHandler(IndentationCheck aIndentCheck,
        DetailAST aAst, ExpressionHandler aParent)
    {
        super(aIndentCheck, "switch", aAst, aParent);
    }

    /**
     * Get the left curly brace portion of the expression we are handling.
     *
     * @return the left curly brace expression
     */
    protected DetailAST getLCurly()
    {
        return getMainAst().findFirstToken(TokenTypes.LCURLY);
    }

    /**
     * Get the right curly brace portion of the expression we are handling.
     *
     * @return the right curly brace expression
     */
    protected DetailAST getRCurly()
    {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    /**
     * There is no list of statements child for this handler.
     *
     * @return null
     */
    protected DetailAST getListChild()
    {
        // all children should be taken care of by case handler (plus
        // there is no parent of just the cases, if checking is needed
        // here in the future, an additional way beyond checkChildren()
        // will have to be devised to get children)
        return null;
    }

    /**
     * There is no child element that is not a list of statements.
     *
     * @return null
     */
    protected DetailAST getNonlistChild()
    {
        return null;
    }

    /**
     * Check the indentation of the switch expression.
     */
    private void checkSwitchExpr()
    {
        checkExpressionSubtree(
            (DetailAST) getMainAst().findFirstToken(TokenTypes.LPAREN).
                getNextSibling(),
            getLevel(),
            false,
            false);
    }

    /**
     * Check the indentation of the expression we are handling.
     */
    public void checkIndentation()
    {
        checkSwitchExpr();
        super.checkIndentation();
    }
}
