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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that there is no whitespace before a colon token of a switch case or default.
 * </p>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;NoWhitespaceBeforeCaseDefaultColon&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class Test {
 *   {
 *     switch(1) {
 *         case 1 : // violation, whitespace before ':' is not allowed here
 *             break;
 *         case 2: // OK
 *             break;
 *         default : // violation, whitespace before ':' is not allowed here
 *             break;
 *     }
 *
 *     switch(2) {
 *         case 2: // OK
 *             break;
 *         default: // OK
 *             break;
 *     }
 *   }
 * }
 * </pre>
 *
 * @since 8.32
 */
@StatelessCheck
public class NoWhitespaceBeforeCaseDefaultColonCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "ws.preceded";

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
        return new int[] {TokenTypes.COLON};
    }

    @Override
    public void visitToken(DetailAST ast) {
        final String line = getLine(ast.getLineNo() - 1);
        final int before = ast.getColumnNo() - 1;
        if (isColonOfCaseOrDefault(ast) && (before == -1
                || Character.isWhitespace(line.charAt(before)))) {
            log(ast, MSG_KEY, ast.getText());
        }
    }

    /**
     * Checks that ast is a colon of switch case or default.
     * @param colonAst DetailAST to check.
     * @return true if ast is a COLON and is child of LITERAL_CASE or LITERAL_DEFAULT.
     */
    private static boolean isColonOfCaseOrDefault(DetailAST colonAst) {
        final DetailAST parent = colonAst.getParent();
        return parent.getType() == TokenTypes.LITERAL_CASE
                || parent.getType() == TokenTypes.LITERAL_DEFAULT;
    }

}
