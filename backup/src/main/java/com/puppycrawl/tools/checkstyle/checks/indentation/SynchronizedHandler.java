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
 * Handler for synchronized statements.
 *
 */
public class SynchronizedHandler extends BlockParentHandler {

    /**
     * Determine that "synchronized" token used as modifier of method.
     */
    private final boolean methodModifier;

    /**
     * Construct an instance of this handler with the given indentation check,
     * name, abstract syntax tree, and parent handler.
     *
     * @param indentCheck the indentation check
     * @param ast         the abstract syntax tree
     * @param parent      the parent handler
     */
    public SynchronizedHandler(IndentationCheck indentCheck, DetailAST ast,
                               AbstractExpressionHandler parent) {
        super(indentCheck, "synchronized", ast, parent);
        methodModifier = isMethodModifier(ast);
    }

    @Override
    public void checkIndentation() {
        if (!methodModifier) {
            super.checkIndentation();
            checkSynchronizedExpr();
            checkWrappingIndentation(getMainAst(),
                    getSynchronizedStatementRightParen(getMainAst()));
        }
    }

    /**
     * Check indentation of expression we synchronized on.
     */
    private void checkSynchronizedExpr() {
        final DetailAST syncAst = getMainAst().findFirstToken(TokenTypes.LPAREN)
                .getNextSibling();
        final IndentLevel expected =
                new IndentLevel(getIndent(), getBasicOffset());
        checkExpressionSubtree(syncAst, expected, false, false);
    }

    /**
     * Checks if given synchronized is modifier of method.
     *
     * @param ast synchronized(TokenTypes.LITERAL_SYNCHRONIZED) to check
     * @return true if synchronized only modifies method
     */
    private static boolean isMethodModifier(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.MODIFIERS;
    }

    /**
     * Returns right parenthesis of synchronized statement.
     *
     * @param syncStatementAST ast node(TokenTypes.LITERAL_SYNCHRONIZED)
     * @return right parenthesis of synchronized statement.
     */
    private static DetailAST getSynchronizedStatementRightParen(DetailAST syncStatementAST) {
        return syncStatementAST.findFirstToken(TokenTypes.RPAREN);
    }

}
