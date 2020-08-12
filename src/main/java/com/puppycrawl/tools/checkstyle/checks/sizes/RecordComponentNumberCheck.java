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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks the number of record components in the header of a record definition.
 * </p>
 * <ul>
 * <li>
 * Property {@code maxPrivate} - Specify the maximum number of {@code private} components allowed.
 * Type is {@code int}.
 * Default value is {@code 8}.
 * </li>
 * <li>
 * Property {@code maxPackage} - Specify the maximum number of {@code package} components allowed.
 * Type is {@code int}.
 * Default value is {@code 8}.
 * </li>
 * <li>
 * Property {@code maxProtected} - Specify the maximum number of {@code protected} components
 * allowed.
 * Type is {@code int}.
 * Default value is {@code 8}.
 * </li>
 * <li>
 * Property {@code maxPublic} - Specify the maximum number of {@code public} components allowed.
 * Type is {@code int}.
 * Default value is {@code 8}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="RecordComponentNumber"/&gt;
 * </pre>
 * <p>
 * To configure the check to allow 5 record components at all scope levels for a record definition:
 * </p>
 * <pre>
 * &lt;module name="RecordComponentNumber"&gt;
 *   &lt;property name="maxPrivate" value="5"/&gt;
 *   &lt;property name="maxPublic" value="5"/&gt;
 *   &lt;property name="maxPackage" value="5"/&gt;
 *   &lt;property name="maxProtected" value="5"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Java code example:
 * </p>
 * <pre>
 * public record MyRecord1(int x, int y, String str) { // ok, 3 components
 *     ...
 * }
 *
 * public record MyRecord2(int x, int y, String str,
 *                           Node node, Order order, Data data) { // violation, 6 components
 *     ...
 * }
 * </pre>
 * <p>
 * To configure the check to allow 10 record components for a public record definition,
 * but 3 for private record definitions:
 * </p>
 * <pre>
 * &lt;module name="RecordComponentNumber"&gt;
 *   &lt;property name="maxPrivate" value="3"/&gt;
 *   &lt;property name="maxPublic" value="10"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>
 * Java code example:
 * </p>
 * <pre>
 * public record MyRecord1(int x, int y, String str) { // ok, public record definition allowed 10
 *     ...
 * }
 *
 * private record MyRecord2(int x, int y, String str, Node node) { // violation
 *     ...                                // private record definition allowed 3 components
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
 * {@code too.many.packageComponents}
 * </li>
 * <li>
 * {@code too.many.privateComponents}
 * </li>
 * <li>
 * {@code too.many.protectedComponents}
 * </li>
 * <li>
 * {@code too.many.publicComponents}
 * </li>
 * </ul>
 *
 * @since 8.36
 */
@FileStatefulCheck
public class RecordComponentNumberCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PRIVATE_COMPONENTS = "too.many.privateComponents";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PACKAGE_COMPONENTS = "too.many.packageComponents";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PROTECTED_COMPONENTS = "too.many.protectedComponents";

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_PUBLIC_COMPONENTS = "too.many.publicComponents";

    /** Default maximum number of allowed components. */
    private static final int DEFAULT_MAX_COMPONENTS = 8;

    /** Specify the maximum number of {@code private} components allowed. */
    private int maxPrivate = DEFAULT_MAX_COMPONENTS;
    /** Specify the maximum number of {@code package} components allowed. */
    private int maxPackage = DEFAULT_MAX_COMPONENTS;
    /** Specify the maximum number of {@code protected} components allowed. */
    private int maxProtected = DEFAULT_MAX_COMPONENTS;
    /** Specify the maximum number of {@code public} components allowed. */
    private int maxPublic = DEFAULT_MAX_COMPONENTS;

    /**
     * Setter to specify the maximum number of {@code private} components allowed.
     *
     * @param value the maximum allowed.
     */
    public void setMaxPrivate(int value) {
        maxPrivate = value;
    }

    /**
     * Setter to specify the maximum number of {@code package} components allowed.
     *
     * @param value the maximum allowed.
     */
    public void setMaxPackage(int value) {
        maxPackage = value;
    }

    /**
     * Setter to specify the maximum number of {@code protected} components allowed.
     *
     * @param value the maximum allowed.
     */
    public void setMaxProtected(int value) {
        maxProtected = value;
    }

    /**
     * Setter to specify the maximum number of {@code public} components allowed.
     *
     * @param value the maximum allowed.
     */
    public void setMaxPublic(int value) {
        maxPublic = value;
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.RECORD_DEF,
        };
    }

    @Override
    public void visitToken(DetailAST ast) {
        // Instantiate new component counter
        final ComponentCounter counter = new ComponentCounter();

        // Count record components in this record definition
        countComponents(counter, ast);

        // Check counter and report violations
        checkCounter(counter, ast);
    }

    /**
     * Determine the visibility modifier, then iterate through the record component list
     * and increment the counter.
     *
     * @param counter the ComponentCounter instance for this check
     * @param ast the ast to check
     */
    private static void countComponents(ComponentCounter counter, DetailAST ast) {
        final DetailAST recordComponents =
                ast.findFirstToken(TokenTypes.RECORD_COMPONENTS);
        final DetailAST modifiers =
                ast.findFirstToken(TokenTypes.MODIFIERS);

        counter.setScope(ScopeUtil.getScopeFromMods(modifiers));

        TokenUtil.forEachChild(recordComponents,
                TokenTypes.RECORD_COMPONENT_DEF, node -> counter.increment());

    }

    /**
     * Check the counter and report violations.
     *
     * @param counter the component counter to check
     * @param ast to report violations against.
     */
    private void checkCounter(ComponentCounter counter, DetailAST ast) {
        final Scope scope = counter.getScope();
        if (scope == Scope.PRIVATE) {
            checkMax(maxPrivate, counter.getTotal(),
                    MSG_PRIVATE_COMPONENTS, ast);
        }
        else if (scope == Scope.PUBLIC) {
            checkMax(maxPublic, counter.getTotal(),
                    MSG_PUBLIC_COMPONENTS, ast);
        }
        else if (scope == Scope.PROTECTED) {
            checkMax(maxProtected, counter.getTotal(),
                    MSG_PROTECTED_COMPONENTS, ast);
        }
        else {
            checkMax(maxPackage, counter.getTotal(),
                    MSG_PACKAGE_COMPONENTS, ast);
        }
    }

    /**
     * Method for reporting if a maximum has been exceeded.
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
     * Counter class used to track the scope of the record definition and the number of
     * components in the record component list.
     */
    private static class ComponentCounter {

        /** The scope of the record definition. */
        private Scope scope;

        /** Tracks the total. */
        private int total;

        /**
         * Increments to counter by one.
         */
        private void increment() {
            total++;
        }

        /**
         * Fetches total number of components.
         *
         * @return the total number of components.
         */
        private int getTotal() {
            return total;
        }

        /**
         * Sets the scope of the record components we are counting.
         *
         * @param scope the scope of the record definition
         */
        private void setScope(Scope scope) {
            this.scope = scope;
        }

        /**
         * Gets the scope of the record components we are counting.
         *
         * @return the scope of this ComponentCounter
         */
        private Scope getScope() {
            return scope;
        }
    }

}
