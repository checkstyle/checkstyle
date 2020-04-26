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
        final DetailAST landNode = getExprChildNode(ast, TokenTypes.LAND);
        if (landNode == null) {
            if (checkOuterNotEqual(ast) || checkOuterInstanceOf(ast)) {
                log(ast, MSG_KEY);
            }
        }
        else {
            final DetailAST notEqualNode = landNode.findFirstToken(TokenTypes.NOT_EQUAL);
            final DetailAST instanceOfNode =
                    landNode.findFirstToken(TokenTypes.LITERAL_INSTANCEOF);
            if (instanceOfNode != null && isNullTest(notEqualNode)
                    && isTextEqual(instanceOfNode, notEqualNode)) {
                log(ast, MSG_KEY);
            }
        }
    }

    /**
     * To get {@link TokenTypes#LITERAL_INSTANCEOF} Node or
     * {@link TokenTypes#NOT_EQUAL}'s full ident text.
     *
     * @param ast DetailAST
     * @return string ident node's text
     */
    private static String getFullText(DetailAST ast) {
        final DetailAST dotNode = ast.findFirstToken(TokenTypes.DOT);
        final String fullText;
        if (dotNode == null) {
            fullText = ast.findFirstToken(TokenTypes.IDENT).getText();
        }
        else {
            final FullIdent fullIdent = FullIdent.createFullIdent(dotNode);
            fullText = fullIdent.getText();
        }
        return fullText;
    }

    private static boolean checkOuterNotEqual(DetailAST ast) {
        final DetailAST outerNotEqualNode = getExprChildNode(ast, TokenTypes.NOT_EQUAL);
        boolean error = false;
        if (isNullTest(outerNotEqualNode)) {
            final DetailAST nestedIfNode = getNestedIfNode(ast);
            if (nestedIfNode != null) {
                final DetailAST instanceOfNode =
                        getExprChildNode(nestedIfNode, TokenTypes.LITERAL_INSTANCEOF);
                if (instanceOfNode != null) {
                    error = isTextEqual(instanceOfNode, outerNotEqualNode);
                }
            }
        }
        return error;
    }

    private static boolean checkOuterInstanceOf(DetailAST ast) {
        final DetailAST outerInstanceOfNode =
                getExprChildNode(ast, TokenTypes.LITERAL_INSTANCEOF);
        boolean error = false;
        if (outerInstanceOfNode != null) {
            final DetailAST nestedIfNode = getNestedIfNode(ast);
            if (nestedIfNode != null) {
                final DetailAST notEqualNode =
                        getExprChildNode(nestedIfNode, TokenTypes.NOT_EQUAL);
                if (isNullTest(notEqualNode)) {
                    error = isTextEqual(outerInstanceOfNode, notEqualNode);
                }
            }
        }
        return error;
    }

    /**
     * To get specific Node that is child of {@link TokenTypes#EXPR} Node.
     *
     * @param ast DetailAST
     * @param token int
     * @return DetailAST
     */
    private static DetailAST getExprChildNode(DetailAST ast, int token) {
        return ast.findFirstToken(TokenTypes.EXPR)
                .findFirstToken(token);
    }

    /**
     * True if nodes have a {@link TokenTypes#LITERAL_NULL}, false otherwise.
     *
     * @param ast DetailAST
     * @return boolean
     */
    private static boolean isNullTest(DetailAST ast) {
        return ast != null
                && ast.findFirstToken(TokenTypes.LITERAL_NULL) != null;
    }

    /**
     * Gets nested {@link TokenTypes#LITERAL_IF} node.
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
     * True if nodes have same texts, false otherwise.
     *
     * @param instanceOfNode DetailAST
     * @param notEqualNode DetailAST
     * @return error boolean
     */
    private static boolean isTextEqual(DetailAST instanceOfNode, DetailAST notEqualNode) {
        return getFullText(instanceOfNode).equals(getFullText(notEqualNode));
    }
}
