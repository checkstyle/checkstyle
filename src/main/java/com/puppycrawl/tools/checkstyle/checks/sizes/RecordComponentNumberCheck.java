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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <p>
 * Checks the number of record components in the
 * <a href="https://docs.oracle.com/javase/specs/jls/se14/preview/specs/records-jls.html#jls-8.10.1">
 * header</a> of a record definition.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum number of components allowed in the header of a
 * record definition.
 * Type is {@code int}.
 * Default value is {@code 8}.
 * </li>
 * <li>
 * Property {@code accessModifiers} - Access modifiers of record definitions where
 * the number of record components should be checked.
 * Type is {@code com.puppycrawl.tools.checkstyle.checks.naming.AccessModifierOption[]}.
 * Default value is {@code public, protected, package, private}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="RecordComponentNumber"/&gt;
 * </pre>
 * <p>
 * Java code example:
 * </p>
 * <pre>
 * public record MyRecord1(int x, int y) { // ok, 2 components
 *     ...
 * }
 *
 * record MyRecord2(int x, int y, String str,
 *                           Node node, Order order, Data data
 *                           String location, Date date, Image image) { // violation, 9 components
 *     ...
 * }
 * </pre>
 * <p>
 * To configure the check to allow 5 record components at all access modifier levels
 * for record definitions:
 * </p>
 * <pre>
 * &lt;module name="RecordComponentNumber"&gt;
 *   &lt;property name="max" value="5"/&gt;
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
 *   &lt;property name="max" value="3"/&gt;
 *   &lt;property name="accessModifiers" value="private"/&gt;
 * &lt;/module&gt;
 * &lt;module name="RecordComponentNumber"&gt;
 *   &lt;property name="max" value="10"/&gt;
 *   &lt;property name="accessModifiers" value="public"/&gt;
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
 * {@code too.many.components}
 * </li>
 * </ul>
 *
 * @since 8.36
 */
@StatelessCheck
public class RecordComponentNumberCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "too.many.components";

    /** Default maximum number of allowed components. */
    private static final int DEFAULT_MAX_COMPONENTS = 8;

    /** Specify the maximum number of components allowed in the header of a record definition. */
    private int max = DEFAULT_MAX_COMPONENTS;

    /**
     * Access modifiers of record definitions where the number
     * of record components should be checked.
     */
    private AccessModifierOption[] accessModifiers = {
        AccessModifierOption.PUBLIC,
        AccessModifierOption.PROTECTED,
        AccessModifierOption.PACKAGE,
        AccessModifierOption.PRIVATE,
    };

    /**
     * Setter to specify the maximum number of components allowed in the header
     * of a record definition.
     *
     * @param value the maximum allowed.
     */
    public void setMax(int value) {
        max = value;
    }

    /**
     * Setter to access modifiers of record definitions where the number of record
     * components should be checked.
     *
     * @param accessModifiers access modifiers of record definitions which should be checked.
     */
    public void setAccessModifiers(AccessModifierOption... accessModifiers) {
        this.accessModifiers =
                Arrays.copyOf(accessModifiers, accessModifiers.length);
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
        return getAcceptableTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final AccessModifierOption accessModifier =
            CheckUtil.getAccessModifierFromModifiersToken(ast);

        if (matchAccessModifiers(accessModifier)) {
            final DetailAST recordComponents =
                ast.findFirstToken(TokenTypes.RECORD_COMPONENTS);
            final int componentCount = countComponents(recordComponents);

            if (componentCount > max) {
                log(ast, MSG_KEY, componentCount, max);
            }
        }
    }

    /**
     * Method to count the number of record components in this record definition.
     *
     * @param recordComponents the ast to check
     * @return the number of record components in this record definition
     */
    private static int countComponents(DetailAST recordComponents) {
        final AtomicInteger count = new AtomicInteger(0);
        TokenUtil.forEachChild(recordComponents,
            TokenTypes.RECORD_COMPONENT_DEF,
            node -> count.getAndIncrement());
        return count.get();
    }

    /**
     * Checks whether a record definition has the correct access modifier to be checked.
     *
     * @param accessModifier the access modifier of the record definition.
     * @return whether the record definition matches the expected access modifier.
     */
    private boolean matchAccessModifiers(final AccessModifierOption accessModifier) {
        return Arrays.stream(accessModifiers)
                .anyMatch(modifier -> modifier == accessModifier);
    }
}
