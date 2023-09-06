///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for switch statements.
 *
 */
public class SwitchHandler extends BlockParentHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public SwitchHandler(IndentationCheck indentCheck,
        DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "switch", ast, parent);
    }

    @Override
    protected DetailAST getLeftCurly() {
        return getMainAst().findFirstToken(TokenTypes.LCURLY);
    }

    @Override
    protected DetailAST getRightCurly() {
        return getMainAst().findFirstToken(TokenTypes.RCURLY);
    }

    @Override
    protected DetailAST getListChild() {
        // all children should be taken care of by case handler (plus
        // there is no parent of just the cases, if checking is needed
        // here in the future, an additional way beyond checkChildren()
        // will have to be devised to get children)
        return null;
    }

    @Override
    protected DetailAST getNonListChild() {
        return null;
    }

    /**
     * Check the indentation of the switch expression.
     */
    private void checkSwitchExpr() {
        checkExpressionSubtree(
            getMainAst().findFirstToken(TokenTypes.LPAREN).getNextSibling(),
            getIndent(),
            false,
            false);
    }

    @Override
    public void checkIndentation() {
        checkSwitchExpr();
        super.checkIndentation();
    }

}
