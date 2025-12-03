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
            processLeftBracket(ast, leftBracket, line);
        }

        if (rightBracket != null) {
            processRightBracket(rightBracket, line);
        }
    }

    /**
     * Checks the left square bracket for whitespace violations.
     *
     * @param ast the parent AST node
     * @param leftBracket the left bracket token
     * @param line the unicode code points array of the line
     */
    private void processLeftBracket(DetailAST ast, DetailAST leftBracket, int[] line) {
        final int columnNo = leftBracket.getColumnNo();
        final int before = columnNo - 1;
        final int after = columnNo + 1;

        // Check if preceded by whitespace (only for TYPE or IDENT contexts)
        if (before >= 0 && CommonUtil.isCodePointWhitespace(line, before)) {
            if (shouldCheckLeftBracketPrecededByWhitespace(ast)) {
                log(leftBracket, MSG_WS_PRECEDED, OPEN_BRACKET);
            }
        }

        // Check if followed by whitespace
        if (after < line.length && CommonUtil.isCodePointWhitespace(line, after)) {
            log(leftBracket, MSG_WS_FOLLOWED, OPEN_BRACKET);
        }
    }

    /**
     * Determines if we should check for whitespace before the left bracket.
     * Only checks when the bracket is preceded by TYPE or IDENT tokens.
     *
     * @param ast the parent AST node (ARRAY_DECLARATOR or INDEX_OP)
     * @return true if we should check for preceding whitespace
     */
    private static boolean shouldCheckLeftBracketPrecededByWhitespace(DetailAST ast) {
        if (ast.getType() == TokenTypes.INDEX_OP) {
            // For INDEX_OP, the first child should be the expression being indexed
            final DetailAST firstChild = ast.getFirstChild();
            return firstChild != null
                && (firstChild.getType() == TokenTypes.IDENT
                    || isTypeToken(firstChild));
        }
        else if (ast.getType() == TokenTypes.ARRAY_DECLARATOR) {
            // For ARRAY_DECLARATOR, check the previous sibling or parent context
            final DetailAST parent = ast.getParent();
            if (parent != null) {
                final DetailAST previousSibling = ast.getPreviousSibling();
                if (previousSibling != null) {
                    return previousSibling.getType() == TokenTypes.IDENT
                        || isTypeToken(previousSibling);
                }
                // Check if parent is TYPE or has TYPE/IDENT
                return parent.getType() == TokenTypes.TYPE
                    || parent.getType() == TokenTypes.IDENT
                    || isTypeToken(parent);
            }
        }
        return true; // Default to checking
    }

    /**
     * Checks if the given AST represents a type token.
     *
     * @param ast the AST to check
     * @return true if the AST is a type-related token
     */
    private static boolean isTypeToken(DetailAST ast) {
        return ast.getType() == TokenTypes.TYPE
            || ast.getType() == TokenTypes.LITERAL_INT
            || ast.getType() == TokenTypes.LITERAL_BOOLEAN
            || ast.getType() == TokenTypes.LITERAL_BYTE
            || ast.getType() == TokenTypes.LITERAL_CHAR
            || ast.getType() == TokenTypes.LITERAL_SHORT
            || ast.getType() == TokenTypes.LITERAL_LONG
            || ast.getType() == TokenTypes.LITERAL_FLOAT
            || ast.getType() == TokenTypes.LITERAL_DOUBLE;
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
            final boolean isValidWithoutWhitespace =
                isCharacterValidAfterRightBracket(charAfter, line, after);

            // If character requires whitespace but doesn't have it, report violation
            if (!isValidWithoutWhitespace && !hasWhitespace) {
                log(rightBracket, MSG_WS_NOT_FOLLOWED, CLOSE_BRACKET);
            }
            // If character is valid without whitespace but has it, report violation
            else if (isValidWithoutWhitespace && hasWhitespace) {
                log(rightBracket, MSG_WS_FOLLOWED, CLOSE_BRACKET);
            }
        }
    }

    /**
     * Checks whether the given character is valid to be right after a right bracket
     * without requiring whitespace.
     *
     * @param charAfter character to check
     * @param line the unicode code points array of the line
     * @param position the position of charAfter in the line
     * @return true if the character is valid after ']' without whitespace
     */
    private static boolean isCharacterValidAfterRightBracket(char charAfter, int[] line,
                                                               int position) {
        if (charAfter == '['     // another bracket: int[][]
            || charAfter == ']'  // closing bracket: x[arr[i]]
            || charAfter == '.'  // member access: arr[i].length
            || charAfter == ','  // comma: arr[i], arr[j]
            || charAfter == ';'  // semicolon: arr[i];
            || charAfter == ')'  // right paren: (arr[i])
            || charAfter == '}'  // right brace: {arr[i]}
            || charAfter == ':') { // colon: for (int x : arr[i])
            return true;
        }

        // Check for postfix ++ or --
        if (charAfter == '+' || charAfter == '-') {
            final int nextPosition = position + 1;
            if (nextPosition < line.length) {
                final char nextChar = Character.toChars(line[nextPosition])[0];
                // If followed by the same operator, it's a postfix operator
                if (nextChar == charAfter) {
                    return true;
                }
            }
            // Single + or - is a binary operator, requires whitespace
            return false;
        }

        return false;
    }

}