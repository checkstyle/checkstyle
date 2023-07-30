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

package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.StatelessCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Implements Joshua Bloch, Effective Java, Item 17 -
 * Use Interfaces only to define types.
 * </p>
 * <p>
 * According to Bloch, an interface should describe a <em>type</em>. It is therefore
 * inappropriate to define an interface that does not contain any methods
 * but only constants. The Standard interface
 * <a href="https://docs.oracle.com/javase/8/docs/api/javax/swing/SwingConstants.html">
 * javax.swing.SwingConstants</a> is an example of an interface that would be flagged by this check.
 * </p>
 * <p>
 * The check can be configured to also disallow marker interfaces like {@code java.io.Serializable},
 * that do not contain methods or constants at all.
 * </p>
 * <ul>
 * <li>
 * Property {@code allowMarkerInterfaces} - Control whether marker interfaces
 * like Serializable are allowed.
 * Type is {@code boolean}.
 * Default value is {@code true}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="InterfaceIsType"/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public interface Test1 { // violation
 *     int a = 3;
 *
 * }
 *
 * public interface Test2 { // OK
 *
 * }
 *
 * public interface Test3 { // OK
 *     int a = 3;
 *     void test();
 * }
 * </pre>
 * <p>
 * To configure the check to report violation so that it doesn't allow Marker Interfaces:
 * </p>
 * <pre>
 * &lt;module name=&quot;InterfaceIsType&quot;&gt;
 *   &lt;property name=&quot;allowMarkerInterfaces&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * public interface Test1 { // violation
 *     int a = 3;
 * }
 *
 * public interface Test2 { // violation
 *
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
 * {@code interface.type}
 * </li>
 * </ul>
 *
 * @since 3.1
 */
@StatelessCheck
public final class InterfaceIsTypeCheck
        extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "interface.type";

    /** Control whether marker interfaces like Serializable are allowed. */
    private boolean allowMarkerInterfaces = true;

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {TokenTypes.INTERFACE_DEF};
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(DetailAST ast) {
        final DetailAST objBlock =
                ast.findFirstToken(TokenTypes.OBJBLOCK);
        final DetailAST methodDef =
                objBlock.findFirstToken(TokenTypes.METHOD_DEF);
        final DetailAST variableDef =
                objBlock.findFirstToken(TokenTypes.VARIABLE_DEF);
        final boolean methodRequired =
                !allowMarkerInterfaces || variableDef != null;

        if (methodDef == null && methodRequired) {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Setter to control whether marker interfaces like Serializable are allowed.
     *
     * @param flag whether to allow marker interfaces or not
     */
    public void setAllowMarkerInterfaces(boolean flag) {
        allowMarkerInterfaces = flag;
    }

}
