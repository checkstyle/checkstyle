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
 * {@code ws.notPreceded}
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
    public static final String MSG_WS_NOT_PRECEDED = "ws.notPreceded";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

    /** Open square bracket literal. */
    private static final String OPEN_BRACKET = "[";

    /** Close square bracket literal. */
    private static final String CLOSE_BRACKET = "]";

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
        final int[] line = getLineCodePoints(ast.getLineNo() - 1);
        final DetailAST leftBracket = ast.findFirstToken(TokenTypes.LBRACK);
        final DetailAST rightBracket = ast.findFirstToken(TokenTypes.RBRACK);

        if (leftBracket != null) {
            processLeftBracket(leftBracket, line);
        }

        if (rightBracket != null) {
            processRightBracket(rightBracket, line);
        }
    }

    /**
     * Checks the left square bracket for whitespace violations.
     *
     * @param leftBracket the left bracket token
     * @param line the unicode code points array of the line
     */
    private void processLeftBracket(DetailAST leftBracket, int[] line) {
        final int columnNo = leftBracket.getColumnNo();
        final int before = columnNo - 1;
        final int after = columnNo + 1;

        // Check if preceded by whitespace
        if (before >= 0 && CommonUtil.isCodePointWhitespace(line, after)) {
            log(leftBracket, MSG_WS_PRECEDED, OPEN_BRACKET);
        }

        // Check if followed by whitespace
        if (after < line.length && CommonUtil.isCodePointWhitespace(line, after)) {
            log(leftBracket, MSG_WS_FOLLOWED, OPEN_BRACKET);
        }
    }

    /**
     * Checks the right square bracket for whitespace violations.
     *
     * @param rightBracket the right bracket token
     * @param line the unicode code points array of the line
     */
    private void processRightBracket(DetailAST rightBracket, int[] line) {
        final int columnNo = rightBracket.getColumnNo();
        final int before = columnNo - 1;
        final int after = columnNo + 1;

        // Check if preceded by whitespace
        if (before >= 0 && CommonUtil.isCodePointWhitespace(line, before)) {
            log(rightBracket, MSG_WS_PRECEDED, CLOSE_BRACKET);
        }

        // Check if it should be followed by whitespace
        if (after < line.length) {
            final char charAfter = Character.toChars(line[after])[0];
            final boolean hasWhitespace = Character.isWhitespace(charAfter);
            final boolean isValidWithoutWhitespace = isCharacterValidAfterRightBracket(charAfter);

            // If character is valid without whitespace, no whitespace should be present
            if (isValidWithoutWhitespace && hasWhitespace) {
                log(rightBracket, MSG_WS_FOLLOWED, CLOSE_BRACKET);
            }
            // If character is not valid without whitespace, whitespace must be present
            else if (!isValidWithoutWhitespace && !hasWhitespace) {
                log(rightBracket, MSG_WS_NOT_FOLLOWED, CLOSE_BRACKET);
            }
        }
    }

    /**
     * Checks whether the given character is valid to be right after a right bracket
     * without requiring whitespace.
     *
     * @param charAfter character to check
     * @return true if the character is valid after ']' without whitespace
     */
    private static boolean isCharacterValidAfterRightBracket(char charAfter) {
        return charAfter == '['     // another bracket: int[][]
            || charAfter == ']'     // closing bracket: x[arr[i]]
            || charAfter == '.'     // member access: arr[i].length
            || charAfter == ','     // comma: arr[i], arr[j]
            || charAfter == ';'     // semicolon: arr[i];
            || charAfter == ')'     // right paren: (arr[i])
            || charAfter == '}'     // right brace: {arr[i]}
            || charAfter == '+'     // postfix ++, or binary +
            || charAfter == '-';    // postfix --, or binary -
    }

}