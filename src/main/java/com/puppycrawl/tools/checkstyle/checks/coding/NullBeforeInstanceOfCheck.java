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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks if unnecessary null check is used before instanceof expression.
 * </p>
 * <ul>
 * <li>
 * Property {@code tokens} - tokens to check
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">
 * LITERAL_IF</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;NullBeforeInstanceOf&quot;/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class Test {
 *
 *     if (myObj != null &amp;&amp; myObj instanceof Object) { // violation
 *
 *     }
 *
 *     if (myObj != null) { // violation
 *         if (myObj instanceof Object) {
 *
 *         }
 *     }
 *
 *    if ((myObj != null) &amp;&amp; (myObj instanceof Object)) {}  // violation
 * }
 * </pre>
 * @since 8.33
 */
@StatelessCheck
public class NullBeforeInstanceOfCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message null.before.instanceof in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "null.before.instanceof";

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.LITERAL_IF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST landNode = ast.findFirstToken(TokenTypes.EXPR)
                .findFirstToken(TokenTypes.LAND);
        if (landNode == null) {
            if (checkOnNextLine(ast)) {
                log(ast, MSG_KEY);
            }
        }
        else {
            final DetailAST notEqualNode = landNode.findFirstToken(TokenTypes.NOT_EQUAL);
            final DetailAST instanceOfNode =
                    landNode.findFirstToken(TokenTypes.LITERAL_INSTANCEOF);
            if (notEqualNode != null && instanceOfNode != null
                    && getText(notEqualNode).equals(getText(instanceOfNode))) {
                log(ast, MSG_KEY);
            }
        }
    }

    private static String getText(DetailAST ast) {
        return ast.findFirstToken(TokenTypes.IDENT).getText();
    }

    /**
     * Check if there is a unnecessary 'null' check before 'instanceof' expression when
     * 'instanceof' expression is on next line of 'null' check.
     *
     * @param ast DetailAST ast
     * @return error boolean
     */
    private static boolean checkOnNextLine(DetailAST ast) {
        final DetailAST notEqualNode = ast.findFirstToken(TokenTypes.EXPR)
                .findFirstToken(TokenTypes.NOT_EQUAL);
        boolean error = false;
        if (notEqualNode != null
                && notEqualNode.findFirstToken(TokenTypes.LITERAL_NULL) != null) {
            final DetailAST slistNode = ast.findFirstToken(TokenTypes.SLIST);
            if (slistNode != null) {
                final DetailAST nestedIfNode = slistNode.findFirstToken(TokenTypes.LITERAL_IF);
                if (nestedIfNode != null) {
                    final DetailAST instanceOfNode =
                            nestedIfNode.findFirstToken(TokenTypes.EXPR)
                                    .findFirstToken(TokenTypes.LITERAL_INSTANCEOF);
                    if (instanceOfNode != null
                            && getText(notEqualNode).equals(getText(instanceOfNode))) {
                        error = true;
                    }
                }
            }
        }
        return error;
    }
}
