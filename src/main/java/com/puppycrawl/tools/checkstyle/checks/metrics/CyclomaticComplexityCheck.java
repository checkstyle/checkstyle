////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks cyclomatic complexity against a specified limit. It is a measure of
 * the minimum number of possible paths through the source and therefore the
 * number of required tests, it is not a about quality of code! It is only
 * applied to methods, c-tors,
 * <a href="https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html">
 * static initializers and instance initializers</a>.
 * </p>
 * <p>
 * The complexity is equal to the number of decision points {@code + 1}.
 * Decision points: {@code if}, {@code while}, {@code do}, {@code for},
 * {@code ?:}, {@code catch}, {@code switch}, {@code case} statements and
 * operators {@code &amp;&amp;} and {@code ||} in the body of target.
 * </p>
 * <p>
 * By pure theory level 1-4 is considered easy to test, 5-7 OK, 8-10 consider
 * re-factoring to ease testing, and 11+ re-factor now as testing will be painful.
 * </p>
 * <p>
 * When it comes to code quality measurement by this metric level 10 is very
 * good level as a ultimate target (that is hard to archive). Do not be ashamed
 * to have complexity level 15 or even higher, but keep it below 20 to catch
 * really bad designed code automatically.
 * </p>
 * <p>
 * Please use Suppression to avoid violations on cases that could not be split
 * in few methods without damaging readability of code or encapsulation.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum threshold allowed.
 * Default value is {@code 10}.
 * </li>
 * <li>
 * Property {@code switchBlockAsSingleDecisionPoint} - Control whether to treat
 * the whole switch block as a single decision point.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_WHILE">
 * LITERAL_WHILE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_DO">
 * LITERAL_DO</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FOR">
 * LITERAL_FOR</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">
 * LITERAL_IF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_SWITCH">
 * LITERAL_SWITCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CASE">
 * LITERAL_CASE</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">
 * LITERAL_CATCH</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#QUESTION">
 * QUESTION</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LAND">
 * LAND</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LOR">
 * LOR</a>.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="CyclomaticComplexity"/&gt;
 * </pre>
 * <p>
 * To configure the check with a threshold of 15:
 * </p>
 * <pre>
 * &lt;module name="CyclomaticComplexity"&gt;
 *   &lt;property name="max" value="15"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Explanation on how complexity is calculated (switchBlockAsSingleDecisionPoint is set to false):
 * </p>
 * <pre>
 * class CC {
 *   // Cyclomatic Complexity = 12
 *   public void doSmth()  {         // 1
 *     if (a == b)  {                // 2
 *       if (a1 == b1                // 3
 *         &amp;&amp; c1 == d1) {            // 4
 *         fiddle();
 *       }
 *       else if (a2 == b2           // 5
 *               || c1 &lt; d1) {       // 6
 *         fiddle();
 *       }
 *       else {
 *         fiddle();
 *       }
 *     }
 *     else if (c == d) {            // 7
 *       while (c == d) {            // 8
 *         fiddle();
 *       }
 *     }
 *     else if (e == f) {
 *       for (n = 0; n &lt; h            // 9
 *             || n &lt; 6; n++) {       // 10
 *         fiddle();
 *       }
 *     }
 *       else {
 *         switch (z) {
 *           case 1:                  // 11
 *             fiddle();
 *             break;
 *           case 2:                  // 12
 *             fiddle();
 *             break;
 *           default:
 *             fiddle();
 *             break;
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * Explanation on how complexity is calculated (switchBlockAsSingleDecisionPoint is set to true):
 * </p>
 * <pre>
 * class SwitchExample {
 *   // Cyclomatic Complexity = 2
 *   public void doSmth()  {          // 1
 *     int z = 1;
 *     switch (z) {                   // 2
 *       case 1:
 *         foo1();
 *         break;
 *       case 2:
 *         foo2();
 *         break;
 *       default:
 *         fooDefault();
 *         break;
 *      }
 *   }
 * }
 * </pre>
 *
 * @since 3.2
 */
@FileStatefulCheck
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

    /** Control whether to treat the whole switch block as a single decision point. */
    private boolean switchBlockAsSingleDecisionPoint;

    /** The current value. */
    private BigInteger currentValue = INITIAL_VALUE;

    /** Specify the maximum threshold allowed. */
    private int max = DEFAULT_COMPLEXITY_VALUE;

    /**
     * Setter to control whether to treat the whole switch block as a single decision point.
     *
     * @param switchBlockAsSingleDecisionPoint whether to treat the whole switch
     *                                          block as a single decision point.
     */
    public void setSwitchBlockAsSingleDecisionPoint(boolean switchBlockAsSingleDecisionPoint) {
        this.switchBlockAsSingleDecisionPoint = switchBlockAsSingleDecisionPoint;
    }

    /**
     * Setter to specify the maximum threshold allowed.
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
    private void visitTokenHook(DetailAST ast) {
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
    private void incrementCurrentValue(BigInteger amount) {
        currentValue = currentValue.add(amount);
    }

    /** Push the current value on the stack. */
    private void pushValue() {
        valueStack.push(currentValue);
        currentValue = INITIAL_VALUE;
    }

    /**
     * Pops a value off the stack and makes it the current value.
     */
    private void popValue() {
        currentValue = valueStack.pop();
    }

    /** Process the start of the method definition. */
    private void visitMethodDef() {
        pushValue();
    }

}
