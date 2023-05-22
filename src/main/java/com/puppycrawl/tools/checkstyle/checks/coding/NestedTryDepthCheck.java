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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Restricts nested try-catch-finally blocks to a specified depth.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify maximum allowed nesting depth.
 * Type is {@code int}.
 * Default value is {@code 1}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;NestedTryDepth&quot;/&gt;
 * </pre>
 * <p>
 *     case 1: Example of code with violation:
 * </p>
 * <pre>
 * try {
 *     try {
 *         try { // violation, current depth is 2, default max allowed depth is 1
 *         } catch (Exception e) {
 *         }
 *     } catch (Exception e) {
 *     }
 * } catch (Exception e) {
 * }
 * </pre>
 * <p>
 *     case 1: Example of compliant code:
 * </p>
 * <pre>
 * try {
 *     try { // OK, current depth is 1, default max allowed depth is also 1
 *     } catch (Exception e) {
 *     }
 * } catch (Exception e) {
 * }
 *         </pre>
 *         <p>case 2: Example of code for handling unique and general exceptions</p>
 *         <pre>
 * try {
 *     try { // OK, current depth is 1, default max allowed depth is also 1
 *             // any more nesting could cause code violation!
 *             throw ArithmeticException();
 *     } catch (ArithmeticException e) { // catches arithmetic exceptions
 *     } catch (NumberFormatException e) { // catches number-format exceptions
 *     } catch (Exception e) { // catches general exceptions other than stated above
 *     }
 * } catch (
 *   ArithmeticException
 *   | NumberFormatException
 *   | ArrayIndexOutOfBoundsException e) { // catches any of the 3 exception
 * } catch (Exception e) { // catches general exception
 * } finally { // do something when try-catch block finished execution
 * }
 * </pre>
 * <p>
 * To configure the check to allow nesting depth 3:
 * </p>
 * <pre>
 * &lt;module name=&quot;NestedTryDepth&quot;&gt;
 *   &lt;property name=&quot;max&quot; value=&quot;3&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 *     Example of code with violation:
 * </p>
 * <pre>
 * try {
 *     try {
 *         try {
 *             try {
 *                 try { // violation, current depth is 4, max allowed depth is 3
 *                 } catch (Exception e) {
 *                 }
 *             } catch (Exception e) {
 *             }
 *         } catch (Exception e) {
 *         }
 *     } catch (Exception e) {
 *     }
 * } catch (Exception e) {
 * }
 * </pre>
 * <p>
 *      Example of compliant code:
 * </p>
 * <pre>
 * try {
 *     try {
 *         try {
 *             try { // OK, current depth is 3, max allowed depth is also 3
 *             } catch (Exception e) {
 *             }
 *         } catch (Exception e) {
 *         }
 *     } catch (Exception e) {
 *     }
 * } catch (Exception e) {
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
 * {@code nested.try.depth}
 * </li>
 * </ul>
 *
 * @since 3.2
 */
@FileStatefulCheck
public final class NestedTryDepthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "nested.try.depth";

    /** Specify maximum allowed nesting depth. */
    private int max = 1;
    /** Current nesting depth. */
    private int depth;

    /**
     * Setter to specify maximum allowed nesting depth.
     *
     * @param max maximum allowed nesting depth.
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
        return new int[] {TokenTypes.LITERAL_TRY};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        depth = 0;
    }

    @Override
    public void visitToken(DetailAST literalTry) {
        if (depth > max) {
            log(literalTry, MSG_KEY, depth, max);
        }
        ++depth;
    }

    @Override
    public void leaveToken(DetailAST literalTry) {
        --depth;
    }

}
