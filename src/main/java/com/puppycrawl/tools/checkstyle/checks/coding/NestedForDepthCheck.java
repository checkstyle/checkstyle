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
 * Restricts nested {@code for} blocks to a specified depth.
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
 * &lt;module name=&quot;NestedForDepth&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * for(int i=0; i&lt;10; i++) {
 *   for(int j=0; j&lt;i; j++) {
 *     for(int k=0; k&lt;j; k++) { // violation, max allowed nested loop number is 1
 *     }
 *   }
 * }
 *
 * for(int i=0; i&lt;10; i++) {
 *   for(int j=0; j&lt;i; j++) { // ok
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to allow nesting depth 2:
 * </p>
 * <pre>
 * &lt;module name=&quot;NestedForDepth&quot;&gt;
 *   &lt;property name=&quot;max&quot; value=&quot;2&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * for(int i=0; i&lt;10; i++) {
 *   for(int j=0; j&lt;i; j++) {
 *     for(int k=0; k&lt;j; k++) {
 *       for(int l=0; l&lt;k; l++) { // violation, max allowed nested loop number is 2
 *       }
 *     }
 *    }
 * }
 *
 * for(int i=0; i&lt;10; i++) {
 *   for(int j=0; j&lt;i; j++) {
 *     for(int k=0; k&lt;j; k++) { // ok
 *     }
 *   }
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
 * {@code nested.for.depth}
 * </li>
 * </ul>
 *
 * @since 5.3
 */
@FileStatefulCheck
public final class NestedForDepthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "nested.for.depth";

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
        return new int[] {TokenTypes.LITERAL_FOR};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        depth = 0;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (depth > max) {
            log(ast, MSG_KEY, depth, max);
        }
        ++depth;
    }

    @Override
    public void leaveToken(DetailAST ast) {
        --depth;
    }

}
