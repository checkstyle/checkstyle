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

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

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

    /**
     * Creates a new {@code ArrayBracketWhitespaceCheck} instance.
     */
    public ArrayBracketWhitespaceCheck() {
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
    public final void visitToken(final DetailAST ast) {
        if (ast.getType() == TokenTypes.RBRACK) {
            if (hasWhitespace(ast, ast.getColumnNo() - 1)) {
                log(ast, MSG_WS_PRECEDED, ast.getText());
            }

            final String msgAfter = getRightBracketAfterMessage(ast);
            if (msgAfter != null) {
                log(ast, msgAfter, ast.getText());
            }
        }
        else {
            final String msgBefore = getLeftBracketBeforeMessage(ast);
            if (msgBefore != null) {
                log(ast, msgBefore, ast.getText());
            }

            if (hasWhitespace(ast, ast.getColumnNo() + 1)) {
                log(ast, MSG_WS_FOLLOWED, ast.getText());
            }
        }
    }

    /**
     * Analyzes a left bracket token to determine if there is a whitespace violation
     * before it, considering annotations.
     *
     * @param leftBracket the left bracket token
     * @return the violation message key, or null if no violation
     */
    @Nullable
    private String getLeftBracketBeforeMessage(final DetailAST leftBracket) {
        String result = null;
        final boolean isWhitespaceBefore =
                hasWhitespace(leftBracket, leftBracket.getColumnNo() - 1);
        final boolean hasAnnotation = hasAnnotationBefore(leftBracket);

        if (isWhitespaceBefore && !hasAnnotation) {
            result = MSG_WS_PRECEDED;
        }
        else if (hasAnnotation && !isWhitespaceBefore) {
            result = MSG_WS_NOT_PRECEDED;
        }
        return result;
    }

    /**
     * Analyzes a right bracket token to determine if there is a whitespace violation
     * after it.
     *
     * @param rightBracket the right bracket token
     * @return the violation message key, or null if no violation
     */
    @Nullable
    private String getRightBracketAfterMessage(final DetailAST rightBracket) {
        String result = null;
        final DetailAST nextToken = findSubsequentNodeOnSameLine(rightBracket);
        if (nextToken != null) {
            final boolean isWhitespaceAfter =
                    hasWhitespace(rightBracket, rightBracket.getColumnNo() + 1);
            final boolean shouldFollow = isRightBracketExceptionToken(nextToken);

            if (shouldFollow && isWhitespaceAfter) {
                result = MSG_WS_FOLLOWED;
            }
            else if (!shouldFollow && !isWhitespaceAfter) {
                result = MSG_WS_NOT_FOLLOWED;
            }
        }
        return result;
    }

    /**
     * Checks whether an {@code ARRAY_DECLARATOR} is immediately preceded by an
     * {@code ANNOTATIONS} sibling, which happens in constructs like
     * {@code int @Ann [] x}.
     *
     * @param node the {@code ARRAY_DECLARATOR} or {@code INDEX_OP} token
     * @return true if the token's previous sibling is an ANNOTATIONS node
     */
    private static boolean hasAnnotationBefore(DetailAST node) {
        final DetailAST previousSibling = node.getPreviousSibling();
        return previousSibling != null
                && previousSibling.getType() == TokenTypes.ANNOTATIONS;
    }

    /**
     * Checks if a whitespace character is present at the given column on the
     * same line as the provided token.
     *
     * @param node the token whose line should be checked
     * @param columnPosition the column number to inspect for whitespace
     * @return true if the character at {@code columnPosition} is a whitespace character
     */
    private boolean hasWhitespace(DetailAST node, int columnPosition) {
        final int[] line = getLineCodePoints(node.getLineNo() - 1);
        return columnPosition >= 0 && columnPosition < line.length
                && CommonUtil.isCodePointWhitespace(line, columnPosition);
    }

    /**
     * Checks if the token is allowed to be directly attached to the right bracket
     * without any whitespace.
     *
     * @param nextToken the token that follows the right bracket.
     * @return true if whitespace is not required.
     */
    private static boolean isRightBracketExceptionToken(final DetailAST nextToken) {
        return TokenUtil.isOfType(nextToken,
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
                TokenTypes.POST_DEC,
                TokenTypes.ELLIPSIS);
    }

    /**
     * Finds the subsequent node on the same line.
     *
     * @param rightBracket the right bracket token
     * @return the closest token on the same line, or null
     */
    @Nullable
    private static DetailAST findSubsequentNodeOnSameLine(final DetailAST rightBracket) {
        DetailAST candidate = null;
        DetailAST current = rightBracket;

        while (current != null) {
            for (DetailAST sibling = current; sibling != null; sibling = sibling.getNextSibling()) {
                candidate = findCloserMatchingToken(candidate, rightBracket, sibling);
            }
            current = current.getParent();
        }
        return candidate;
    }

    /**
     * Searches for the closest matching token to the candidate without recursion.
     *
     * @param candidate the currently found node
     * @param rightBracket the right bracket token
     * @param current the current node being checked
     * @return the closest token on the same line
     */
    @Nullable
    private static DetailAST findCloserMatchingToken(@Nullable final DetailAST candidate,
            final DetailAST rightBracket, final DetailAST current) {
        DetailAST result = candidate;
        final boolean newCandidate = current.getLineNo() == rightBracket.getLineNo()
                && current.getColumnNo() > rightBracket.getColumnNo()
                && (candidate == null || current.getColumnNo() < candidate.getColumnNo());
        if (newCandidate) {
            result = current;
        }

        return result;
    }

}
