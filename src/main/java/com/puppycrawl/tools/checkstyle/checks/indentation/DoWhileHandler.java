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
 * Handler for do...while blocks.
 *
 */
public class DoWhileHandler extends BlockParentHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public DoWhileHandler(IndentationCheck indentCheck,
            DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "do..while", ast, parent);
    }

    /**
     * Check the indentation level of the while and conditional expression.
     */
    private void checkWhileExpr() {
        // check while statement alone

        final DetailAST whileAst = getMainAst().findFirstToken(TokenTypes.DO_WHILE);

        if (isOnStartOfLine(whileAst)
                && !getIndent().isAcceptable(expandedTabsColumnNo(whileAst))) {
            logError(whileAst, "while", expandedTabsColumnNo(whileAst));
        }

        // check condition alone

        final DetailAST condAst = getMainAst().findFirstToken(TokenTypes.LPAREN).getNextSibling();

        checkExpressionSubtree(condAst, getIndent(), false, false);
    }

    @Override
    protected DetailAST getNonListChild() {
        return getMainAst().getFirstChild();
    }

    @Override
    public void checkIndentation() {
        super.checkIndentation();
        checkWhileExpr();
    }

}
