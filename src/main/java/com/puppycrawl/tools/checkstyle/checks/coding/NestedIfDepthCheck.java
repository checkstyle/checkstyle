///
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Restricts nested if-else blocks to a specified depth.
 * </div>
 * <ul>
 * <li>
 * Property {@code max} - Specify maximum allowed nesting depth.
 * Type is {@code int}.
 * Default value is {@code 1}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code nested.if.depth}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public final class NestedIfDepthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "nested.if.depth";

    /** Specify maximum allowed nesting depth. */
    private int max = 1;
    /** Current nesting depth. */
    private int depth;

    /**
     * Setter to specify maximum allowed nesting depth.
     *
     * @param max maximum allowed nesting depth.
     * @since 3.2
     */
    public void setMax(int max) {
        this.max = max;
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
        return new int[] {TokenTypes.LITERAL_IF};
    }

    @Override
    public void visitToken(DetailAST literalIf) {
        if (!isElseIf(literalIf)) {
            if (depth > max) {
                log(literalIf, MSG_KEY, depth, max);
            }
            ++depth;
        }
    }

    @Override
    public void leaveToken(DetailAST literalIf) {
        if (!isElseIf(literalIf)) {
            --depth;
        }
    }

    /**
     * Returns whether a token represents an ELSE as part of an ELSE / IF set.
     *
     * @param ast the token to check
     * @return whether it is
     */
    private static boolean isElseIf(DetailAST ast) {
        final DetailAST parentAST = ast.getParent();

        return isElse(parentAST) || isElseWithCurlyBraces(parentAST);
    }

    /**
     * Returns whether a token represents an ELSE.
     *
     * @param ast the token to check
     * @return whether the token represents an ELSE
     */
    private static boolean isElse(DetailAST ast) {
        return ast.getType() == TokenTypes.LITERAL_ELSE;
    }

    /**
     * Returns whether a token represents an SLIST as part of an ELSE
     * statement.
     *
     * @param ast the token to check
     * @return whether the toke does represent an SLIST as part of an ELSE
     */
    private static boolean isElseWithCurlyBraces(DetailAST ast) {
        return ast.getChildCount() == 2 && isElse(ast.getParent());
    }
}
