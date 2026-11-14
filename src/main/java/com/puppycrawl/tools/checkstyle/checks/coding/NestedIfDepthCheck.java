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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Restricts nested if-else blocks to a specified depth.
 * </div>
 *
 * @since 3.2
 */
@FileStatefulCheck
public final class NestedIfDepthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "nested.if.depth";

    /** Specify maximum allowed nesting depth. */
    private int max = 1;
    /** Current nesting depth. */
    private int depth;

    /**
     * Creates a new {@code NestedIfDepthCheck} instance.
     */
    public NestedIfDepthCheck() {
        // no code by default
    }

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
     * An {@code if} is a continuation of the same if-else chain when it is a direct
     * child of an {@code else} token, or when it is the only statement in the block
     * of an {@code else} statement and has no {@code else} branch of its own, which
     * is the same code as an unbraced else-if. An {@code if} with its own
     * {@code else} branch inside an {@code else} block is a complete nested if-else
     * statement and counts towards the depth.
     *
     * @param ast the token to check
     * @return whether it is
     */
    private static boolean isElseIf(DetailAST ast) {
        final DetailAST parentAST = ast.getParent();

        return isElse(parentAST) || isElseWithCurlyBraces(parentAST, ast);
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
     * Returns whether a token represents an SLIST as part of an ELSE statement
     * and the given {@code if} is a continuation of the if-else chain in it.
     *
     * @param ast the SLIST token to check
     * @param literalIf the {@code if} token inside the SLIST
     * @return whether the {@code if} is an else-if continuation in the block
     */
    private static boolean isElseWithCurlyBraces(DetailAST ast, DetailAST literalIf) {
        return ast.getChildCount() == 2 && isElse(ast.getParent())
            && literalIf.getChildCount(TokenTypes.LITERAL_ELSE) == 0;
    }

}
