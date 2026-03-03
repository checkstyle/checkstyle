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
 * Checks that the whitespace around square-bracket tokens {@code [} and {@code ]}
 * follows the Google Java Style Guide requirements for array declarations, array creation,
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
 * @since 13.4.0
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

    /**
     * Number of AST levels to ascend from the array token when establishing
     * the subtree to search for the next token after a right bracket.
     */
    private static final int MAX_ANCESTOR_LEVELS = 5;

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
        final DetailAST nextToken = findNextToken(ast, rightBracket);
        if (nextToken != null) {
            final int[] line = getLineCodePoints(rightBracket.getLineNo() - 1);
            final int bracketEnd = rightBracket.getColumnNo() + 1;

            final boolean hasWhitespace = CommonUtil.isCodePointWhitespace(line, bracketEnd);
            final boolean requiresWhitespace = !isValidWithoutWhitespace(nextToken);

            if (requiresWhitespace && !hasWhitespace) {
                log(rightBracket, MSG_WS_NOT_FOLLOWED, RIGHT_BRACKET);
            }
            else if (!requiresWhitespace && hasWhitespace) {
                log(rightBracket, MSG_WS_FOLLOWED, RIGHT_BRACKET);
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
     * Finds the next token after a right bracket by ascending the AST a fixed
     * number of levels to establish a search scope, then scanning that subtree.
     * Only tokens on the same line as the right bracket are considered.
     *
     * @param ast the array token (ARRAY_DECLARATOR or INDEX_OP) that owns the bracket
     * @param rightBracket the right bracket token whose successor is needed
     * @return the closest same-line token that follows the bracket, or {@code null}
     *         if no such token exists on that line
     */
    private static DetailAST findNextToken(DetailAST ast, DetailAST rightBracket) {
        final int bracketLine = rightBracket.getLineNo();
        // bracketEnd is the first column after the ']' character itself
        final int afterBracket = rightBracket.getColumnNo() + 1;

        DetailAST ancestor = ast;
        for (int level = 0; level < MAX_ANCESTOR_LEVELS; level++) {
            ancestor = ancestor.getParent();
        }

        return findClosestTokenAfter(ancestor, bracketLine, afterBracket);
    }

    /**
     * Recursively scans the subtree rooted at {@code node} and returns the token
     * that is on the same line as the bracket, starts at or after {@code column},
     * and has the smallest column number among all such candidates.
     *
     * @param node the subtree root to scan
     * @param line the line number of the right bracket
     * @param column the first column after the right bracket
     * @return the closest qualifying token, or null if none exists
     */
    private static DetailAST findClosestTokenAfter(DetailAST node, int line, int column) {
        DetailAST closest = null;

        if (node.getType() != TokenTypes.RBRACK && isTokenAfter(node, line, column)) {
            closest = node;
        }

        for (DetailAST child = node.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            final DetailAST candidate = findClosestTokenAfter(child, line, column);
            if (candidate != null
                    && (closest == null
                            || candidate.getColumnNo() <= closest.getColumnNo())) {
                closest = candidate;
            }
        }

        return closest;
    }

    /**
     * Checks if a token appears at or after the given column on the same line.
     * Tokens on a different line are excluded so that whitespace checks are
     * never applied across line boundaries.
     *
     * @param token the token to check
     * @param line the line number of the right bracket
     * @param column the first column after the right bracket (bracketEnd)
     * @return true if the token is on the same line at or after {@code column}
     */
    private static boolean isTokenAfter(DetailAST token, int line, int column) {
        return token.getLineNo() == line && token.getColumnNo() >= column;
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
                || type == TokenTypes.RCURLY
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
