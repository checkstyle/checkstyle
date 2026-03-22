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

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Say hello.
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
 * {@code say.hello}
 * </li>
 * </ul>
 *
 * @since 1.0.0
 */
@StatelessCheck
public class SayHelloCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "say.hello";

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
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (!hasHelloVariable(ast)) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Checks if a class has a variable named 'hello'.
     *
     * @param ast the class def AST
     * @return true if hello variable exists
     */
    private static boolean hasHelloVariable(DetailAST ast) {
        boolean found = false;
        DetailAST child = ast.getFirstChild();
        while (child != null) {
            if (child.getType() == TokenTypes.VARIABLE_DEF) {
                final DetailAST ident = child.findFirstToken(TokenTypes.IDENT);
                if ("hello".equals(ident.getText())) {
                    found = true;
                    break;
                }
            }
            if (hasHelloVariable(child)) {
                found = true;
                break;
            }
            child = child.getNextSibling();
        }
        return found;
    }
}
