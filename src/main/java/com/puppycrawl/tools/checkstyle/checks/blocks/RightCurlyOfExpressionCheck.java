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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <div>
 * Checks the placement of right curly braces (<code>'}'</code>) for expression blocks:
 * array initializers, block-bodied lambda expressions and anonymous classes.
 * </div>
 *
 * <p>
 * The right curly brace of an expression block is different from the right curly brace
 * of a code block, because it is always followed by another part of the expression
 * or the statement, for example a semicolon, a closing parenthesis or a method call.
 * For this reason the policies of this check are defined by the position of the right
 * curly brace in relation to the previous part of the expression, and not in relation
 * to the part that follows it. Placement of right curly braces for code blocks
 * is validated by {@code RightCurly} check.
 * </p>
 *
 * <p>
 * It is possible to enforce three different policies:
 * </p>
 * <ul>
 * <li>
 * {@code on_same_line} - The right curly brace must be on the same line as the
 * previous part of the expression:
 * {@snippet lang="text" :
 * String[] names = new String[] {"One", "Two", };
 * Runnable action = () -> { doSomething(); };
 * }
 * </li>
 * <li>
 * {@code on_new_line} - The right curly brace must be on a new line:
 * {@snippet lang="text" :
 * String[] names = new String[] {"One", "Two",
 * };
 * Runnable action = () -> { doSomething();
 * };
 * }
 * </li>
 * <li>
 * {@code on_new_line_or_singleline} - The right curly brace must be on a new line,
 * unless the whole expression block is on a single line:
 * {@snippet lang="text" :
 * // Single-line format of block
 * String[] names = new String[] {"One", "Two", };
 *
 * // Multi-line format of block
 * String[] names = new String[] {"One", "Two",
 * };
 * }
 * </li>
 * </ul>
 *
 * <p>
 * For multi-dimensional arrays only the outer right curly brace is validated.
 * For anonymous classes only the right curly brace of the class body is validated,
 * the right curly brace of an instance initializer inside such class is not
 * an expression brace and is not validated.
 * </p>
 *
 * @since 14.5.0
 */
@StatelessCheck
public class RightCurlyOfExpressionCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_LINE_NEW = "line.new";

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY_LINE_PREVIOUS = "line.previous";

    /** The right curly brace, passed as an argument to violation messages. */
    private static final String RIGHT_CURLY = "}";

    /**
     * Specify the policy on placement of a right curly brace (<code>'}'</code>)
     * of an expression block.
     */
    private RightCurlyOfExpressionOption option =
            RightCurlyOfExpressionOption.ON_NEW_LINE_OR_SINGLELINE;

    /**
     * Creates a new {@code RightCurlyOfExpressionCheck} instance.
     */
    public RightCurlyOfExpressionCheck() {
        // no code by default
    }

    /**
     * Setter to specify the policy on placement of a right curly brace (<code>'}'</code>)
     * of an expression block.
     *
     * @param optionStr string to decode option from
     * @throws IllegalArgumentException if unable to decode
     * @since 14.5.0
     */
    public void setOption(String optionStr) {
        option = RightCurlyOfExpressionOption.valueOf(optionStr.trim().toUpperCase(Locale.ENGLISH));
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
            TokenTypes.LAMBDA,
            TokenTypes.ARRAY_INIT,
            TokenTypes.LITERAL_NEW,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        DetailAST block = null;
        if (ast.getType() == TokenTypes.LAMBDA) {
            block = getFirstChildOfType(ast, TokenTypes.SLIST);
        }
        else if (ast.getType() == TokenTypes.LITERAL_NEW) {
            block = getFirstChildOfType(ast, TokenTypes.OBJBLOCK);
        }
        else if (!isNestedArrayInit(ast)) {
            block = ast;
        }
        if (block != null) {
            validateRightCurly(block);
        }
    }

    /**
     * Validates the placement of the right curly brace of the given expression block.
     *
     * @param block the block to validate, one of SLIST of a lambda, ARRAY_INIT
     *              or OBJBLOCK of an anonymous class
     */
    private void validateRightCurly(DetailAST block) {
        final DetailAST rightCurly = block.getLastChild();
        final DetailAST previousToken = getPreviousToken(block, rightCurly);
        final boolean onSameLineAsPrevious = previousToken.getLineNo() == rightCurly.getLineNo();
        if (option == RightCurlyOfExpressionOption.ON_SAME_LINE) {
            if (!onSameLineAsPrevious) {
                log(rightCurly, MSG_KEY_LINE_PREVIOUS, RIGHT_CURLY,
                        rightCurly.getColumnNo() + 1);
            }
        }
        else if (option == RightCurlyOfExpressionOption.ON_NEW_LINE) {
            if (onSameLineAsPrevious) {
                log(rightCurly, MSG_KEY_LINE_NEW, RIGHT_CURLY,
                        rightCurly.getColumnNo() + 1);
            }
        }
        else if (onSameLineAsPrevious
                && block.getLineNo() != rightCurly.getLineNo()) {
            log(rightCurly, MSG_KEY_LINE_NEW, RIGHT_CURLY,
                    rightCurly.getColumnNo() + 1);
        }
    }

    /**
     * Checks whether the given array initializer is nested inside another
     * array initializer. Only the outer array initializer is validated.
     *
     * @param ast the ARRAY_INIT token to check
     * @return true if the array initializer is nested inside another one
     */
    private static boolean isNestedArrayInit(DetailAST ast) {
        return ast.getParent().getType() == TokenTypes.ARRAY_INIT;
    }

    /**
     * Gets the token that is located right before the given right curly brace
     * inside the given block. For an empty block it is the block token itself,
     * which is located at the left curly brace.
     *
     * @param block the block to search in
     * @param rightCurly the right curly brace token of the block
     * @return the token before the right curly brace
     */
    private static DetailAST getPreviousToken(DetailAST block, DetailAST rightCurly) {
        DetailAST previous = null;
        DetailAST child = block.getFirstChild();
        while (child != null && child != rightCurly) {
            previous = getLastLeaf(child);
            child = child.getNextSibling();
        }
        if (previous == null) {
            previous = block;
        }
        return previous;
    }

    /**
     * Gets the last leaf of the subtree of the given token, in other words the
     * last token of the subtree in the source code.
     *
     * @param ast the token to get the last leaf of
     * @return the last leaf token
     */
    private static DetailAST getLastLeaf(DetailAST ast) {
        DetailAST leaf = ast;
        while (leaf.getFirstChild() != null) {
            leaf = leaf.getLastChild();
        }
        return leaf;
    }

    /**
     * Gets the first child of the given token that has the given type.
     *
     * @param ast the token to get the child of
     * @param type the type of the child to find
     * @return the first child of the given type, or null if there is no such child
     */
    private static DetailAST getFirstChildOfType(DetailAST ast, int type) {
        DetailAST result = null;
        DetailAST child = ast.getFirstChild();
        while (child != null && result == null) {
            if (child.getType() == type) {
                result = child;
            }
            child = child.getNextSibling();
        }
        return result;
    }

}
