////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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
 * Handler for if statements.
 *
 * @author jrichard
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
    public IndentLevel suggestedChildLevel(AbstractExpressionHandler child) {
        if (child instanceof ElseHandler) {
            return getLevel();
        }
        return super.suggestedChildLevel(child);
    }

    @Override
    protected IndentLevel getLevelImpl() {
        if (isIfAfterElse()) {
            return getParent().getLevel();
        }
        return super.getLevelImpl();
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
            && parent.getLineNo() == getMainAst().getLineNo();
    }

    @Override
    protected void checkTopLevelToken() {
        if (isIfAfterElse()) {
            return;
        }

        super.checkTopLevelToken();
    }

    /**
     * Check the indentation of the conditional expression.
     */
    private void checkCondExpr() {
        final DetailAST condAst = getMainAst().findFirstToken(TokenTypes.LPAREN)
            .getNextSibling();
        final IndentLevel expected =
            new IndentLevel(getLevel(), getBasicOffset());
        checkExpressionSubtree(condAst, expected, false, false);
    }

    @Override
    public void checkIndentation() {
        super.checkIndentation();
        checkCondExpr();
        final LineWrappingHandler lineWrap =
            new LineWrappingHandler(getIndentCheck(), getMainAst(),
                    getIfStatementRightParen(getMainAst()));
        lineWrap.checkIndentation();
    }

    /**
     * Returns right parenthesis of if statement.
     * @param literalIfAst
     *          literal-if ast node(TokenTypes.LITERAL_IF)
     * @return right parenthesis of if statement.
     */
    private static DetailAST getIfStatementRightParen(DetailAST literalIfAst) {
        return literalIfAst.findFirstToken(TokenTypes.RPAREN);
    }
}
