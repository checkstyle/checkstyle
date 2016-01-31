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
 * Checks cyclomatic complexity against a specified limit. The complexity is
 * measured by the number of "if", "while", "do", "for", "?:", "catch",
 * "switch", "case", "&amp;&amp;" and "||" statements (plus one) in the body of
 * the member. It is a measure of the minimum number of possible paths through
 * the source and therefore the number of required tests. Generally 1-4 is
 * considered good, 5-7 ok, 8-10 consider re-factoring, and 11+ re-factor now!
 *
 * <p>Check has following properties:
 *
 * <p><b>switchBlockAsSingleDecisionPoint</b> - controls whether to treat the whole switch
 * block as a single decision point. Default value is <b>false</b>
 *
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author Oliver Burn
 * @author <a href="mailto:andreyselkin@gmail.com">Andrei Selkin</a>
 */
public class CyclomaticComplexityCheck
    extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "cyclomaticComplexity";

    /** The initial current value. */
    private static final BigInteger INITIAL_VALUE = BigInteger.ONE;

    /** Default allowed complexity. */
    private static final int DEFAULT_COMPLEXITY_VALUE = 10;

    /** Stack of values - all but the current value. */
    private final Deque<BigInteger> valueStack = new ArrayDeque<>();

    /** Whether to treat the whole switch block as a single decision point.*/
    private boolean switchBlockAsSingleDecisionPoint;

    /** The current value. */
    private BigInteger currentValue = INITIAL_VALUE;

    /** Threshold to report error for. */
    private int max = DEFAULT_COMPLEXITY_VALUE;

    /**
     * Sets whether to treat the whole switch block as a single decision point.
     * @param switchBlockAsSingleDecisionPoint whether to treat the whole switch
     *                                          block as a single decision point.
     */
    public void setSwitchBlockAsSingleDecisionPoint(boolean switchBlockAsSingleDecisionPoint) {
        this.switchBlockAsSingleDecisionPoint = switchBlockAsSingleDecisionPoint;
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
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
            TokenTypes.LAND,
            TokenTypes.LOR,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
            TokenTypes.LAND,
            TokenTypes.LOR,
        };
    }

    @Override
    public final int[] getRequiredTokens() {
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
                break;
        }
    }

    /**
     * Hook called when visiting a token. Will not be called the method
     * definition tokens.
     *
     * @param ast the token being visited
     */
    protected final void visitTokenHook(DetailAST ast) {
        if (switchBlockAsSingleDecisionPoint) {
            if (ast.getType() != TokenTypes.LITERAL_CASE) {
                incrementCurrentValue(BigInteger.ONE);
            }
        }
        else if (ast.getType() != TokenTypes.LITERAL_SWITCH) {
            incrementCurrentValue(BigInteger.ONE);
        }
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
}
