////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FastStack;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import java.math.BigInteger;

/**
 * Base class for checks the calculate complexity based around methods.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author Oliver Burn
 */
public abstract class AbstractComplexityCheck
    extends Check
{
    /** the initial current value */
    private static final BigInteger INITIAL_VALUE = BigInteger.ONE;

    /** stack of values - all but the current value */
    private final FastStack<BigInteger> mValueStack = FastStack.newInstance();

    /** the current value */
    private BigInteger mCurrentValue = BigInteger.ZERO;

    /** threshold to report error for */
    private int mMax;

    /**
     * Creates an instance.
     * @param aMax the threshold of when to report an error
     */
    public AbstractComplexityCheck(int aMax)
    {
        mMax = aMax;
    }

    /**
     * @return the message ID to log violations with
     */
    protected abstract String getMessageID();

    /**
     * Hook called when visiting a token. Will not be called the method
     * definition tokens.
     *
     * @param aAST the token being visited
     */
    protected void visitTokenHook(DetailAST aAST)
    {
    }

    /**
     * Hook called when leaving a token. Will not be called the method
     * definition tokens.
     *
     * @param aAST the token being left
     */
    protected void leaveTokenHook(DetailAST aAST)
    {
    }

    @Override
    public final int[] getRequiredTokens()
    {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
        };
    }

    /** @return the maximum threshold allowed */
    public final int getMax()
    {
        return mMax;
    }

    /**
     * Set the maximum threshold allowed.
     *
     * @param aMax the maximum threshold
     */
    public final void setMax(int aMax)
    {
        mMax = aMax;
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.METHOD_DEF:
        case TokenTypes.INSTANCE_INIT:
        case TokenTypes.STATIC_INIT:
            visitMethodDef();
            break;
        default:
            visitTokenHook(aAST);
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        switch (aAST.getType()) {
        case TokenTypes.CTOR_DEF:
        case TokenTypes.METHOD_DEF:
        case TokenTypes.INSTANCE_INIT:
        case TokenTypes.STATIC_INIT:
            leaveMethodDef(aAST);
            break;
        default:
            leaveTokenHook(aAST);
        }
    }

    /**
     * @return the current value
     */
    protected final BigInteger getCurrentValue()
    {
        return mCurrentValue;
    }

    /**
     * Set the current value
     * @param aValue the new value
     */
    protected final void setCurrentValue(BigInteger aValue)
    {
        mCurrentValue = aValue;
    }

    /**
     * Increments the current value by a specified amount.
     *
     * @param aBy the amount to increment by
     */
    protected final void incrementCurrentValue(BigInteger aBy)
    {
        setCurrentValue(getCurrentValue().add(aBy));
    }

    /** Push the current value on the stack */
    protected final void pushValue()
    {
        mValueStack.push(mCurrentValue);
        mCurrentValue = INITIAL_VALUE;
    }

    /**
     * @return pop a value off the stack and make it the current value
     */
    protected final BigInteger popValue()
    {
        mCurrentValue = mValueStack.pop();
        return mCurrentValue;
    }

    /** Process the start of the method definition */
    private void visitMethodDef()
    {
        pushValue();
    }

    /**
     * Process the end of a method definition.
     *
     * @param aAST the token representing the method definition
     */
    private void leaveMethodDef(DetailAST aAST)
    {
        final BigInteger max = BigInteger.valueOf(mMax);
        if (mCurrentValue.compareTo(max) > 0) {
            log(aAST, getMessageID(), mCurrentValue, max);
        }
        popValue();
    }
}
