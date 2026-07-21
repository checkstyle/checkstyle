///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks for over-complicated boolean expressions. Currently, it finds code like
 * {@code if (b == true)}, {@code b || true}, {@code !false},
 * {@code boolean a = q > 12 ? true : false},
 * etc.
 * </div>
 *
 * <p>
 * Rationale: Complex boolean logic makes code hard to understand and maintain.
 * </p>
 *
 * @since 3.0
 */
@StatelessCheck
public class SimplifyBooleanExpressionCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "simplify.expression";

    /**
     * Creates a new {@code SimplifyBooleanExpressionCheck} instance.
     */
    public SimplifyBooleanExpressionCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.LITERAL_TRUE, TokenTypes.LITERAL_FALSE};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        switch (parent.getType()) {
            case TokenTypes.NOT_EQUAL,
                 TokenTypes.EQUAL,
                 TokenTypes.LNOT,
                 TokenTypes.LOR,
                 TokenTypes.LAND -> log(parent, MSG_KEY);

            case TokenTypes.QUESTION -> {
                final DetailAST firstChild = skipParentheses(parent.getFirstChild());
                final DetailAST nextSibling = skipParentheses(ast.getNextSibling());
                if (TokenUtil.isBooleanLiteralType(firstChild.getType())
                        || nextSibling != null
                        && TokenUtil.isBooleanLiteralType(
                                skipParentheses(nextSibling.getNextSibling()).getType())) {
                    log(parent, MSG_KEY);
                }
            }

            default -> {
                // do nothing
            }
        }
    }

    /**
     * Iterates sibling nodes, skipping parentheses.
     *
     * @param node The starting node.
     * @return The first sibling not of type {@code TokenTypes.LPAREN} or
     *     {@code TokenTypes.RPAREN}, or {@code null} if no such node exists.
     */
    private static DetailAST skipParentheses(DetailAST node) {
        DetailAST result = node;
        while (TokenUtil.isOfType(result, TokenTypes.LPAREN, TokenTypes.RPAREN)) {
            result = result.getNextSibling();
        }
        return result;
    }

}
