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

import java.util.Optional;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <div>
 * Checks that there is no whitespace after a token.
 * More specifically, it checks that it is not followed by whitespace,
 * or (if linebreaks are allowed) all characters on the line after are
 * whitespace. To forbid linebreaks after a token, set property
 * {@code allowLineBreaks} to {@code false}.
 * </div>
 *
 * <p>
 * The check processes
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INDEX_OP">
 * INDEX_OP</a> token specially from other tokens. Actually it is checked that
 * there is no whitespace before this token, not after it.
 * </p>
 *
 * <p>
 * Note: This check processes the
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SYNCHRONIZED">
 * LITERAL_SYNCHRONIZED</a> token only when it appears as a part of a
 * <a href="https://docs.oracle.com/javase/specs/jls/se19/html/jls-14.html#jls-14.19">
 * synchronized statement</a>, i.e. {@code synchronized(this) {}}.
 * </p>
 *
 * @since 3.0
 */
@StatelessCheck
public class NoWhitespaceAfterCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "ws.followed";

    /** Control whether whitespace is allowed if the token is at a linebreak. */
    private boolean allowLineBreaks = true;

    /**
     * Creates a new {@code NoWhitespaceAfterCheck} instance.
     */
    public NoWhitespaceAfterCheck() {
        // no code by default
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.ARRAY_INIT,
            TokenTypes.AT,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.UNARY_MINUS,
            TokenTypes.UNARY_PLUS,
            TokenTypes.BNOT,
            TokenTypes.LNOT,
            TokenTypes.DOT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.ARRAY_INIT,
            TokenTypes.AT,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.UNARY_MINUS,
            TokenTypes.UNARY_PLUS,
            TokenTypes.BNOT,
            TokenTypes.LNOT,
            TokenTypes.DOT,
            TokenTypes.TYPECAST,
            TokenTypes.INDEX_OP,
            TokenTypes.DO_WHILE,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.METHOD_REF,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_CATCH,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    /**
     * Setter to control whether whitespace is allowed if the token is at a linebreak.
     *
     * @param allowLineBreaks whether whitespace should be flagged at linebreaks.
     * @since 3.0
     */
    public void setAllowLineBreaks(boolean allowLineBreaks) {
        this.allowLineBreaks = allowLineBreaks;
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (shouldCheckWhitespaceAfter(ast)) {
            final DetailAST whitespaceFollowedAst = getWhitespaceFollowedNode(ast);
            final int whitespaceColumnNo = getPositionAfter(whitespaceFollowedAst);
            final int whitespaceLineNo = whitespaceFollowedAst.getLineNo();

            if (hasTrailingWhitespace(ast, whitespaceColumnNo, whitespaceLineNo)) {
                log(ast, MSG_KEY, whitespaceFollowedAst.getText());
            }
        }
    }

    /**
     * For a visited ast node returns node that should be checked
     * for not being followed by whitespace.
     *
     * @param ast
     *        , visited node.
     * @return node before ast.
     */
    private static DetailAST getWhitespaceFollowedNode(DetailAST ast) {
        return switch (ast.getType()) {
            case TokenTypes.TYPECAST -> ast.findFirstToken(TokenTypes.RPAREN);
            case TokenTypes.INDEX_OP -> getIndexOpPreviousElement(ast);
            default -> ast;
        };
    }

    /**
     * Returns whether whitespace after a visited node should be checked. A
     * {@link TokenTypes#LITERAL_SYNCHRONIZED LITERAL_SYNCHRONIZED} token that is not part of
     * a synchronized statement (a synchronized method modifier) is skipped.
     *
     * @param ast the visited node
     * @return true if whitespace after ast should be checked
     */
    private static boolean shouldCheckWhitespaceAfter(DetailAST ast) {
        final boolean isSynchronizedMethod = ast.getType() == TokenTypes.LITERAL_SYNCHRONIZED
                        && ast.getFirstChild() == null;
        return !isSynchronizedMethod;
    }

    /**
     * Gets position after token (place of possible redundant whitespace).
     *
     * @param ast Node representing token.
     * @return position after token.
     */
    private static int getPositionAfter(DetailAST ast) {
        return ast.getColumnNo() + ast.getText().length();
    }

    /**
     * Checks if there is unwanted whitespace after the visited node.
     *
     * @param ast
     *        , visited node.
     * @param whitespaceColumnNo
     *        , column number of a possible whitespace.
     * @param whitespaceLineNo
     *        , line number of a possible whitespace.
     * @return true if whitespace found.
     */
    private boolean hasTrailingWhitespace(DetailAST ast,
        int whitespaceColumnNo, int whitespaceLineNo) {
        final boolean result;
        final int astLineNo = ast.getLineNo();
        final int[] line = getLineCodePoints(astLineNo - 1);
        if (astLineNo == whitespaceLineNo && whitespaceColumnNo < line.length) {
            result = CommonUtil.isCodePointWhitespace(line, whitespaceColumnNo);
        }
        else {
            result = !allowLineBreaks;
        }
        return result;
    }

    /**
     * Gets previous node for {@link TokenTypes#INDEX_OP INDEX_OP} token
     * for usage in getPositionAfter method.
     *
     * @param ast
     *        , {@code TokenTypes#INDEX_OP INDEX_OP} node.
     * @return previous node by text order.
     */
    private static DetailAST getIndexOpPreviousElement(DetailAST ast) {
        final DetailAST result;
        final DetailAST firstChild = ast.getFirstChild();
        if (firstChild.getType() == TokenTypes.INDEX_OP) {
            // second or higher array index
            result = firstChild.findFirstToken(TokenTypes.RBRACK);
        }
        else if (firstChild.getType() == TokenTypes.IDENT) {
            result = firstChild;
        }
        else {
            final DetailAST ident = getIdentLastToken(ast);
            if (ident == null) {
                final DetailAST rparen = ast.findFirstToken(TokenTypes.RPAREN);
                // construction like new int[]{1}[0]
                if (rparen == null) {
                    final DetailAST lastChild = firstChild.getLastChild();
                    result = lastChild.findFirstToken(TokenTypes.RCURLY);
                }
                // construction like ((byte[]) pixels)[0]
                else {
                    result = rparen;
                }
            }
            else {
                result = ident;
            }
        }
        return result;
    }

    /**
     * Gets leftmost token of identifier.
     *
     * @param ast
     *        , token possibly possessing an identifier.
     * @return leftmost token of identifier.
     */
    private static DetailAST getIdentLastToken(DetailAST ast) {
        final DetailAST result;
        final Optional<DetailAST> dot = getPrecedingDot(ast);
        // method call case
        if (dot.isEmpty() || ast.getFirstChild().getType() == TokenTypes.METHOD_CALL) {
            final DetailAST methodCall = ast.findFirstToken(TokenTypes.METHOD_CALL);
            if (methodCall == null) {
                result = ast.findFirstToken(TokenTypes.IDENT);
            }
            else {
                result = methodCall.findFirstToken(TokenTypes.RPAREN);
            }
        }
        // qualified name case
        else {
            result = dot.orElseThrow().getFirstChild().getNextSibling();
        }
        return result;
    }

    /**
     * Gets the dot preceding a class member array index operation or class reference.
     *
     * @param leftBracket the ast we are checking
     * @return dot preceding the left bracket
     */
    private static Optional<DetailAST> getPrecedingDot(DetailAST leftBracket) {
        final DetailAST referencedMemberDot = leftBracket.findFirstToken(TokenTypes.DOT);
        final Optional<DetailAST> result = Optional.ofNullable(referencedMemberDot);
        return result.or(() -> getReferencedClassDot(leftBracket));
    }

    /**
     * Gets the dot preceding a class reference.
     *
     * @param leftBracket the ast we are checking
     * @return dot preceding the left bracket
     */
    private static Optional<DetailAST> getReferencedClassDot(DetailAST leftBracket) {
        final DetailAST parent = leftBracket.getParent();
        Optional<DetailAST> classDot = Optional.empty();
        if (parent.getType() != TokenTypes.ASSIGN) {
            classDot = Optional.ofNullable(parent.findFirstToken(TokenTypes.DOT));
        }
        return classDot;
    }

}
