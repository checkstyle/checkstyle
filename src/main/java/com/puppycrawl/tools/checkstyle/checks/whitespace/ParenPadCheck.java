////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>Checks the padding of parentheses; that is whether a space is required
 * after a left parenthesis and before a right parenthesis, or such spaces are
 * forbidden, with the exception that it does
 * not check for padding of the right parenthesis at an empty for iterator and
 * empty for initializer.
 * Use Check {@link EmptyForIteratorPadCheck EmptyForIteratorPad} to validate
 * empty for iterators and {@link EmptyForInitializerPadCheck EmptyForInitializerPad}
 * to validate empty for initializers. Typecasts are also not checked, as there is
 * {@link TypecastParenPadCheck TypecastParenPad} to validate them.
 * </p>
 * <p>
 * The policy to verify is specified using the {@link PadOption} class and
 * defaults to {@link PadOption#NOSPACE}.
 * </p>
 * <p> By default the check will check parentheses that occur with the following
 * tokens:
 *  {@link TokenTypes#ANNOTATION ANNOTATION},
 *  {@link TokenTypes#ANNOTATION_FIELD_DEF ANNOTATION_FIELD_DEF},
 *  {@link TokenTypes#CTOR_DEF CTOR_DEF},
 *  {@link TokenTypes#CTOR_CALL CTOR_CALL},
 *  {@link TokenTypes#ENUM_CONSTANT_DEF ENUM_CONSTANT_DEF},
 *  {@link TokenTypes#EXPR EXPR},
 *  {@link TokenTypes#LITERAL_CATCH LITERAL_CATCH},
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_NEW LITERAL_NEW},
 *  {@link TokenTypes#LITERAL_SWITCH LITERAL_SWITCH},
 *  {@link TokenTypes#LITERAL_SYNCHRONIZED LITERAL_SYNCHRONIZED},
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE},
 *  {@link TokenTypes#METHOD_CALL METHOD_CALL},
 *  {@link TokenTypes#METHOD_DEF METHOD_DEF},
 *  {@link TokenTypes#RESOURCE_SPECIFICATION RESOURCE_SPECIFICATION},
 *  {@link TokenTypes#SUPER_CTOR_CALL SUPER_CTOR_CALL},
 *  {@link TokenTypes#QUESTION QUESTION},
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="ParenPad"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check to require spaces for the
 * parentheses of constructor, method, and super constructor invocations is:
 * </p>
 * <pre>
 * &lt;module name="ParenPad"&gt;
 *     &lt;property name="tokens"
 *               value="CTOR_CALL, METHOD_CALL, SUPER_CTOR_CALL"/&gt;
 *     &lt;property name="option" value="space"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Oliver Burn
 * @author Vladislav Lisetskiy
 */
public class ParenPadCheck extends AbstractParenPadCheck {

    /**
     * The array of Acceptable Tokens.
     */
    private final int[] acceptableTokens;

    /**
     * Initializes and sorts acceptableTokens to make binary search over it possible.
     */
    public ParenPadCheck() {
        acceptableTokens = makeAcceptableTokens();
        Arrays.sort(acceptableTokens);
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
        return CommonUtils.EMPTY_INT_ARRAY;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.METHOD_CALL:
                processLeft(ast);
                processRight(ast.findFirstToken(TokenTypes.RPAREN));
                processExpression(ast);
                break;
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
                visitNewEnumConstDefAnnotationSync(ast);
                break;
            default:
                processLeft(ast.findFirstToken(TokenTypes.LPAREN));
                processRight(ast.findFirstToken(TokenTypes.RPAREN));
        }
    }

    /**
     * Checks parens in {@link TokenTypes#ENUM_CONSTANT_DEF}, {@link TokenTypes#ANNOTATION}
     * {@link TokenTypes#LITERAL_SYNCHRONIZED} and {@link TokenTypes#LITERAL_NEW}.
     * @param ast the token to check.
     */
    private void visitNewEnumConstDefAnnotationSync(DetailAST ast) {
        final DetailAST parenAst = ast.findFirstToken(TokenTypes.LPAREN);
        if (parenAst != null) {
            processLeft(parenAst);
            processRight(ast.findFirstToken(TokenTypes.RPAREN));
        }
    }

    /**
     * Checks parens in {@link TokenTypes#LITERAL_FOR}.
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
     * @param ast the token to check.
     */
    private void processExpression(DetailAST ast) {
        if (ast.branchContains(TokenTypes.LPAREN)) {
            DetailAST childAst = ast.getFirstChild();
            while (childAst != null) {
                if (childAst.getType() == TokenTypes.LPAREN) {
                    processLeft(childAst);
                    processExpression(childAst);
                }
                else if (childAst.getType() == TokenTypes.RPAREN && !isInTypecast(childAst)) {
                    processRight(childAst);
                }
                else if (!isAcceptableToken(childAst)) {
                    //Traverse all subtree tokens which will never be configured
                    //to be launched in visitToken()
                    processExpression(childAst);
                }
                childAst = childAst.getNextSibling();
            }
        }
    }

    /**
     * Checks whether AcceptableTokens contains the given ast.
     * @param ast the token to check.
     * @return true if the ast is in AcceptableTokens.
     */
    private boolean isAcceptableToken(DetailAST ast) {
        boolean result = false;
        if (Arrays.binarySearch(acceptableTokens, ast.getType()) >= 0) {
            result = true;
        }
        return result;
    }

    /**
     * @return acceptableTokens.
     */
    private static int[] makeAcceptableTokens() {
        return new int[] {TokenTypes.ANNOTATION,
            TokenTypes.ANNOTATION_FIELD_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.CTOR_DEF,
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
        };
    }

    /**
     * Checks whether {@link TokenTypes#RPAREN} is a closing paren
     * of a {@link TokenTypes#TYPECAST}.
     * @param ast of a {@link TokenTypes#RPAREN} to check.
     * @return true if ast is a closing paren of a {@link TokenTypes#TYPECAST}.
     */
    private static boolean isInTypecast(DetailAST ast) {
        boolean result = false;
        if (ast.getParent().getType() == TokenTypes.TYPECAST) {
            final DetailAST firstRparen = ast.getParent().findFirstToken(TokenTypes.RPAREN);
            if (firstRparen.getLineNo() == ast.getLineNo()
                    && firstRparen.getColumnNo() == ast.getColumnNo()) {
                result = true;
            }
        }
        return result;
    }

    /**
     * Checks that a token follows an empty for iterator.
     * @param ast the token to check
     * @return whether a token follows an empty for iterator
     */
    private static boolean isFollowsEmptyForIterator(DetailAST ast) {
        boolean result = false;
        final DetailAST parent = ast.getParent();
        //Only traditional for statements are examined, not for-each statements
        if (parent.findFirstToken(TokenTypes.FOR_EACH_CLAUSE) == null) {
            final DetailAST forIterator =
                parent.findFirstToken(TokenTypes.FOR_ITERATOR);
            result = forIterator.getChildCount() == 0;
        }
        return result;
    }

    /**
     * Checks that a token precedes an empty for initializer.
     * @param ast the token to check
     * @return whether a token precedes an empty for initializer
     */
    private static boolean isPrecedingEmptyForInit(DetailAST ast) {
        boolean result = false;
        final DetailAST parent = ast.getParent();
        //Only traditional for statements are examined, not for-each statements
        if (parent.findFirstToken(TokenTypes.FOR_EACH_CLAUSE) == null) {
            final DetailAST forIterator =
                    parent.findFirstToken(TokenTypes.FOR_INIT);
            result = forIterator.getChildCount() == 0;
        }
        return result;
    }
}
