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
 * Checks that the whitespace around the square bracket tokens {@code [} and {@code ]}
 * are correct to the typical convention.
 * The convention is not configurable.
 * </div>
 *
 * <p>
 * Left square bracket ({@code [}):
 * </p>
 * <ul>
 * <li>must not be preceded by whitespace when preceded by a type or identifier.</li>
 * <li>must not be followed by whitespace.</li>
 * </ul>
 *
 * <p>
 * Right square bracket ({@code ]}):
 * </p>
 * <ul>
 * <li>must not be preceded by whitespace.</li>
 * <li>must be followed by whitespace, except when it is followed by:
 *   another bracket, a dot, a comma, a semicolon, postfix operators ({@code ++}, {@code --}),
 *   a right parenthesis, or a method reference ({@code ::}).</li>
 * </ul>
 *
 * @since 10.22.0
 */
@StatelessCheck
public class ArrayBracketWhitespaceCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_PRECEDED = "ws.preceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_FOLLOWED = "ws.followed";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

    /** Left square bracket. */
    private static final char LBRACK = '[';

    /** Left square bracket string. */
    private static final String LBRACK_STR = "[";

    /** Right square bracket. */
    private static final char RBRACK = ']';

    /** Right square bracket string. */
    private static final String RBRACK_STR = "]";

    @Override
    public final int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public final int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.INDEX_OP,
        };
    }

    @Override
    public final void visitToken(final DetailAST ast) {
        processLeftBracket(ast);
        processRightBracket(ast);
    }

    /**
     * Processes a left bracket for whitespace violations.
     *
     * @param ast the left bracket or its parent token
     */
    private void processLeftBracket(final DetailAST ast) {
        final int bracketLineNo = ast.getLineNo();
        final int[] line = getLineCodePoints(bracketLineNo - 1);
        final int bracketColumn = ast.getColumnNo();

        if (bracketColumn > 0
                && CommonUtil.isCodePointWhitespace(line,
                    bracketColumn - 1)) {
            log(ast, MSG_WS_PRECEDED,
                    LBRACK_STR);
        }

        if (bracketColumn + 1 < line.length
                && CommonUtil.isCodePointWhitespace(line,
                    bracketColumn + 1)) {
            log(ast, MSG_WS_FOLLOWED,
                    LBRACK_STR);
        }
    }

    /**
     * Processes a right bracket for whitespace violations.
     *
     * @param ast the right bracket or its parent token
     */
    private void processRightBracket(final DetailAST ast) {
        final DetailAST rbrack = ast.findFirstToken(TokenTypes.RBRACK);
        final int rbrackLineNo = rbrack.getLineNo();
        final int[] line = getLineCodePoints(rbrackLineNo - 1);
        final int rbrackColumn = rbrack.getColumnNo();

        if (rbrackColumn > 0
                && CommonUtil.isCodePointWhitespace(line,
                    rbrackColumn - 1)) {
            log(rbrack, MSG_WS_PRECEDED,
                    RBRACK_STR);
        }

        if (rbrackColumn + 1 < line.length) {
            checkAfterRightBracket(rbrack, rbrackColumn, line, ast);
        }
    }

    /**
     * Check the whitespace after a right bracket.
     *
     * @param ast the right bracket token to check
     * @param column the column of the right bracket
     * @param line the line code points
     * @param parentAst the parent AST token (ARRAY_DECLARATOR or INDEX_OP)
     */
    private void checkAfterRightBracket(final DetailAST ast, final int column,
                                        final int[] line, final DetailAST parentAst) {
        final int nextNonWs = getNextNonWhitespaceIndex(line, column + 1);

        if (nextNonWs != -1) {
            final boolean isWhitespaceAfter =
                    CommonUtil.isCodePointWhitespace(line, column + 1);
            final boolean isException = isException(line, nextNonWs, parentAst);

            if (isException) {
                if (isWhitespaceAfter) {
                    log(ast, MSG_WS_FOLLOWED,
                            RBRACK_STR);
                }
            }
            else if (!isWhitespaceAfter) {
                log(ast, MSG_WS_NOT_FOLLOWED,
                        RBRACK_STR);
            }
        }
    }

    /**
     * Gets the index of the next non-whitespace character in the line.
     *
     * @param line the line code points
     * @param startIndex the index to start searching from
     * @return the index of the next non-whitespace character, or -1 if not found
     */
    private static int getNextNonWhitespaceIndex(final int[] line,
                                                 final int startIndex) {
        int result = -1;
        for (int index = startIndex; index < line.length; index++) {
            if (!CommonUtil.isCodePointWhitespace(line, index)) {
                result = index;
                break;
            }
        }
        return result;
    }

    /**
     * Checks if the character at the specified index is an exception to the
     * "whitespace after right bracket" rule.
     *
     * @param line the line code points
     * @param index the index to check
     * @param parentAst the parent AST token (ARRAY_DECLARATOR or INDEX_OP)
     * @return true if it is an exception
     */
    private static boolean isException(final int[] line, final int index,
                                       final DetailAST parentAst) {
        return isSingleCharException(line[index], parentAst)
                || isDoubleCharException(line, index);
    }

    /**
     * Checks if the character is a single-character exception to the
     * "whitespace after right bracket" rule.
     *
     * @param codePoint the code point to check
     * @param parentAst the parent AST token
     * @return true if it is a single-character exception
     */
    private static boolean isSingleCharException(final int codePoint, final DetailAST parentAst) {
        boolean result = false;
        if (codePoint == LBRACK) {
            result = true;
        }
        else if (codePoint == RBRACK) {
            result = true;
        }
        else if (codePoint == '.') {
            result = true;
        }
        else if (codePoint == ',') {
            result = true;
        }
        else if (codePoint == ';') {
            result = true;
        }
        else if (codePoint == ')') {
            result = true;
        }
        else if (codePoint == '>' && parentAst.getType() == TokenTypes.ARRAY_DECLARATOR) {
            result = true;
        }
        return result;
    }

    /**
     * Checks if the character at the specified index is a two-character exception
     * (like {@code ++}, {@code --}, or generic close {@code &gt;}).
     *
     * @param line the line code points
     * @param index the index to check
     * @return true if it is a two-character exception
     */
    private static boolean isDoubleCharException(final int[] line, final int index) {
        boolean result = false;
        if (index + 1 < line.length) {
            final int charAtIndex = line[index];
            final int nextChar = line[index + 1];

            if (charAtIndex == '+' && nextChar == '+') {
                result = true;
            }
            else if (charAtIndex == '-' && nextChar == '-') {
                result = true;
            }
            else if (charAtIndex == ':' && nextChar == ':') {
                result = true;
            }
        }
        return result;
    }

}
