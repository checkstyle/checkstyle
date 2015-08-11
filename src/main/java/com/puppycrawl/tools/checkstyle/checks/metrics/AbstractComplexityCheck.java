////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Base class for checks the calculate complexity based around methods.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author Oliver Burn
 */
public abstract class AbstractComplexityCheck
    extends Check {
    /** the initial current value */
    private static final BigInteger INITIAL_VALUE = BigInteger.ONE;

    /** stack of values - all but the current value */
    private final Deque<BigInteger> valueStack = new ArrayDeque<>();

    /** the current value */
    private BigInteger currentValue = BigInteger.ZERO;

    /** threshold to report error for */
    private int max;

    /**
     * Creates an instance.
     * @param max the threshold of when to report an error
     */
    protected AbstractComplexityCheck(int max) {
        this.max = max;
    }

    /**
     * @return the message ID to log violations with
     */
    protected abstract String getMessageID();

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
        };
    }

    /** @return the maximum threshold allowed */
    public final int getMax() {
        return max;
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
     * Hook called when visiting a token. Will not be called the method
     * definition tokens.
     *
     * @param ast the token being visited
     */
    protected void visitTokenHook(DetailAST ast) {
        // no code
    }

    /**
     * Hook called when leaving a token. Will not be called the method
     * definition tokens.
     *
     * @param ast the token being left
     */
    protected void leaveTokenHook(DetailAST ast) {
        // no code
    }

    /**
     * @return the current value
     */
    protected final BigInteger getCurrentValue() {
        return currentValue;
    }

    /**
     * Set the current value
     * @param value the new value
     */
    protected final void setCurrentValue(BigInteger value) {
        currentValue = value;
    }

    /**
     * Increments the current value by a specified amount.
     *
     * @param by the amount to increment by
     */
    protected final void incrementCurrentValue(BigInteger by) {
        setCurrentValue(getCurrentValue().add(by));
    }

    /** Push the current value on the stack */
    protected final void pushValue() {
        valueStack.push(currentValue);
        currentValue = INITIAL_VALUE;
    }

    /**
     * @return pop a value off the stack and make it the current value
     */
    protected final BigInteger popValue() {
        currentValue = valueStack.pop();
        return currentValue;
    }

    /** Process the start of the method definition */
    private void visitMethodDef() {
        pushValue();
    }

    /**
     * Process the end of a method definition.
     *
     * @param ast the token representing the method definition
     */
    private void leaveMethodDef(DetailAST ast) {
        final BigInteger bigIntegerMax = BigInteger.valueOf(getMax());
        if (currentValue.compareTo(bigIntegerMax) > 0) {
            log(ast, getMessageID(), currentValue, bigIntegerMax);
        }
        popValue();
    }
}
