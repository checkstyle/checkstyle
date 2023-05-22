///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
 * number of required tests, it is not about quality of code! It is only
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
 * really bad-designed code automatically.
 * </p>
 * <p>
 * Please use Suppression to avoid violations on cases that could not be split
 * in few methods without damaging readability of code or encapsulation.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum threshold allowed.
 * Type is {@code int}.
 * Default value is {@code 10}.
 * </li>
 * <li>
 * Property {@code switchBlockAsSingleDecisionPoint} - Control whether to treat
 * the whole switch block as a single decision point.
 * Type is {@code boolean}.
 * Default value is {@code false}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
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
 * Example:
 * </p>
 * <pre>
 * class CyclomaticComplexity {
 *   // Cyclomatic Complexity = 11
 *   int a, b, c, d, n;
 *   public void foo() { // 1, function declaration
 *     if (a == 1) { // 2, if
 *       fun1();
 *     } else if (a == b // 3, if
 *       &amp;&amp; a == c) { // 4, &amp;&amp; operator
 *       if (c == 2) { // 5, if
 *         fun2();
 *       }
 *     } else if (a == d) { // 6, if
 *       try {
 *         fun4();
 *       } catch (Exception e) { // 7, catch
 *       }
 *     } else {
 *       switch(n) {
 *         case 1: // 8, case
 *           fun1();
 *           break;
 *         case 2: // 9, case
 *           fun2();
 *           break;
 *         case 3: // 10, case
 *           fun3();
 *           break;
 *         default:
 *           break;
 *       }
 *     }
 *     d = a &lt; 0 ? -1 : 1; // 11, ternary operator
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check with a threshold of 4 and check only for while and do-while loops:
 * </p>
 * <pre>
 * &lt;module name="CyclomaticComplexity"&gt;
 *   &lt;property name="max" value="4"/&gt;
 *   &lt;property name="tokens" value="LITERAL_WHILE, LITERAL_DO"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class CyclomaticComplexity {
 *   // Cyclomatic Complexity = 5
 *   int a, b, c, d;
 *   public void foo() { // 1, function declaration
 *     while (a &lt; b // 2, while
 *       &amp;&amp; a &gt; c) {
 *       fun();
 *     }
 *     if (a == b) {
 *       do { // 3, do
 *         fun();
 *       } while (d);
 *     } else if (c == d) {
 *       while (c &gt; 0) { // 4, while
 *         fun();
 *       }
 *       do { // 5, do-while
 *         fun();
 *       } while (a);
 *     }
 *   }
 * }
 * </pre>
 * <p>
 * To configure the check to consider switch-case block as one decision point.
 * </p>
 * <pre>
 * &lt;module name="CyclomaticComplexity"&gt;
 *   &lt;property name="switchBlockAsSingleDecisionPoint" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Example:
 * </p>
 * <pre>
 * class CyclomaticComplexity {
 *   // Cyclomatic Complexity = 11
 *   int a, b, c, d, e, n;
 *   public void foo() { // 1, function declaration
 *     if (a == b) { // 2, if
 *       fun1();
 *     } else if (a == 0 // 3, if
 *       &amp;&amp; b == c) { // 4, &amp;&amp; operator
 *       if (c == -1) { // 5, if
 *         fun2();
 *       }
 *     } else if (a == c // 6, if
 *       || a == d) { // 7, || operator
 *       fun3();
 *     } else if (d == e) { // 8, if
 *       try {
 *         fun4();
 *       } catch (Exception e) { // 9, catch
 *       }
 *     } else {
 *       switch(n) { // 10, switch
 *         case 1:
 *           fun1();
 *           break;
 *         case 2:
 *           fun2();
 *           break;
 *         default:
 *           break;
 *       }
 *     }
 *     a = a &gt; 0 ? b : c; // 11, ternary operator
 *   }
 * }
 * </pre>
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code cyclomaticComplexity}
 * </li>
 * </ul>
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
            TokenTypes.COMPACT_CTOR_DEF,
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
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public final int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.COMPACT_CTOR_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
            case TokenTypes.COMPACT_CTOR_DEF:
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
            case TokenTypes.COMPACT_CTOR_DEF:
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
