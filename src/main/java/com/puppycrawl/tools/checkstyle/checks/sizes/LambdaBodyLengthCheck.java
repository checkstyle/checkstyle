////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks lambda body length.
 * </p>
 * <p>
 * Rationale: Similar to anonymous inner classes, if lambda body becomes very long
 * it is hard to understand and to see the flow of the method
 * where the lambda is defined. Therefore long lambda body
 * should usually be extracted to method.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum number of lines allowed.
 * Type is {@code int}.
 * Default value is {@code 10}.
 * </li>
 * </ul>
 * <p>
 * To configure the check to accept lambda bodies with up to 10 lines:
 * </p>
 * <pre>
 * &lt;module name="LambdaBodyLength"/&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   Runnable r = () -> { // violation, 11 lines
 *       System.out.println(2); // line 2
 *       System.out.println(3);
 *       System.out.println(4);
 *       System.out.println(5);
 *       System.out.println(6);
 *       System.out.println(7);
 *       System.out.println(8);
 *       System.out.println(9);
 *       System.out.println(10);
 *   }; // line 11
 *
 *   Runnable r2 = () -> // violation, 11 lines
 *       "someString".trim() // line 1
 *                   .trim()
 *                   .trim()
 *                   .trim()
 *                   .trim()
 *                   .trim()
 *                   .trim()
 *                   .trim()
 *                   .trim()
 *                   .trim()
 *                   .trim(); // line 11
 *
 *   Runnable r3 = () -> { // ok, 10 lines
 *       System.out.println(2); // line 2
 *       System.out.println(3);
 *       System.out.println(4);
 *       System.out.println(5);
 *       System.out.println(6);
 *       System.out.println(7);
 *       System.out.println(8);
 *       System.out.println(9);
 *   }; // line 10
 * }
 * </pre>
 * <p>
 * To configure the check to accept lambda bodies with max 5 lines:
 * </p>
 * <pre>
 * &lt;module name="LambdaBodyLength"&gt;
 *   &lt;property name="max" value="5"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class Test {
 *   Runnable r = () -> { // violation, 6 lines
 *       System.out.println(2); // line 2
 *       System.out.println(3);
 *       System.out.println(4);
 *       System.out.println(5);
 *   };
 *
 *   Runnable r2 = () -> // violation, 6 lines
 *       "someString".trim()
 *                   .trim()
 *                   .trim()
 *                   .trim()
 *                   .trim()
 *                   .trim();
 *
 *   Runnable r3 = () -> { // ok, 5 lines
 *       System.out.println(2);
 *       System.out.println(3);
 *       System.out.println(4);
 *   };
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
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

    private int getLength(DetailAST ast) {
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
     * Get last node line number.
     *
     * @param lambdaBody lambda body node.
     * @return last node line number.
     */
    private static int getLastNodeLineNumber(DetailAST lambdaBody) {
        DetailAST node = lambdaBody.getLastChild();
        int result = node.getLineNo();
        while (node != null) {
            result = node.getLineNo();
            node = node.getLastChild();
        }
        return result;
    }

    /**
     * Get first node line number.
     *
     * @param lambdaBody lambda body node.
     * @return first node line number.
     */
    private static int getFirstNodeLineNumber(DetailAST lambdaBody) {
        DetailAST node = lambdaBody.getFirstChild();
        int result = node.getLineNo();
        while (node != null) {
            result = node.getLineNo();
            node = node.getFirstChild();
        }
        return result;
    }

    /**
     * Setter to specify the maximum number of lines allowed.
     *
     * @param length the maximum length of lambda body.
     */
    public void setMax(int length) {
        max = length;
    }

}
