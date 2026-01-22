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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks that a token is followed by whitespace, with the exception that it
 * does not check for whitespace after the semicolon of an empty for iterator.
 * Use Check
 * <a href="https://checkstyle.org/checks/whitespace/emptyforiteratorpad.html">
 * EmptyForIteratorPad</a> to validate empty for iterators.
 * </div>
 *
 * @since 3.0
 */
@StatelessCheck
public class WhitespaceAfterCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_TYPECAST = "ws.typeCast";

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.TYPECAST,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_YIELD,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.DO_WHILE,
            TokenTypes.ELLIPSIS,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_WHEN,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.TYPECAST,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_YIELD,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.DO_WHILE,
            TokenTypes.ELLIPSIS,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LAMBDA,
            TokenTypes.LITERAL_WHEN,
            TokenTypes.ANNOTATIONS,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.TYPECAST) {
            final DetailAST targetAST = ast.findFirstToken(TokenTypes.RPAREN);
            final int[] line = getLineCodePoints(targetAST.getLineNo() - 1);
            if (!isFollowedByWhitespace(targetAST, line)) {
                log(targetAST, MSG_WS_TYPECAST);
            }
        }
        else if (ast.getType() == TokenTypes.ANNOTATIONS) {
            if (ast.getFirstChild() != null) {
                DetailAST targetAST = ast.getFirstChild().getLastChild();
                if (targetAST.getType() == TokenTypes.DOT) {
                    targetAST = targetAST.getLastChild();
                }
                final int[] line = getLineCodePoints(targetAST.getLineNo() - 1);
                if (!isFollowedByWhitespace(targetAST, line)) {
                    final Object[] message = {targetAST.getText()};
                    log(targetAST, MSG_WS_NOT_FOLLOWED, message);
                }
            }
        }
        else {
            final int[] line = getLineCodePoints(ast.getLineNo() - 1);
            if (!isFollowedByWhitespace(ast, line)) {
                final Object[] message = {ast.getText()};
                log(ast, MSG_WS_NOT_FOLLOWED, message);
            }
        }
    }

    /**
     * Checks whether token is followed by a whitespace.
     *
     * @param targetAST Ast token.
     * @param line Unicode code points array of line associated with the ast token.
     * @return true if ast token is followed by a whitespace.
     */
    private static boolean isFollowedByWhitespace(DetailAST targetAST, int... line) {
        final int after =
            targetAST.getColumnNo() + targetAST.getText().length();
        boolean followedByWhitespace = true;

        if (after < line.length) {
            final int codePoint = line[after];

            followedByWhitespace = codePoint == ';'
                || codePoint == ')'
                || Character.isWhitespace(codePoint);
        }
        return followedByWhitespace;
    }

}
