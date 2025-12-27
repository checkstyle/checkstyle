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
 * @since 12.4.0
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
        // Check left bracket
        if (precededByWhitespace(ast)) {
            log(ast, MSG_WS_PRECEDED, LEFT_BRACKET);
        }
        if (followedByWhitespace(ast)) {
            log(ast, MSG_WS_FOLLOWED, LEFT_BRACKET);
        }

        // Check right bracket
        final DetailAST rightBracket = ast.findFirstToken(TokenTypes.RBRACK);
        if (precededByWhitespace(rightBracket)) {
            log(rightBracket, MSG_WS_PRECEDED, RIGHT_BRACKET);
        }

        // Check whitespace after right bracket
        final int[] line = getLineCodePoints(rightBracket.getLineNo() - 1);
        final int bracketEnd = rightBracket.getColumnNo() + 1;

        if (bracketEnd < line.length) {
            final boolean hasWhitespace = CommonUtil.isCodePointWhitespace(line, bracketEnd);
            final DetailAST parent = rightBracket.getParent();
            final DetailAST nextToken = findNextToken(parent, rightBracket);

            if (nextToken != null) {
                final boolean requiresWhitespace = !isValidWithoutWhitespace(nextToken);

                if (requiresWhitespace && !hasWhitespace) {
                    log(rightBracket, MSG_WS_NOT_FOLLOWED, RIGHT_BRACKET);
                }
                else if (!requiresWhitespace && hasWhitespace) {
                    log(rightBracket, MSG_WS_FOLLOWED, RIGHT_BRACKET);
                }
            }
        }
    }

    /**
     * Checks if a token is preceded by whitespace.
     *
     * @param token the token to check
     * @return true if preceded by whitespace, false otherwise
     */
    private boolean precededByWhitespace(DetailAST token) {
        final int[] line = getLineCodePoints(token.getLineNo() - 1);
        final int columnNo = token.getColumnNo();
        final int before = columnNo - 1;

        return before >= 0 && CommonUtil.isCodePointWhitespace(line, before);
    }

    /**
     * Checks if a token is followed by whitespace.
     *
     * @param token the token to check
     * @return true if followed by whitespace, false otherwise
     */
    private boolean followedByWhitespace(DetailAST token) {
        final int[] line = getLineCodePoints(token.getLineNo() - 1);
        final int columnNo = token.getColumnNo();
        final int after = columnNo + 1;

        return after < line.length && CommonUtil.isCodePointWhitespace(line, after);
    }

    /**
     * Finds the next token after a right bracket by searching in a limited scope.
     *
     * @param arrayToken the array token (ARRAY_DECLARATOR or INDEX_OP)
     * @param rightBracket the right bracket token for position checking
     * @return the next token that appears after the bracket, or null if none found
     */
    private static DetailAST findNextToken(DetailAST arrayToken, DetailAST rightBracket) {
        final int bracketLine = rightBracket.getLineNo();
        final int bracketCol = rightBracket.getColumnNo();

        // Traverse up a limited number of levels to find a reasonable search scope
        DetailAST searchRoot = arrayToken;
        final int maxLevels = 5;

        for (int level = 0; level < maxLevels; level++) {
            searchRoot = searchRoot.getParent();
        }

        return findClosestTokenAfter(searchRoot, bracketLine, bracketCol);
    }

    /**
     * Recursively scans the AST to find the token closest to and after the given position.
     *
     * @param node the current node to scan
     * @param line the line number of the bracket
     * @param column the column number of the bracket
     * @return the closest token after the position, or null if none found
     */
    private static DetailAST findClosestTokenAfter(DetailAST node, int line, int column) {
        DetailAST closest = null;
        int closestDistance = Integer.MAX_VALUE;

        // Check current node (skip RBRACK and structural tokens)
        if (isValidTokenToCheck(node)
                && isTokenAfter(node, line, column) && node.getLineNo() == line) {
            final int distance = node.getColumnNo() - column;
            closest = node;
            closestDistance = distance;
        }

        DetailAST child = node.getFirstChild();
        while (child != null) {
            final DetailAST candidate = findClosestTokenAfter(child, line, column);
            if (candidate != null) {
                final int distance = candidate.getColumnNo() - column;
                if (distance < closestDistance) {
                    closest = candidate;
                    closestDistance = distance;
                }
            }

            child = child.getNextSibling();
        }

        return closest;
    }

    /**
     * Checks if a token is valid to consider as the "next" token.
     * Filters out structural/container tokens that don't represent actual code.
     *
     * @param node the token to check
     * @return true if this is a valid token to check
     */
    private static boolean isValidTokenToCheck(DetailAST node) {
        final int type = node.getType();

        return !isStructuralToken(type);
    }

    /**
     * Checks if a token type is a structural/container token.
     *
     * @param type the token type to check
     * @return true if this is a structural token
     */
    private static boolean isStructuralToken(int type) {
        return type == TokenTypes.RBRACK
                || isExpressionContainer(type)
                || isTypeContainer(type)
                || isDeclarationContainer(type);
    }

    /**
     * Checks if a token type is an expression container.
     *
     * @param type the token type to check
     * @return true if this is an expression container
     */
    private static boolean isExpressionContainer(int type) {
        return type == TokenTypes.EXPR || type == TokenTypes.ELIST;
    }

    /**
     * Checks if a token type is a type-related container.
     *
     * @param type the token type to check
     * @return true if this is a type container
     */
    private static boolean isTypeContainer(int type) {
        return type == TokenTypes.TYPE
                || type == TokenTypes.TYPE_ARGUMENTS
                || type == TokenTypes.TYPE_ARGUMENT;
    }

    /**
     * Checks if a token type is a declaration/definition container.
     *
     * @param type the token type to check
     * @return true if this is a declaration container
     */
    private static boolean isDeclarationContainer(int type) {
        return type == TokenTypes.VARIABLE_DEF
                || type == TokenTypes.MODIFIERS
                || type == TokenTypes.ANNOTATIONS
                || type == TokenTypes.OBJBLOCK;
    }

    /**
     * Checks if a token appears after the given position in the source code.
     *
     * @param token the token to check
     * @param line the line number to compare against
     * @param column the column number to compare against
     * @return true if the token is on a later line or same line but later column
     */
    private static boolean isTokenAfter(DetailAST token, int line, int column) {
        return token.getLineNo() > line
            || token.getLineNo() == line && token.getColumnNo() > column;
    }

    /**
     * Checks if the given token can follow a right bracket without whitespace.
     * Uses TokenTypes to determine valid tokens.
     *
     * @param nextToken the token that follows the right bracket
     * @return true if the token can follow without whitespace
     */
    private static boolean isValidWithoutWhitespace(DetailAST nextToken) {
        final int nextType = nextToken.getType();

        return isBracketOrAccessor(nextType)
                || isPunctuation(nextType)
                || isPostfixOrShift(nextType);
    }

    /**
     * Checks if a token type is a bracket or accessor.
     *
     * @param type the token type to check
     * @return true if this is a bracket or accessor
     */
    private static boolean isBracketOrAccessor(int type) {
        return type == TokenTypes.ARRAY_DECLARATOR
                || type == TokenTypes.INDEX_OP
                || type == TokenTypes.DOT
                || type == TokenTypes.METHOD_REF;
    }

    /**
     * Checks if a token type is punctuation that can follow without whitespace.
     *
     * @param type the token type to check
     * @return true if this is valid punctuation
     */
    private static boolean isPunctuation(int type) {
        return type == TokenTypes.COMMA
                || type == TokenTypes.SEMI
                || type == TokenTypes.RPAREN
                || type == TokenTypes.GENERIC_END;
    }

    /**
     * Checks if a token type is a postfix operator or shift operator.
     *
     * @param type the token type to check
     * @return true if this is a postfix or shift operator
     */
    private static boolean isPostfixOrShift(int type) {
        return type == TokenTypes.POST_INC
                || type == TokenTypes.POST_DEC
                || type == TokenTypes.SL
                || type == TokenTypes.SR;
    }
}
