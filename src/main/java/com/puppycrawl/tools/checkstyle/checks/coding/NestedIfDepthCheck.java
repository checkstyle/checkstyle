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
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * <p>
 * Restricts nested if-else blocks to a specified depth.
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
 * &lt;module name=&quot;NestedIfDepth&quot;/&gt;
 * </pre>
 * <p>Valid code example:</p>
 * <pre>
 * if (true) {
 *     if (true) {} // OK
 *     else {}
 * }
 * </pre>
 * <p>Invalid code example:</p>
 * <pre>
 * if (true) {
 *     if (true) {
 *         if (true) { // violation, nested if-else depth is 2 (max allowed is 1)
 *         }
 *     }
 * }
 * </pre>
 * <p>
 * To configure the check to allow nesting depth 3:
 * </p>
 * <pre>
 * &lt;module name=&quot;NestedIfDepth&quot;&gt;
 *   &lt;property name=&quot;max&quot; value=&quot;3&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Valid code example:</p>
 * <pre>
 * if (true) {
 *    if (true) {
 *       if (true) {
 *          if (true) {} // OK
 *          else {}
 *       }
 *    }
 * }
 * </pre>
 * <p>Invalid code example:</p>
 * <pre>
 * if (true) {
 *    if (true) {
 *       if (true) {
 *          if (true) {
 *             if (true) { // violation, nested if-else depth is 4 (max allowed is 3)
 *                if (true) {} // violation, nested if-else depth is 5 (max allowed is 3)
 *                else {}
 *             }
 *          }
 *       }
 *    }
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
    public void beginTree(DetailAST rootAST) {
        depth = 0;
    }

    @Override
    public void visitToken(DetailAST literalIf) {
        if (!CheckUtil.isElseIf(literalIf)) {
            if (depth > max) {
                log(literalIf, MSG_KEY, depth, max);
            }
            ++depth;
        }
    }

    @Override
    public void leaveToken(DetailAST literalIf) {
        if (!CheckUtil.isElseIf(literalIf)) {
            --depth;
        }
    }

}
