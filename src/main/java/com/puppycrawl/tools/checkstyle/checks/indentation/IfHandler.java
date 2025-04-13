////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for if statements.
 *
 */
public class IfHandler extends BlockParentHandler {

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public IfHandler(IndentationCheck indentCheck,
                     DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "if", ast, parent);
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        final IndentLevel result;
        if (child instanceof ElseHandler) {
            result = getIndent();
        }
        else {
            result = super.getSuggestedChildIndent(child);
        }
        return result;
    }

    @Override
    protected IndentLevel getIndentImpl() {
        final IndentLevel result;
        if (isIfAfterElse()) {
            result = getParent().getIndent();
        }
        else {
            result = super.getIndentImpl();
        }
        return result;
    }

    /**
     * Determines if this 'if' statement is part of an 'else' clause
     * and on the same line.
     *
     * @return true if this 'if' is part of an 'else', false otherwise
     */
    private boolean isIfAfterElse() {
        // check if there is an 'else' and an 'if' on the same line
        final DetailAST parent = getMainAst().getParent();
        return parent.getType() == TokenTypes.LITERAL_ELSE
            && TokenUtil.areOnSameLine(parent, getMainAst());
    }

    @Override
    protected void checkTopLevelToken() {
        if (!isIfAfterElse()) {
            super.checkTopLevelToken();
        }
    }

    /**
     * Check the indentation of the conditional expression.
     */
    private void checkCondExpr() {
        final DetailAST condAst = getMainAst().findFirstToken(TokenTypes.LPAREN)
            .getNextSibling();
        final IndentLevel expected =
            new IndentLevel(getIndent(), getBasicOffset());
        checkExpressionSubtree(condAst, expected, false, false);
    }

    @Override
    public void checkIndentation() {
        super.checkIndentation();
        checkCondExpr();
        checkWrappingIndentation(getMainAst(), getIfStatementRightParen(getMainAst()));
    }

    /**
     * Returns right parenthesis of if statement.
     *
     * @param literalIfAst
     *          literal-if ast node(TokenTypes.LITERAL_IF)
     * @return right parenthesis of if statement.
     */
    private static DetailAST getIfStatementRightParen(DetailAST literalIfAst) {
        return literalIfAst.findFirstToken(TokenTypes.RPAREN);
    }

}
