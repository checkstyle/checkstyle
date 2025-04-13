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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * <div>
 * Checks the number of methods declared in each type declaration by access modifier
 * or total count.
 * </div>
 *
 * <p>
 * This check can be configured to flag classes that define too many methods
 * to prevent the class from getting too complex. Counting can be customized
 * to prevent too many total methods in a type definition ({@code maxTotal}),
 * or to prevent too many methods of a specific access modifier ({@code private},
 * {@code package}, {@code protected} or {@code public}). Each count is completely
 * separated to customize how many methods of each you want to allow. For example,
 * specifying a {@code maxTotal} of 10, still means you can prevent more than 0
 * {@code maxPackage} methods. A violation won't appear for 8 public methods,
 * but one will appear if there is also 3 private methods or any package-private methods.
 * </p>
 *
 * <p>
 * Methods defined in anonymous classes are not counted towards any totals.
 * Counts only go towards the main type declaration parent, and are kept separate
 * from it's children's inner types.
 * </p>
 * <pre>
 * public class ExampleClass {
 *   public enum Colors {
 *     RED, GREEN, YELLOW;
 *
 *     public String getRGB() { ... } // NOT counted towards ExampleClass
 *   }
 *
 *   public void example() { // counted towards ExampleClass
 *     Runnable r = (new Runnable() {
 *       public void run() { ... } // NOT counted towards ExampleClass, won't produce any violations
 *     });
 *   }
 *
 *   public static class InnerExampleClass {
 *     protected void example2() { ... } // NOT counted towards ExampleClass,
 *                                    // but counted towards InnerExampleClass
 *   }
 * }
 * </pre>
 * <ul>
 * <li>
 * Property {@code maxPackage} - Specify the maximum number of {@code package} methods allowed.
 * Type is {@code int}.
 * Default value is {@code 100}.
 * </li>
 * <li>
 * Property {@code maxPrivate} - Specify the maximum number of {@code private} methods allowed.
 * Type is {@code int}.
 * Default value is {@code 100}.
 * </li>
 * <li>
 * Property {@code maxProtected} - Specify the maximum number of {@code protected} methods allowed.
 * Type is {@code int}.
 * Default value is {@code 100}.
 * </li>
 * <li>
 * Property {@code maxPublic} - Specify the maximum number of {@code public} methods allowed.
 * Type is {@code int}.
 * Default value is {@code 100}.
 * </li>
 * <li>
 * Property {@code maxTotal} - Specify the maximum number of methods allowed at all scope levels.
 * Type is {@code int}.
 * Default value is {@code 100}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check
 * Type is {@code java.lang.String[]}.
 * Validation type is {@code tokenSet}.
 * Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_CONSTANT_DEF">
 * ENUM_CONSTANT_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#RECORD_DEF">
 * RECORD_DEF</a>.
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
 * {@code too.many.methods}
 * </li>
 * <li>
 * {@code too.many.packageMethods}
 * </li>
 * <li>
 * {@code too.many.privateMethods}
 * </li>
 * <li>
 * {@code too.many.protectedMethods}
 * </li>
 * <li>
 * {@code too.many.publicMethods}
 * </li>
 * </ul>
 *
 * @since 5.3
 */
@FileStatefulCheck
public final class MethodCountCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PRIVATE_METHODS = "too.many.privateMethods";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PACKAGE_METHODS = "too.many.packageMethods";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PROTECTED_METHODS = "too.many.protectedMethods";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PUBLIC_METHODS = "too.many.publicMethods";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_MANY_METHODS = "too.many.methods";

    /** Default maximum number of methods. */
    private static final int DEFAULT_MAX_METHODS = 100;

    /** Maintains stack of counters, to support inner types. */
    private final Deque<MethodCounter> counters = new ArrayDeque<>();

    /** Specify the maximum number of {@code private} methods allowed. */
    private int maxPrivate = DEFAULT_MAX_METHODS;
    /** Specify the maximum number of {@code package} methods allowed. */
    private int maxPackage = DEFAULT_MAX_METHODS;
    /** Specify the maximum number of {@code protected} methods allowed. */
    private int maxProtected = DEFAULT_MAX_METHODS;
    /** Specify the maximum number of {@code public} methods allowed. */
    private int maxPublic = DEFAULT_MAX_METHODS;
    /** Specify the maximum number of methods allowed at all scope levels. */
    private int maxTotal = DEFAULT_MAX_METHODS;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public void visitToken(DetailAST ast) {
        if (ast.getType() == TokenTypes.METHOD_DEF) {
            if (isInLatestScopeDefinition(ast)) {
                raiseCounter(ast);
            }
        }
        else {
            counters.push(new MethodCounter(ast));
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        if (ast.getType() != TokenTypes.METHOD_DEF) {
            final MethodCounter counter = counters.pop();

            checkCounters(counter, ast);
        }
    }

    /**
     * Checks if there is a scope definition to check and that the method is found inside that scope
     * (class, enum, etc.).
     *
     * @param methodDef
     *        The method to analyze.
     * @return {@code true} if the method is part of the latest scope definition and should be
     *         counted.
     */
    private boolean isInLatestScopeDefinition(DetailAST methodDef) {
        boolean result = false;

        if (!counters.isEmpty()) {
            final DetailAST latestDefinition = counters.peek().getScopeDefinition();

            result = latestDefinition == methodDef.getParent().getParent();
        }

        return result;
    }

    /**
     * Determine the visibility modifier and raise the corresponding counter.
     *
     * @param method
     *            The method-subtree from the AbstractSyntaxTree.
     */
    private void raiseCounter(DetailAST method) {
        final MethodCounter actualCounter = counters.peek();
        final Scope scope = ScopeUtil.getScope(method);
        actualCounter.increment(scope);
    }

    /**
     * Check the counters and report violations.
     *
     * @param counter the method counters to check
     * @param ast to report violations against.
     */
    private void checkCounters(MethodCounter counter, DetailAST ast) {
        checkMax(maxPrivate, counter.value(Scope.PRIVATE),
            MSG_PRIVATE_METHODS, ast);
        checkMax(maxPackage, counter.value(Scope.PACKAGE),
            MSG_PACKAGE_METHODS, ast);
        checkMax(maxProtected, counter.value(Scope.PROTECTED),
            MSG_PROTECTED_METHODS, ast);
        checkMax(maxPublic, counter.value(Scope.PUBLIC),
            MSG_PUBLIC_METHODS, ast);
        checkMax(maxTotal, counter.getTotal(), MSG_MANY_METHODS, ast);
    }

    /**
     * Utility for reporting if a maximum has been exceeded.
     *
     * @param max the maximum allowed value
     * @param value the actual value
     * @param msg the message to log. Takes two arguments of value and maximum.
     * @param ast the AST to associate with the message.
     */
    private void checkMax(int max, int value, String msg, DetailAST ast) {
        if (max < value) {
            log(ast, msg, value, max);
        }
    }

    /**
     * Setter to specify the maximum number of {@code private} methods allowed.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxPrivate(int value) {
        maxPrivate = value;
    }

    /**
     * Setter to specify the maximum number of {@code package} methods allowed.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxPackage(int value) {
        maxPackage = value;
    }

    /**
     * Setter to specify the maximum number of {@code protected} methods allowed.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxProtected(int value) {
        maxProtected = value;
    }

    /**
     * Setter to specify the maximum number of {@code public} methods allowed.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxPublic(int value) {
        maxPublic = value;
    }

    /**
     * Setter to specify the maximum number of methods allowed at all scope levels.
     *
     * @param value the maximum allowed.
     * @since 5.3
     */
    public void setMaxTotal(int value) {
        maxTotal = value;
    }

    /**
     * Marker class used to collect data about the number of methods per
     * class. Objects of this class are used on the Stack to count the
     * methods for each class and layer.
     */
    private static final class MethodCounter {

        /** Maintains the counts. */
        private final Map<Scope, Integer> counts = new EnumMap<>(Scope.class);
        /**
         * The surrounding scope definition (class, enum, etc.) which the method counts are
         * connected to.
         */
        private final DetailAST scopeDefinition;
        /** Tracks the total. */
        private int total;

        /**
         * Creates an interface.
         *
         * @param scopeDefinition
         *        The surrounding scope definition (class, enum, etc.) which to count all methods
         *        for.
         */
        private MethodCounter(DetailAST scopeDefinition) {
            this.scopeDefinition = scopeDefinition;
        }

        /**
         * Increments to counter by one for the supplied scope.
         *
         * @param scope the scope counter to increment.
         */
        private void increment(Scope scope) {
            total++;
            counts.put(scope, 1 + value(scope));
        }

        /**
         * Gets the value of a scope counter.
         *
         * @param scope the scope counter to get the value of
         * @return the value of a scope counter
         */
        private int value(Scope scope) {
            Integer value = counts.get(scope);
            if (value == null) {
                value = 0;
            }
            return value;
        }

        /**
         * Returns the surrounding scope definition (class, enum, etc.) which the method counts
         * are connected to.
         *
         * @return the surrounding scope definition
         */
        private DetailAST getScopeDefinition() {
            return scopeDefinition;
        }

        /**
         * Fetches total number of methods.
         *
         * @return the total number of methods.
         */
        private int getTotal() {
            return total;
        }

    }

}
