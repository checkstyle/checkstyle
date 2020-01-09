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

package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <p>
 * Checks nested (internal) classes/interfaces are declared at the bottom of the
 * primary (top-level) class after all method and field declarations.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;InnerTypeLast&quot;/&gt;
 * </pre>
 *
 * @since 5.2
 */
@FileStatefulCheck
public class InnerTypeLastCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "arrangement.members.before.inner";

    /** Meet a root class. */
    private boolean rootClass = true;

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
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        rootClass = true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        // First root class
        if (rootClass) {
            rootClass = false;
        }
        else {
            DetailAST nextSibling = ast.getNextSibling();
            while (nextSibling != null) {
                if (!ScopeUtil.isInCodeBlock(ast)
                    && (nextSibling.getType() == TokenTypes.VARIABLE_DEF
                        || nextSibling.getType() == TokenTypes.METHOD_DEF)) {
                    log(nextSibling, MSG_KEY);
                }
                nextSibling = nextSibling.getNextSibling();
            }
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        // Is this a root class
        if (ast.getParent() == null) {
            rootClass = true;
        }
    }

}
