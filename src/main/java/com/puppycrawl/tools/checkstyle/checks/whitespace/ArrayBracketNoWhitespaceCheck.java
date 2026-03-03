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

import java.util.Set;

import javax.annotation.Nullable;

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
 *   <li>must not be preceded with whitespace when preceded by a
 *     {@code TYPE} or {@code IDENT} in array declarations or array access</li>
 *   <li>must not be followed with whitespace</li>
 * </ul>
 *
 * <p>
 * Right square bracket ("{@code ]}"):
 * </p>
 * <ul>
 *   <li>must not be preceded with whitespace</li>
 *   <li>must be followed with whitespace in all cases, except when followed by:
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
 * @since 13.9.0
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
    public static final String MSG_WS_NOT_PRECEDED = "ws.notPreceded";

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

    /**
     * Tokens that are valid after a right bracket without whitespace.
     */
    private static final Set<Integer> VALID_AFTER_RIGHT_BRACKET_TOKENS =
        Set.of(
            TokenTypes.ARRAY_DECLARATOR,
            TokenTypes.INDEX_OP,
            TokenTypes.DOT,
            TokenTypes.METHOD_REF,
            TokenTypes.RBRACK,
            TokenTypes.RPAREN,
            TokenTypes.RCURLY,
            TokenTypes.COMMA,
            TokenTypes.SEMI,
            TokenTypes.GENERIC_END,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC
        );

    /**
     * Creates a new {@code ArrayBracketNoWhitespaceCheck} instance.
     */
    public ArrayBracketNoWhitespaceCheck() {
        // no code by default
    }

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
            TokenTypes.RBRACK,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.RBRACK) {
            processRightBracket(ast);
        }
        else {
            final boolean whitespaceBefore = isWhitespaceAt(ast, ast.getColumnNo() - 1);
            final boolean annotationBefore = isPrecededByAnnotation(ast);
            if (!annotationBefore && whitespaceBefore) {
                log(ast, MSG_WS_PRECEDED, ast.getText());
            }
            else if (annotationBefore && !whitespaceBefore) {
                log(ast, MSG_WS_NOT_PRECEDED, ast.getText());
            }
            if (isWhitespaceAt(ast, ast.getColumnNo() + 1)) {
                log(ast, MSG_WS_FOLLOWED, ast.getText());
            }
        }
    }

    /**
     * Processes a right bracket token and logs violations if it is preceded
     * or followed by whitespace inappropriately.
     *
     * @param ast the right bracket token to process
     */
    private void processRightBracket(DetailAST ast) {
        if (isWhitespaceAt(ast, ast.getColumnNo() - 1)) {
            log(ast, MSG_WS_PRECEDED, ast.getText());
        }

        final DetailAST nextToken = findNextToken(ast);
        if (nextToken != null) {
            final boolean whitespaceAfter = isWhitespaceAt(ast, ast.getColumnNo() + 1);
            final boolean requiresWhitespace = !isValidWithoutWhitespace(nextToken);
            if (requiresWhitespace && !whitespaceAfter) {
                log(ast, MSG_WS_NOT_FOLLOWED, ast.getText());
            }
            else if (!requiresWhitespace && whitespaceAfter) {
                log(ast, MSG_WS_FOLLOWED, ast.getText());
            }
        }
    }

    /**
     * Checks whether an {@code ARRAY_DECLARATOR} is immediately preceded by an
     * {@code ANNOTATIONS} sibling, which happens in constructs like
     * {@code int @Ann [] x}.
     *
     * @param ast the {@code ARRAY_DECLARATOR} or {@code INDEX_OP} token
     * @return true if the token's previous sibling is an ANNOTATIONS node
     */
    private static boolean isPrecededByAnnotation(DetailAST ast) {
        final DetailAST previousSibling = ast.getPreviousSibling();
        return previousSibling != null
                && previousSibling.getType() == TokenTypes.ANNOTATIONS;
    }

    /**
     * Checks if a whitespace character is present at the given column on the
     * same line as the provided token.
     *
     * @param token the token whose line should be checked
     * @param columnNo the column number to inspect for whitespace
     * @return true if the character at {@code columnNo} is a whitespace character
     */
    private boolean isWhitespaceAt(DetailAST token, int columnNo) {
        final int[] line = getLineCodePoints(token.getLineNo() - 1);
        return columnNo >= 0 && columnNo < line.length
                && CommonUtil.isCodePointWhitespace(line, columnNo);
    }

    /**
     * Finds the next token after a right bracket by climbing the AST and
     * scanning next-sibling chains at each level. At every level all siblings
     * are visited and passed to {@link #findBestCandidate}.
     * The candidate with the smallest qualifying column is returned.
     *
     * @param rightBracket the right bracket token whose successor is needed
     * @return the closest same-line token that follows the bracket, or {@code null}
     *         if no such token exists on that line
     */
    @Nullable
    private static DetailAST findNextToken(DetailAST rightBracket) {
        DetailAST candidate = null;
        DetailAST current = rightBracket;

        while (current != null) {
            for (DetailAST sibling = current; sibling != null; sibling = sibling.getNextSibling()) {
                candidate = findBestCandidate(candidate, rightBracket, sibling);
            }
            current = current.getParent();
        }
        return candidate;
    }

    /**
     * Evaluates whether {@code current} is a better next-token candidate than
     * the existing {@code candidate} relative to {@code rightBracket}.
     * A token qualifies as a better candidate when it sits on the same line as
     * the right bracket, has a greater column number than the bracket, and
     * either no candidate exists yet or its column number is closer to the
     * bracket than the current best. When the criteria are met the new token
     * is returned; otherwise the existing candidate is returned unchanged.
     *
     * @param candidate the current best candidate
     * @param rightBracket the right bracket token
     * @param current the current AST node being evaluated
     * @return the new best candidate
     */
    @Nullable
    private static DetailAST findBestCandidate(@Nullable DetailAST candidate,
            DetailAST rightBracket, DetailAST current) {
        DetailAST result = candidate;
        final boolean newCandidate = current.getLineNo() == rightBracket.getLineNo()
                && current.getColumnNo() > rightBracket.getColumnNo()
                && (candidate == null
                        || current.getColumnNo() < candidate.getColumnNo());
        if (newCandidate) {
            result = current;
        }
        return result;
    }

    /**
     * Checks if the given token can follow a right bracket without whitespace.
     * Uses TokenTypes to determine valid tokens.
     *
     * @param nextToken the token that follows the right bracket
     * @return true if the token can follow without whitespace
     */
    private static boolean isValidWithoutWhitespace(DetailAST nextToken) {
        final int type = nextToken.getType();

        return VALID_AFTER_RIGHT_BRACKET_TOKENS.contains(type);
    }

}
