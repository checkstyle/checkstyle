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
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the policy on the padding of parentheses; that is whether a space is required
 * after a left parenthesis and before a right parenthesis, or such spaces are
 * forbidden. No check occurs at the right parenthesis after an empty for
 * iterator, at the left parenthesis before an empty for initialization, or at
 * the right parenthesis of a try-with-resources resource specification where
 * the last resource variable has a trailing semicolon.
 * Use Check
 * <a href="https://checkstyle.org/checks/whitespace/emptyforiteratorpad.html">
 * EmptyForIteratorPad</a> to validate empty for iterators and
 * <a href="https://checkstyle.org/checks/whitespace/emptyforinitializerpad.html">
 * EmptyForInitializerPad</a> to validate empty for initializers.
 * Typecasts are also not checked, as there is
 * <a href="https://checkstyle.org/checks/whitespace/typecastparenpad.html">
 * TypecastParenPad</a> to validate them.
 *
 * <p>Only parentheses directly owned by configured tokens are checked. For example,
 * parentheses around multiplication operands are checked by the {@code STAR} token.</p>
 * </div>
 *
 * @since 3.0
 */
@StatelessCheck
public class ParenPadCheck extends AbstractParenPadCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_WS_FOLLOWED = "ws.followed";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_WS_NOT_FOLLOWED = "ws.notFollowed";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_WS_PRECEDED = "ws.preceded";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_WS_NOT_PRECEDED = "ws.notPreceded";

    /**
     * Initializes message keys.
     */
    public ParenPadCheck() {
        super(MSG_WS_FOLLOWED, MSG_WS_NOT_FOLLOWED, MSG_WS_PRECEDED, MSG_WS_NOT_PRECEDED);
    }

    @Override
    public int[] getDefaultTokens() {
        return makeAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return makeAcceptableTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.METHOD_CALL -> {
                processLeft(ast);
                processRight(ast.findFirstToken(TokenTypes.RPAREN));
            }

            case TokenTypes.LITERAL_FOR -> visitLiteralFor(ast);

            case TokenTypes.RESOURCE_SPECIFICATION -> visitResourceSpecification(ast);

            default -> processExpression(ast);
        }
    }

    /**
     * Checks parens in {@link TokenTypes#RESOURCE_SPECIFICATION}.
     *
     * @param ast the token to check.
     */
    private void visitResourceSpecification(DetailAST ast) {
        processLeft(ast.findFirstToken(TokenTypes.LPAREN));
        final DetailAST rparen = ast.findFirstToken(TokenTypes.RPAREN);
        if (!hasPrecedingSemiColon(rparen)) {
            processRight(rparen);
        }
    }

    /**
     * Checks that a token is preceded by a semicolon.
     *
     * @param ast the token to check
     * @return whether a token is preceded by a semicolon
     */
    private static boolean hasPrecedingSemiColon(DetailAST ast) {
        return ast.getPreviousSibling().getType() == TokenTypes.SEMI;
    }

    /**
     * Checks parens in {@link TokenTypes#LITERAL_FOR}.
     *
     * @param ast the token to check.
     */
    private void visitLiteralFor(DetailAST ast) {
        final DetailAST lparen = ast.findFirstToken(TokenTypes.LPAREN);
        if (!isPrecedingEmptyForInit(lparen)) {
            processLeft(lparen);
        }
        final DetailAST rparen = ast.findFirstToken(TokenTypes.RPAREN);
        if (!isFollowsEmptyForIterator(rparen)) {
            processRight(rparen);
        }
    }

    /**
     * Checks only the parentheses owned by the configured token.
     *
     * @param ast the token to check
     */
    private void processExpression(DetailAST ast) {
        for (DetailAST child = ast.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            if (child.getType() == TokenTypes.LPAREN) {
                processLeft(child);
            }
            else if (child.getType() == TokenTypes.RPAREN && !isInTypecast(child)) {
                processRight(child);
            }
        }
    }

    /**
     * Returns array of acceptable tokens.
     *
     * @return acceptableTokens.
     */
    private static int[] makeAcceptableTokens() {
        return new int[] {
            TokenTypes.ANNOTATION,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.CTOR_DEF,
            TokenTypes.DOT,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.EXPR,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.METHOD_CALL,
            TokenTypes.METHOD_DEF,
            TokenTypes.QUESTION,
            TokenTypes.RESOURCE_SPECIFICATION,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LAMBDA,
            TokenTypes.RECORD_DEF,
            TokenTypes.RECORD_PATTERN_DEF,
            TokenTypes.ASSIGN,
            TokenTypes.PLUS_ASSIGN,
            TokenTypes.MINUS_ASSIGN,
            TokenTypes.STAR_ASSIGN,
            TokenTypes.DIV_ASSIGN,
            TokenTypes.MOD_ASSIGN,
            TokenTypes.SR_ASSIGN,
            TokenTypes.BSR_ASSIGN,
            TokenTypes.SL_ASSIGN,
            TokenTypes.BAND_ASSIGN,
            TokenTypes.BXOR_ASSIGN,
            TokenTypes.BOR_ASSIGN,
            TokenTypes.LAND,
            TokenTypes.LOR,
            TokenTypes.BAND,
            TokenTypes.BOR,
            TokenTypes.BXOR,
            TokenTypes.EQUAL,
            TokenTypes.NOT_EQUAL,
            TokenTypes.LT,
            TokenTypes.LE,
            TokenTypes.GT,
            TokenTypes.GE,
            TokenTypes.SL,
            TokenTypes.SR,
            TokenTypes.BSR,
            TokenTypes.PLUS,
            TokenTypes.MINUS,
            TokenTypes.STAR,
            TokenTypes.DIV,
            TokenTypes.MOD,
            TokenTypes.UNARY_PLUS,
            TokenTypes.UNARY_MINUS,
            TokenTypes.LNOT,
            TokenTypes.BNOT,
            TokenTypes.INC,
            TokenTypes.DEC,
            TokenTypes.POST_INC,
            TokenTypes.POST_DEC,
            TokenTypes.INDEX_OP,
            TokenTypes.METHOD_REF,
            TokenTypes.LITERAL_INSTANCEOF,
            TokenTypes.TYPECAST,
        };
    }

    /**
     * Checks whether {@link TokenTypes#RPAREN} is a closing paren of a {@link TokenTypes#TYPECAST}.
     *
     * @param ast of a {@code TokenTypes#RPAREN} to check.
     * @return true if ast is a closing paren of a {@code TokenTypes#TYPECAST}.
     */
    private static boolean isInTypecast(DetailAST ast) {
        boolean result = false;
        if (ast.getParent().getType() == TokenTypes.TYPECAST) {
            final DetailAST firstRparen = ast.getParent().findFirstToken(TokenTypes.RPAREN);
            if (TokenUtil.areOnSameLine(firstRparen, ast)
                    && firstRparen.getColumnNo() == ast.getColumnNo()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Checks that a token follows an empty for iterator.
     *
     * @param ast the token to check
     * @return whether a token follows an empty for iterator
     */
    private static boolean isFollowsEmptyForIterator(DetailAST ast) {
        boolean result = false;
        final DetailAST parent = ast.getParent();
        // Only traditional for statements are examined, not for-each statements
        if (parent.findFirstToken(TokenTypes.FOR_EACH_CLAUSE) == null) {
            final DetailAST forIterator =
                parent.findFirstToken(TokenTypes.FOR_ITERATOR);
            result = !forIterator.hasChildren();
        }
        return result;
    }

    /**
     * Checks that a token precedes an empty for initializer.
     *
     * @param ast the token to check
     * @return whether a token precedes an empty for initializer
     */
    private static boolean isPrecedingEmptyForInit(DetailAST ast) {
        boolean result = false;
        final DetailAST parent = ast.getParent();
        // Only traditional for statements are examined, not for-each statements
        if (parent.findFirstToken(TokenTypes.FOR_EACH_CLAUSE) == null) {
            final DetailAST forIterator =
                    parent.findFirstToken(TokenTypes.FOR_INIT);
            result = !forIterator.hasChildren();
        }
        return result;
    }

}
