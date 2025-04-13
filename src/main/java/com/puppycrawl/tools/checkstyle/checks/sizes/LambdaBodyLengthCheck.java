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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks lambda body length.
 * </div>
 *
 * <p>
 * Rationale: Similar to anonymous inner classes, if lambda body becomes very long
 * it is hard to understand and to see the flow of the method
 * where the lambda is defined. Therefore, long lambda body
 * should usually be extracted to method.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum number of lines allowed.
 * Type is {@code int}.
 * Default value is {@code 10}.
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
 * {@code maxLen.lambdaBody}
 * </li>
 * </ul>
 *
 * @since 8.37
 */
@StatelessCheck
public class LambdaBodyLengthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "maxLen.lambdaBody";

    /** Default maximum number of lines. */
    private static final int DEFAULT_MAX = 10;

    /** Specify the maximum number of lines allowed. */
    private int max = DEFAULT_MAX;

    /**
     * Setter to specify the maximum number of lines allowed.
     *
     * @param length the maximum length of lambda body.
     * @since 8.37
     */
    public void setMax(int length) {
        max = length;
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
        return new int[] {TokenTypes.LAMBDA};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getParent().getType() != TokenTypes.SWITCH_RULE) {
            final int length = getLength(ast);
            if (length > max) {
                log(ast, MSG_KEY, length, max);
            }
        }
    }

    /**
     * Get length of lambda body.
     *
     * @param ast lambda body node.
     * @return length of lambda body.
     */
    private static int getLength(DetailAST ast) {
        final DetailAST lambdaBody = ast.getLastChild();
        final int length;
        if (lambdaBody.getType() == TokenTypes.SLIST) {
            length = lambdaBody.getLastChild().getLineNo() - lambdaBody.getLineNo();
        }
        else {
            length = getLastNodeLineNumber(lambdaBody) - getFirstNodeLineNumber(lambdaBody);
        }
        return length + 1;
    }

    /**
     * Get last child node in the tree line number.
     *
     * @param lambdaBody lambda body node.
     * @return last child node in the tree line number.
     */
    private static int getLastNodeLineNumber(DetailAST lambdaBody) {
        DetailAST node = lambdaBody;
        int result;
        do {
            result = node.getLineNo();
            node = node.getLastChild();
        } while (node != null);
        return result;
    }

    /**
     * Get first child node in the tree line number.
     *
     * @param lambdaBody lambda body node.
     * @return first child node in the tree line number.
     */
    private static int getFirstNodeLineNumber(DetailAST lambdaBody) {
        DetailAST node = lambdaBody;
        int result;
        do {
            result = node.getLineNo();
            node = node.getFirstChild();
        } while (node != null);
        return result;
    }

}
