////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Check the number of nested {@code for} -statements. The maximum
 * number of nested layers can be configured. The default value is 1.
 * The code for the class is copied from the NestedIfDepthCheck-class.
 * The only difference is the intercepted token (for instead of if).
 * Example:
 * <pre>
 *  &lt;!-- Restricts nested for blocks to a specified depth (default = 1).
 *                                                                        --&gt;
 *  &lt;module name=&quot;com.puppycrawl.tools.checkstyle.checks.coding
 *                                            .CatchWithLostStackCheck&quot;&gt;
 *    &lt;property name=&quot;severity&quot; value=&quot;info&quot;/&gt;
 *    &lt;property name=&quot;max&quot; value=&quot;1&quot;/&gt;
 *  &lt;/module&gt;
 * </pre>
 * @author Alexander Jesse
 * @see NestedIfDepthCheck
 */
public final class NestedForDepthCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "nested.for.depth";

    /** Maximum allowed nesting depth. */
    private int max = 1;
    /** Current nesting depth. */
    private int depth;

    /**
     * Setter for maximum allowed nesting depth.
     * @param max maximum allowed nesting depth.
     */
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.LITERAL_FOR};
    }

    @Override
    public int[] getRequiredTokens() {
        return getAcceptableTokens();
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
