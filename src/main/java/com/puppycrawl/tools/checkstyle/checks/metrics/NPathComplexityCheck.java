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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks the npath complexity against a specified limit (default = 200).
 * The npath metric computes the number of possible execution paths
 * through a function. Similar to the cyclomatic complexity but also
 * takes into account the nesting of conditional statements and
 * multi-part boolean expressions.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author o_sukhodolsky
 */
public final class NPathComplexityCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "npathComplexity";

    /** Default allowed complexity. */
    private static final int DEFAULT_MAX = 200;

    /** The initial current value. */
    private static final BigInteger INITIAL_VALUE = BigInteger.ONE;

    /** Stack of values - all but the current value. */
    private final Deque<BigInteger> valueStack = new ArrayDeque<>();

    /** The current value. */
    private BigInteger currentValue = INITIAL_VALUE;

    /** Threshold to report error for. */
    private int max = DEFAULT_MAX;

    /**
     * Set the maximum threshold allowed.
     *
     * @param max the maximum threshold
     */
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_IF:
            case TokenTypes.QUESTION:
            case TokenTypes.LITERAL_TRY:
            case TokenTypes.LITERAL_SWITCH:
                visitMultiplyingConditional();
                break;
            case TokenTypes.LITERAL_ELSE:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.LITERAL_CASE:
                visitAddingConditional();
                break;
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                visitMethodDef();
                break;
            default:
                break;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_IF:
            case TokenTypes.QUESTION:
            case TokenTypes.LITERAL_TRY:
            case TokenTypes.LITERAL_SWITCH:
                leaveMultiplyingConditional();
                break;
            case TokenTypes.LITERAL_ELSE:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.LITERAL_CASE:
                leaveAddingConditional();
                break;
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                leaveMethodDef(ast);
                break;
            default:
                break;
        }
    }

    /** Visits else, catch or case. */
    private void visitAddingConditional() {
        pushValue();
    }

    /** Leaves else, catch or case. */
    private void leaveAddingConditional() {
        currentValue = currentValue.subtract(BigInteger.ONE).add(popValue());
    }

    /** Visits while, do, for, if, try, ? (in ?::) or switch. */
    private void visitMultiplyingConditional() {
        pushValue();
    }

    /** Leaves while, do, for, if, try, ? (in ?::) or switch. */
    private void leaveMultiplyingConditional() {
        currentValue = currentValue.add(BigInteger.ONE).multiply(popValue());
    }

    /** Push the current value on the stack. */
    private void pushValue() {
        valueStack.push(currentValue);
        currentValue = INITIAL_VALUE;
    }

    /**
     * Pops a value off the stack and makes it the current value.
     * @return pop a value off the stack and make it the current value
     */
    private BigInteger popValue() {
        currentValue = valueStack.pop();
        return currentValue;
    }

    /** Process the start of the method definition. */
    private void visitMethodDef() {
        pushValue();
    }

    /**
     * Process the end of a method definition.
     *
     * @param ast the token representing the method definition
     */
    private void leaveMethodDef(DetailAST ast) {
        final BigInteger bigIntegerMax = BigInteger.valueOf(max);
        if (currentValue.compareTo(bigIntegerMax) > 0) {
            log(ast, MSG_KEY, currentValue, bigIntegerMax);
        }
        popValue();
    }
}
