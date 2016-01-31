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
 * Base class for checks the calculate complexity based around methods.
 * @deprecated Checkstyle will not support abstract checks anymore. Use
 *             {@link AbstractCheck} instead.
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author Oliver Burn
 * @noinspection AbstractClassNeverImplemented
 */
@Deprecated
public abstract class AbstractComplexityCheck
    extends AbstractCheck {
    /** The initial current value. */
    private static final BigInteger INITIAL_VALUE = BigInteger.ONE;

    /** Stack of values - all but the current value. */
    private final Deque<BigInteger> valueStack = new ArrayDeque<>();

    /** The current value. */
    private BigInteger currentValue = BigInteger.ZERO;

    /** Threshold to report error for. */
    private int max;

    /**
     * Creates an instance.
     * @param max the threshold of when to report an error
     */
    protected AbstractComplexityCheck(int max) {
        this.max = max;
    }

    /**
     * Gets the message ID to log violations with.
     * @return the message ID to log violations with
     */
    protected abstract String getMessageID();

    /**
     * Hook called when visiting a token. Will not be called the method
     * definition tokens.
     *
     * @param ast the token being visited
     */
    protected abstract void visitTokenHook(DetailAST ast);

    /**
     * Hook called when leaving a token. Will not be called the method
     * definition tokens.
     *
     * @param ast the token being left
     */
    protected abstract void leaveTokenHook(DetailAST ast);

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
        };
    }

    /**
     * Set the maximum threshold allowed.
     *
     * @param max the maximum threshold
     */
    public final void setMax(int max) {
        this.max = max;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                visitMethodDef();
                break;
            default:
                visitTokenHook(ast);
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                leaveMethodDef(ast);
                break;
            default:
                leaveTokenHook(ast);
        }
    }

    /**
     * Gets the current value.
     * @return the current value
     */
    protected final BigInteger getCurrentValue() {
        return currentValue;
    }

    /**
     * Set the current value.
     * @param value the new value
     */
    protected final void setCurrentValue(BigInteger value) {
        currentValue = value;
    }

    /**
     * Increments the current value by a specified amount.
     *
     * @param amount the amount to increment by
     */
    protected final void incrementCurrentValue(BigInteger amount) {
        currentValue = currentValue.add(amount);
    }

    /** Push the current value on the stack. */
    protected final void pushValue() {
        valueStack.push(currentValue);
        currentValue = INITIAL_VALUE;
    }

    /**
     * Pops a value off the stack and makes it the current value.
     * @return pop a value off the stack and make it the current value
     */
    protected final BigInteger popValue() {
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
            log(ast, getMessageID(), currentValue, bigIntegerMax);
        }
        popValue();
    }
}
