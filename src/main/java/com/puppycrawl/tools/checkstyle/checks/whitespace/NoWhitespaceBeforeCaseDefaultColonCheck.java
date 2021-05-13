////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks that there is no whitespace before the colon in a switch block.
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
 *         case 3, 4
 *                  : break; // violation, whitespace before ':' is not allowed here
 *         case 4,
 *               5: break; // ok
 *         default
 *               : // violation, whitespace before ':' is not allowed here
 *             break;
 *     }
 *
 *     switch(day) {
 *         case MONDAY, FRIDAY, SUNDAY: System.out.println("  6"); break;
 *         case TUESDAY               : System.out.println("  7"); break; // violation
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
 * {@code ws.preceded.nonPrintableSymbol}
 * </li>
 * </ul>
 *
 * @since 8.43
 */
@StatelessCheck
public class NoWhitespaceBeforeCaseDefaultColonCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "ws.preceded.nonPrintableSymbol";

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
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (isInSwitch(ast) && isWhiteSpaceBeforeColon(ast)) {
            log(ast, MSG_KEY, ast.getText());
        }
    }

    /**
     * Checks if the colon is inside a switch block.
     *
     * @param colonAst DetailAST to check.
     * @return true, if colon case is inside a switch block.
     */
    private static boolean isInSwitch(DetailAST colonAst) {
        return TokenUtil.isOfType(colonAst.getParent(), TokenTypes.LITERAL_CASE,
                TokenTypes.LITERAL_DEFAULT);
    }

    /**
     * Checks if there is a whitespace before the colon of a switch case or switch default.
     *
     * @param colonAst DetailAST to check.
     * @return true, if there is whitespace preceding colonAst.
     */
    private static boolean isWhiteSpaceBeforeColon(DetailAST colonAst) {
        final DetailAST parent = colonAst.getParent();
        final boolean isColonOfCase = parent.getType() == TokenTypes.LITERAL_CASE;
        final boolean isColonOfDefault = parent.getType() == TokenTypes.LITERAL_DEFAULT;
        return isColonOfCase && isWhitespaceBeforeColonOfCase(colonAst)
                || isColonOfDefault && isWhitespaceBeforeColonOfDefault(colonAst)
                || isColonOnDifferentLine(colonAst);
    }

    /**
     * Checks if there is a whitespace before the colon of a switch case.
     *
     * @param colonAst DetailAST to check.
     * @return true, if there is whitespace preceding colonAst.
     */
    private static boolean isWhitespaceBeforeColonOfCase(DetailAST colonAst) {
        final DetailAST previousSibling = colonAst.getPreviousSibling();
        int offset = 0;
        if (previousSibling.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            offset = 1;
        }
        return colonAst.getColumnNo() != getLastColumnNumberOf(previousSibling) + offset;
    }

    /**
     * Checks if there is a whitespace before the colon of a switch default.
     *
     * @param colonAst DetailAST to check.
     * @return true, if there is whitespace preceding colonAst.
     */
    private static boolean isWhitespaceBeforeColonOfDefault(DetailAST colonAst) {
        final boolean horizontalWhitespace;
        final DetailAST commentBlock = colonAst.getPreviousSibling();
        if (commentBlock == null) {
            final DetailAST literalDefault = colonAst.getParent();
            horizontalWhitespace = colonAst.getColumnNo()
                    != literalDefault.getColumnNo() + literalDefault.getText().length();
        }
        else {
            horizontalWhitespace =
                    colonAst.getColumnNo() != getLastColumnNumberOf(commentBlock) + 1;
        }
        return horizontalWhitespace;
    }

    /**
     * Checks if the colon is on same line as of case or default.
     *
     * @param colonAst DetailAST to check.
     * @return true, if colon case is in different line as of case or default.
     */
    private static boolean isColonOnDifferentLine(DetailAST colonAst) {
        final DetailAST previousToken;
        final DetailAST parent = colonAst.getParent();
        if (parent.getType() == TokenTypes.LITERAL_CASE) {
            previousToken = colonAst.getPreviousSibling();
        }
        else {
            previousToken = colonAst.getParent();
        }
        return !TokenUtil.areOnSameLine(previousToken, colonAst);
    }

    /**
     * Returns the last column number of an ast.
     *
     * @param ast DetailAST to check.
     * @return ast's last column number.
     */
    private static int getLastColumnNumberOf(DetailAST ast) {
        DetailAST lastChild = ast;
        while (lastChild.hasChildren()) {
            lastChild = lastChild.getLastChild();
        }
        return lastChild.getColumnNo() + lastChild.getText().length();
    }

}
