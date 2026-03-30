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
 * @since 13.9.0
 */
@StatelessCheck
public class ArrayBracketWhitespaceCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_WS_PRECEDED = "ws.preceded";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_WS_FOLLOWED = "ws.followed";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

    /** Left square bracket. */
    private static final char LBRACK = '[';

    /** Right square bracket. */
    private static final char RBRACK = ']';

    /** Dot. */
    private static final int DOT = '.';

    /** Comma. */
    private static final int COMMA = ',';

    /** Semicolon. */
    private static final int SEMICOLON = ';';

    /** Right parenthesis. */
    private static final int RPAREN = ')';

    /** Greater than. */
    private static final int GREATER_THAN = '>';

    /** Plus. */
    private static final int PLUS = '+';

    /** Minus. */
    private static final int MINUS = '-';

    /** Colon. */
    private static final int COLON = ':';

    /** Right curly bracket. */
    private static final int RCURLY = '}';

    /**
     * Creates a new {@code ArrayBracketWhitespaceCheck} instance.
     */
    public ArrayBracketWhitespaceCheck() {
        // no code by default
    }

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

        final boolean isWhitespaceBefore = bracketColumn > 0
                && CommonUtil.isCodePointWhitespace(line, bracketColumn - 1);
        if (isWhitespaceBefore && !isAnnotationBeforeBracket(ast)) {
            log(ast, MSG_WS_PRECEDED, LBRACK);
        }

        final boolean isWhitespaceAfter = bracketColumn + 1 < line.length
                && CommonUtil.isCodePointWhitespace(line, bracketColumn + 1);
        if (isWhitespaceAfter) {
            log(ast, MSG_WS_FOLLOWED, LBRACK);
        }
    }

    /**
     * Checks whether the previous sibling of a token is an annotation.
     *
     * @param ast the token to check
     * @return true if the previous sibling is an annotation
     */
    private static boolean isAnnotationBeforeBracket(final DetailAST ast) {
        final DetailAST previousSibling = ast.getPreviousSibling();
        return previousSibling != null
                && previousSibling.getType() == TokenTypes.ANNOTATIONS;
    }

    /**
     * Processes a right bracket for whitespace violations.
     *
     * @param ast the right bracket or its parent token
     */
    private void processRightBracket(final DetailAST ast) {
        final DetailAST rbrack = ast.getLastChild();
        final int rbrackLineNo = rbrack.getLineNo();
        final int[] line = getLineCodePoints(rbrackLineNo - 1);
        final int rbrackColumn = rbrack.getColumnNo();

        if (rbrackColumn > 0
                && CommonUtil.isCodePointWhitespace(line,
                    rbrackColumn - 1)) {
            log(rbrack, MSG_WS_PRECEDED, RBRACK);
        }

        checkAfterRightBracket(rbrack, rbrackColumn, line, ast);
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
            final boolean shouldFollow = shouldFollowRightBracket(line, nextNonWs, parentAst);

            if (shouldFollow) {
                if (isWhitespaceAfter) {
                    log(ast, MSG_WS_FOLLOWED, RBRACK);
                }
            }
            else if (!isWhitespaceAfter) {
                log(ast, MSG_WS_NOT_FOLLOWED, RBRACK);
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
    private static boolean shouldFollowRightBracket(final int[] line, final int index,
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
        final boolean isSimpleException = codePoint == LBRACK
                || codePoint == RBRACK
                || codePoint == DOT
                || codePoint == COMMA
                || codePoint == SEMICOLON
                || codePoint == RPAREN
                || codePoint == RCURLY;
        return isSimpleException
                || codePoint == GREATER_THAN && parentAst.getType() == TokenTypes.ARRAY_DECLARATOR;
    }

    /**
     * Checks if the character at the specified index is a two-character exception
     * (like {@code ++}, {@code --}, or {@code ::}).
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

            if (charAtIndex == nextChar) {
                result = charAtIndex == PLUS
                        || charAtIndex == MINUS
                        || charAtIndex == COLON;
            }
        }
        return result;
    }

}
