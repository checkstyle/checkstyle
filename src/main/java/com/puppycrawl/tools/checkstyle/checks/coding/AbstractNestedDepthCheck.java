////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Abstract class which provides helpers functionality for nested checks.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 */
public abstract class AbstractNestedDepthCheck extends Check {
    /** maximum allowed nesting depth */
    private int max;
    /** current nesting depth */
    private int depth;

    /**
     * Creates new instance of checks.
     * @param max default allowed nesting depth.
     */
    protected AbstractNestedDepthCheck(int max) {
        setMax(max);
    }

    @Override
    public final int[] getRequiredTokens() {
        return getDefaultTokens();
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        depth = 0;
    }

    /**
     * Setter for maximum allowed nesting depth.
     * @param max maximum allowed nesting depth.
     */
    public final void setMax(int max) {
        this.max = max;
    }

    /**
     * Increasing current nesting depth.
     * @param ast note which increases nesting.
     * @param messageId message id for logging error.
     */
    protected final void nestIn(DetailAST ast, String messageId) {
        if (depth > max) {
            log(ast, messageId, depth, max);
        }
        ++depth;
    }

    /** Decreasing current nesting depth */
    protected final void nestOut() {
        --depth;
    }
}
