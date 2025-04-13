////
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
///

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.BitSet;

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
 * <a href="https://checkstyle.org/checks/whitespace/emptyforiteratorpad.html#EmptyForIteratorPad">
 * EmptyForIteratorPad</a> to validate empty for iterators and
 * <a href="https://checkstyle.org/checks/whitespace/emptyforinitializerpad.html#EmptyForInitializerPad">
 * EmptyForInitializerPad</a> to validate empty for initializers.
 * Typecasts are also not checked, as there is
 * <a href="https://checkstyle.org/checks/whitespace/typecastparenpad.html#TypecastParenPad">
 * TypecastParenPad</a> to validate them.
 * </div>
 * <ul>
 * <li>
 * Property {@code option} - Specify policy on how to pad parentheses.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.whitespace.PadOption}.
 * Default value is {@code nospace}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION">
 * ANNOTATION</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_FIELD_DEF">
 * ANNOTATION_FIELD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_CALL">
 * CTOR_CALL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CTOR_DEF">
 * CTOR_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#DOT">
 * DOT</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#EXPR">
 * EXPR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">
 * LITERAL_CATCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_DO">
 * LITERAL_DO</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FOR">
 * LITERAL_FOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">
 * LITERAL_IF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_NEW">
 * LITERAL_NEW</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SWITCH">
 * LITERAL_SWITCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SYNCHRONIZED">
 * LITERAL_SYNCHRONIZED</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_WHILE">
 * LITERAL_WHILE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_CALL">
 * METHOD_CALL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#METHOD_DEF">
 * METHOD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#QUESTION">
 * QUESTION</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RESOURCE_SPECIFICATION">
 * RESOURCE_SPECIFICATION</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#SUPER_CTOR_CALL">
 * SUPER_CTOR_CALL</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAMBDA">
 * LAMBDA</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_PATTERN_DEF">
 * RECORD_PATTERN_DEF</a>.
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
 * @since 3.0
 */
public class ParenPadCheck extends AbstractParenPadCheck {

    /**
     * Tokens that this check handles.
     */
    private final BitSet acceptableTokens;

    /**
     * Initializes acceptableTokens.
     */
    public ParenPadCheck() {
        acceptableTokens = TokenUtil.asBitSet(makeAcceptableTokens());
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
            case TokenTypes.METHOD_CALL:
                processLeft(ast);
                processRight(ast.findFirstToken(TokenTypes.RPAREN));
                break;
            case TokenTypes.DOT:
            case TokenTypes.EXPR:
            case TokenTypes.QUESTION:
                processExpression(ast);
                break;
            case TokenTypes.LITERAL_FOR:
                visitLiteralFor(ast);
                break;
            case TokenTypes.ANNOTATION:
            case TokenTypes.ENUM_CONSTANT_DEF:
            case TokenTypes.LITERAL_NEW:
            case TokenTypes.LITERAL_SYNCHRONIZED:
            case TokenTypes.LAMBDA:
                visitTokenWithOptionalParentheses(ast);
                break;
            case TokenTypes.RESOURCE_SPECIFICATION:
                visitResourceSpecification(ast);
                break;
            default:
                processLeft(ast.findFirstToken(TokenTypes.LPAREN));
                processRight(ast.findFirstToken(TokenTypes.RPAREN));
        }
    }

    /**
     * Checks parens in token which may not contain parens, e.g.
     * {@link TokenTypes#ENUM_CONSTANT_DEF}, {@link TokenTypes#ANNOTATION}
     * {@link TokenTypes#LITERAL_SYNCHRONIZED}, {@link TokenTypes#LITERAL_NEW} and
     * {@link TokenTypes#LAMBDA}.
     *
     * @param ast the token to check.
     */
    private void visitTokenWithOptionalParentheses(DetailAST ast) {
        final DetailAST parenAst = ast.findFirstToken(TokenTypes.LPAREN);
        if (parenAst != null) {
            processLeft(parenAst);
            processRight(ast.findFirstToken(TokenTypes.RPAREN));
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
     * Checks parens inside {@link TokenTypes#EXPR}, {@link TokenTypes#QUESTION}
     * and {@link TokenTypes#METHOD_CALL}.
     *
     * @param ast the token to check.
     */
    private void processExpression(DetailAST ast) {
        DetailAST currentNode = ast.getFirstChild();
        while (currentNode != null) {
            if (currentNode.getType() == TokenTypes.LPAREN) {
                processLeft(currentNode);
            }
            else if (currentNode.getType() == TokenTypes.RPAREN && !isInTypecast(currentNode)) {
                processRight(currentNode);
            }
            else if (currentNode.hasChildren() && !isAcceptableToken(currentNode)) {
                // Traverse all subtree tokens which will never be configured
                // to be launched in visitToken()
                currentNode = currentNode.getFirstChild();
                continue;
            }

            // Go up after processing the last child
            while (currentNode.getNextSibling() == null && currentNode.getParent() != ast) {
                currentNode = currentNode.getParent();
            }
            currentNode = currentNode.getNextSibling();
        }
    }

    /**
     * Checks whether AcceptableTokens contains the given ast.
     *
     * @param ast the token to check.
     * @return true if the ast is in AcceptableTokens.
     */
    private boolean isAcceptableToken(DetailAST ast) {
        return acceptableTokens.get(ast.getType());
    }

    /**
     * Returns array of acceptable tokens.
     *
     * @return acceptableTokens.
     */
    private static int[] makeAcceptableTokens() {
        return new int[] {TokenTypes.ANNOTATION,
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
        };
    }

    /**
     * Checks whether {@link TokenTypes#RPAREN} is a closing paren
     * of a {@link TokenTypes#TYPECAST}.
     *
     * @param ast of a {@link TokenTypes#RPAREN} to check.
     * @return true if ast is a closing paren of a {@link TokenTypes#TYPECAST}.
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
