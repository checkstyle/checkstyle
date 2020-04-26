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
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks if unnecessary null check is used around instanceof expression.
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
 * &lt;module name=&quot;NullTestAroundInstanceOf&quot;/&gt;
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
 *
 * @since 8.34
 */
@StatelessCheck
public class NullTestAroundInstanceOfCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message null.before.instanceof in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "null.test.around.instanceof";

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
                    && notEqualNode.findFirstToken(TokenTypes.LITERAL_NULL) != null
                    && getIdentNodeText(notEqualNode).equals(getIdentNodeText(instanceOfNode))) {
                log(ast, MSG_KEY);
            }
        }
    }

    /**
     * To get instanceOfNode or notEqualNode's full ident text.
     *
     * @param ast DetailAST
     * @return string ident node's text
     */
    private static String getIdentNodeText(DetailAST ast) {
        final DetailAST dotNode = ast.findFirstToken(TokenTypes.DOT);
        final String string;
        if (dotNode == null) {
            string = ast.findFirstToken(TokenTypes.IDENT).getText();
        }
        else {
            final FullIdent fullIdent = FullIdent.createFullIdent(dotNode);
            string = fullIdent.getText();
        }
        return string;
    }

    /**
     * Check if there is a unnecessary 'null' check before 'instanceof' expression when
     * 'instanceof' expression is on next line of 'null' check and vice versa.
     *
     * @param ast DetailAST
     * @return error boolean
     */
    private static boolean checkOnNextLine(DetailAST ast) {
        final DetailAST outerNotEqualNode = ast.findFirstToken(TokenTypes.EXPR)
                .findFirstToken(TokenTypes.NOT_EQUAL);
        boolean error = false;
        if (outerNotEqualNode != null
                && outerNotEqualNode.findFirstToken(TokenTypes.LITERAL_NULL) != null) {
            final DetailAST nestedIfNode = getNestedIfNode(ast);
            if (nestedIfNode != null) {
                final DetailAST instanceOfNode =
                        nestedIfNode.findFirstToken(TokenTypes.EXPR)
                                .findFirstToken(TokenTypes.LITERAL_INSTANCEOF);
                if (instanceOfNode != null) {
                    error = isNodeTextEqual(instanceOfNode, outerNotEqualNode);
                }
            }
        }

        final DetailAST outerInstanceOfNode = ast.findFirstToken(TokenTypes.EXPR)
                .findFirstToken(TokenTypes.LITERAL_INSTANCEOF);
        if (outerInstanceOfNode != null) {
            final DetailAST nestedIfNode = getNestedIfNode(ast);
            if (nestedIfNode != null) {
                final DetailAST notEqualNode =
                        nestedIfNode.findFirstToken(TokenTypes.EXPR)
                                .findFirstToken(TokenTypes.NOT_EQUAL);
                if (notEqualNode != null
                        && notEqualNode.findFirstToken(TokenTypes.LITERAL_NULL) != null) {
                    error = isNodeTextEqual(outerInstanceOfNode, notEqualNode);
                }
            }
        }

        return error;
    }

    /**
     * To get LITERAL_IF Node that is nested.
     *
     * @param ast DetailAST
     * @return nestedIfNode DetailAST
     */
    private static DetailAST getNestedIfNode(DetailAST ast) {
        final DetailAST slistNode = ast.findFirstToken(TokenTypes.SLIST);
        final DetailAST nestedIfNode;
        if (slistNode == null) {
            nestedIfNode = ast.findFirstToken(TokenTypes.LITERAL_IF);
        }
        else {
            nestedIfNode = slistNode.findFirstToken(TokenTypes.LITERAL_IF);
        }
        return nestedIfNode;
    }

    /**
     * Judge whether the text of the two nodes is the same.
     *
     * @param instanceOfNode DetailAST
     * @param notEqualNode DetailAST
     * @return error boolean
     */
    private static Boolean isNodeTextEqual(DetailAST instanceOfNode, DetailAST notEqualNode) {
        boolean error = false;
        if (getIdentNodeText(instanceOfNode).equals(getIdentNodeText(notEqualNode))) {
            error = true;
        }
        return error;
    }
}
