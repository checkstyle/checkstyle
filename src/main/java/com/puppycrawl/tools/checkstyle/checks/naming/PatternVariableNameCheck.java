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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks that pattern variable names conform to a specified pattern.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[a-z][a-zA-Z0-9]*$"}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name="PatternVariableName"/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *     MyClass(Object o1){
 *         if (o1 instanceof String STRING) { // violation, name 'STRING' must
 *                                            // match pattern '^[a-z][a-zA-Z0-9]*$'
 *         }
 *         if (o1 instanceof Integer num) { // OK
 *         }
 *     }
 * }
 * </pre>
 * <p>
 * An example of how to configure the check for names that have a lower case letter, followed by
 * letters and digits, optionally separated by underscore:
 * </p>
 * <pre>
 * &lt;module name="PatternVariableName"&gt;
 *   &lt;property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *     MyClass(Object o1){
 *         if (o1 instanceof String STR) { // violation, name 'STR' must
 *                                         // match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 *         }
 *         if (o1 instanceof Integer num) { // OK
 *         }
 *         if (o1 instanceof Integer num_1) { // OK
 *         }
 *     }
 * }
 * </pre>
 * <p>
 * An example of how to configure the check to that all variables have 3 or more chars in name:
 * </p>
 * <pre>
 * &lt;module name="PatternVariableName"&gt;
 *   &lt;property name="format" value="^[a-z][_a-zA-Z0-9]{2,}$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *     MyClass(Object o1){
 *         if (o1 instanceof String s) { // violation, name 's' must
 *                                       // match pattern '^[a-z][_a-zA-Z0-9]{2,}$'
 *         }
 *         if (o1 instanceof Integer num) { // OK
 *         }
 *     }
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
 * {@code name.invalidPattern}
 * </li>
 * </ul>
 *
 * @since 8.36
 */
public class PatternVariableNameCheck extends AbstractNameCheck {

    /** Creates a new {@code PatternVariableNameCheck} instance. */
    public PatternVariableNameCheck() {
        super("^[a-z][a-zA-Z0-9]*$");
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.PATTERN_VARIABLE_DEF,
        };
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        return true;
    }
}
