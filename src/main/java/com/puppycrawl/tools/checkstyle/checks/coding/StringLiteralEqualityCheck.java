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

/**
 * <div>
 * Checks that string literals are not used with <code>==</code> or <code>&#33;=</code>.
 * Since <code>==</code> will compare the object references, not the actual value of the strings,
 * <code>String.equals()</code> should be used.
 * </div>
 */
@StatelessCheck
public class StringLiteralEqualityCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties".
     */
    public static final String MSG_KEY = "string.literal.equality";

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
        return new int[] {TokenTypes.EQUAL, TokenTypes.NOT_EQUAL};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST left = ast.getFirstChild();
        final DetailAST right = left.getNextSibling();

        // Detect string literal even inside nested expressions
        if ((containsStringLiteral(left) && !isStringLiteralExpression(right))
                || (containsStringLiteral(right) && !isStringLiteralExpression(left))) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Recursively checks whether AST contains a string literal.
     *
     * @param ast AST node
     * @return true if string literal exists
     */
    private static boolean containsStringLiteral(DetailAST ast) {
        if (ast == null) {
            return false;
        }

        if (ast.getType() == TokenTypes.STRING_LITERAL) {
            return true;
        }

        DetailAST child = ast.getFirstChild();
        while (child != null) {
            if (containsStringLiteral(child)) {
                return true;
            }
            child = child.getNextSibling();
        }

        return false;
    }

    /**
     * Checks whether expression is purely composed of string literals
     * (including concatenation).
     *
     * @param ast AST node
     * @return true if expression is string literal expression
     */
    private static boolean isStringLiteralExpression(DetailAST ast) {
        if (ast == null) {
            return false;
        }

        final int type = ast.getType();

        // Direct literal
        if (type == TokenTypes.STRING_LITERAL
                || type == TokenTypes.TEXT_BLOCK_LITERAL_BEGIN) {
            return true;
        }

        // Concatenation: both sides must be literals
        if (type == TokenTypes.PLUS) {
            final DetailAST left = ast.getFirstChild();
            final DetailAST right = left.getNextSibling();

            return isStringLiteralExpression(left)
                    && isStringLiteralExpression(right);
        }

        return false;
    }
}