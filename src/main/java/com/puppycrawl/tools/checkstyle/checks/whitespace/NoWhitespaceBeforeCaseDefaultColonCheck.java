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
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

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
 * @since 8.33
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
    public boolean isCommentNodesRequired() {
        return true;
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        final boolean astIsColonOfCase = parent.getType() == TokenTypes.LITERAL_CASE;
        final boolean astIsColonOfDefault = parent.getType() == TokenTypes.LITERAL_DEFAULT;
        final boolean astIsColonOfCaseOrDefault = astIsColonOfCase || astIsColonOfDefault;
        final boolean onSameLine = TokenUtil.areOnSameLine(parent, ast);
        final boolean violation = astIsColonOfCaseOrDefault
                && (!onSameLine
                || astIsColonOfCase && checkWhitespaceBeforeColonOfCase(ast)
                || astIsColonOfDefault && checkWhitespaceBeforeColonOfDefault(ast));

        if (violation) {
            log(ast, MSG_KEY, ast.getText());
        }
    }

    /**
     * Checks if there is a whitespace before the colon of a switch case.
     * @param colonAst DetailAST to check.
     * @return whether there is a whitespace before colonAst or not.
     */
    private static boolean checkWhitespaceBeforeColonOfCase(DetailAST colonAst) {
        final DetailAST previousSibling = colonAst.getPreviousSibling();
        int offset = 0;
        if (previousSibling.getType() == TokenTypes.BLOCK_COMMENT_BEGIN) {
            offset = 1;
        }
        return colonAst.getColumnNo() != getLastColumnOf(previousSibling) + offset;
    }

    /**
     * Checks if there is a whitespace before the colon of a switch default.
     * @param colonAst DetailAST to check.
     * @return whether there is a whitespace before colonAst or not.
     */
    private static boolean checkWhitespaceBeforeColonOfDefault(DetailAST colonAst) {
        final boolean whitespaceDetected;
        final DetailAST commentBlock = colonAst.getPreviousSibling();
        if (commentBlock == null) {
            final DetailAST literalDefault = colonAst.getParent();
            whitespaceDetected = colonAst.getColumnNo()
                    != literalDefault.getColumnNo() + literalDefault.getText().length();
        }
        else {
            whitespaceDetected = colonAst.getColumnNo() != getLastColumnOf(commentBlock) + 1;
        }
        return whitespaceDetected;
    }

    /**
     * Returns the last column of an ast.
     * @param ast DetailAST to check.
     * @return ast's last column.
     */
    private static int getLastColumnOf(DetailAST ast) {
        DetailAST lastChild = ast;
        while (lastChild.hasChildren()) {
            lastChild = lastChild.getLastChild();
        }
        return lastChild.getColumnNo() + lastChild.getText().length();
    }

}
