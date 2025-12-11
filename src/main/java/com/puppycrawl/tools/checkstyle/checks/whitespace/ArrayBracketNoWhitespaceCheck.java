///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
 * Checks that the whitespace around square-bracket tokens {@code [} and {@code ]}
 * follows the typical Java convention for array declarations, array creation,
 * and array indexing.
 * </div>
 *
 * <p>
 * Whitespace is defined by implementation of
 * {@link Character#isWhitespace(char)}.
 * </p>
 *
 * <p>
 * Left square bracket ("{@code [}"):
 * </p>
 * <ul>
 * <li>must not be preceded with whitespace when preceded by a
 *   {@code TYPE} or {@code IDENT} in array declarations or array access</li>
 * <li>must not be followed with whitespace</li>
 * </ul>
 *
 * <p>
 * Right square bracket ("{@code ]}"):
 * </p>
 * <ul>
 * <li>must not be preceded with whitespace</li>
 * <li>must be followed with whitespace in all cases, except when followed by:
 *   <ul>
 *     <li>another bracket: {@code [][]}</li>
 *     <li>a dot for member access: {@code arr[i].length}</li>
 *     <li>a comma or semicolon: {@code arr[i],} or {@code arr[i];}</li>
 *     <li>postfix operators: {@code arr[i]++} or {@code arr[i]--}</li>
 *     <li>a right parenthesis or another closing construct: {@code (arr[i])}</li>
 *   </ul>
 * </li>
 * </ul>
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
 * {@code ws.followed}
 * </li>
 * <li>
 * {@code ws.notFollowed}
 * </li>
 * <li>
 * {@code ws.preceded}
 * </li>
 * </ul>
 *
 * @since 10.20.0
 */
@StatelessCheck
public class ArrayBracketNoWhitespaceCheck extends AbstractCheck {

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

    /** Open square bracket literal. */
    private static final String LEFT_BRACKET = "[";

    /** Close square bracket literal. */
    private static final String RIGHT_BRACKET = "]";

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
        return new int[] {
            TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.INDEX_OP,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        processLeftBracket(ast);

        // Find the RBRACK child and process it
        final DetailAST rightBracket = ast.findFirstToken(TokenTypes.RBRACK);
        processRightBracket(rightBracket);
    }

    /**
     * Checks the left square bracket for whitespace violations.
     * For ARRAY_DECLARATOR and INDEX_OP tokens, this checks whitespace
     * BEFORE and AFTER the token position (which represents the {@code [}).
     *
     * @param leftBracket the ARRAY_DECLARATOR or INDEX_OP token (represents '[')
     */
    private void processLeftBracket(DetailAST leftBracket) {
        final int[] line = getLineCodePoints(leftBracket.getLineNo() - 1);
        final int columnNo = leftBracket.getColumnNo();
        final int before = columnNo - 1;
        final int after = columnNo + 1;

        // Check if preceded by whitespace
        if (before >= 0 && CommonUtil.isCodePointWhitespace(line, before)) {
            log(leftBracket, MSG_WS_PRECEDED, LEFT_BRACKET);
        }

        // Check if followed by whitespace
        if (after < line.length && CommonUtil.isCodePointWhitespace(line, after)) {
            log(leftBracket, MSG_WS_FOLLOWED, LEFT_BRACKET);
        }
    }

    /**
     * Checks the right square bracket for whitespace violations.
     *
     * @param rightBracket the right bracket token
     */
    private void processRightBracket(DetailAST rightBracket) {
        final int[] line = getLineCodePoints(rightBracket.getLineNo() - 1);
        final int columnNo = rightBracket.getColumnNo();
        final int before = columnNo - 1;
        final int after = columnNo + 1;

        // Check if preceded by whitespace
        if (before >= 0 && CommonUtil.isCodePointWhitespace(line, before)) {
            log(rightBracket, MSG_WS_PRECEDED, RIGHT_BRACKET);
        }

        // Check if it should be followed by whitespace
        if (after < line.length) {
            final boolean hasWhitespace = CommonUtil.isCodePointWhitespace(line, after);
            final int charIdxToCheck;
            if (hasWhitespace) {
                charIdxToCheck = after + 1;
            }
            else {
                charIdxToCheck = after;
            }

            if (charIdxToCheck < line.length) {
                final int codePoint = line[charIdxToCheck];

                final boolean isValidWithoutWhitespace =
                    isCharacterValidAfterRightBracket(codePoint, line, charIdxToCheck);

                if (hasWhitespace && isValidWithoutWhitespace) {
                    log(rightBracket, MSG_WS_FOLLOWED, RIGHT_BRACKET);
                }
                else if (!hasWhitespace && !isValidWithoutWhitespace) {
                    log(rightBracket, MSG_WS_NOT_FOLLOWED, RIGHT_BRACKET);
                }
            }
        }
    }

    /**
     * Checks whether the given character is valid to be right after a right bracket
     * without requiring whitespace.
     *
     * @param codePoint code point to check
     * @param line the unicode code points array of the line
     * @param position the position of the code point in the line
     * @return true if the character is valid after {@code ]} without whitespace
     */
    private static boolean isCharacterValidAfterRightBracket(int codePoint, int[] line,
                                                               int position) {
        final char charAfter = (char) codePoint;

        return isAlwaysValidCharAfterRightBracket(charAfter)
            || isMethodReferenceOrPostfixOperator(charAfter, line, position)
            || isAngleBracketOrShiftOperator(charAfter, line, position);
    }

    /**
     * Checks if the character after {@code ]} is always allowed without whitespace.
     *
     * @param charAfter the character to check
     * @return true if always allowed without whitespace
     */
    private static boolean isAlwaysValidCharAfterRightBracket(char charAfter) {
        return charAfter == '['
            || charAfter == ']'
            || charAfter == '.'
            || charAfter == ','
            || charAfter == ';'
            || charAfter == ')';
    }

    /**
     * Checks if the character after {@code ]} starts a valid postfix operator (++ or --)
     * or method reference operator ({@code ::}).
     *
     * @param charAfter the character to check
     * @param line the unicode code points array of the line
     * @param position the position of the code point in the line
     * @return true if a valid method reference or postfix operator exists
     */
    private static boolean isMethodReferenceOrPostfixOperator(
        char charAfter, int[] line, int position) {
        boolean isMethodReferenceOrPostfixOperator = false;
        if (charAfter == '+' || charAfter == '-' || charAfter == ':') {
            final int nextPosition = position + 1;
            final int nextCodePoint = line[nextPosition];
            final char nextChar = (char) nextCodePoint;

            if (nextChar == charAfter) {
                isMethodReferenceOrPostfixOperator = true;
            }
        }

        return isMethodReferenceOrPostfixOperator;
    }

    /**
     * Checks if the character after {@code ]} is an angle bracket ({@code >})
     * or a shift operator ({@code <<} or {@code >>}).
     *
     * @param charAfter the character to check
     * @param line the unicode code points array of the line
     * @param position the position of the code point in the line
     * @return true if a valid angle bracket or shift operator exists
     */
    private static boolean isAngleBracketOrShiftOperator(char charAfter, int[] line, int position) {
        boolean result = false;
        if (charAfter == '>' || charAfter == '<') {
            final int nextPosition = position + 1;
            final char nextChar = (char) line[nextPosition];
            // Accept shift operators
            if (nextChar == charAfter) {
                result = true;
            }
            // Accept single angle bracket, but NOT if it's followed by '='
            else if (nextChar != '=' && charAfter == '>') {
                result = true;
            }
        }
        return result;
    }
}
