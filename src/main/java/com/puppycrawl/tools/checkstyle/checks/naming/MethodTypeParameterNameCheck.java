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
 * Checks that method type parameter names conform to a specified pattern.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers.
 * Type is {@code java.util.regex.Pattern}.
 * Default value is {@code "^[A-Z]$"}.
 * </li>
 * </ul>
 * <p>
 * To configure the check:
 * </p>
 * <pre>
 * &lt;module name=&quot;MethodTypeParameterName&quot;/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   public &lt;T&gt; void method1() {} // OK
 *   public &lt;a&gt; void method2() {} // violation,  name 'a' must match pattern '^[A-Z]$'
 *   public &lt;K, V&gt; void method3() {} // OK
 *   public &lt;k, V&gt; void method4() {} // violation, name 'k' must match pattern '^[A-Z]$'
 * }
 * </pre>
 * <p>
 * An example of how to configure the check for names that are only a single letter is:
 * </p>
 * <pre>
 * &lt;module name=&quot;MethodTypeParameterName&quot;&gt;
 *    &lt;property name=&quot;format&quot; value=&quot;^[a-zA-Z]$&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * class MyClass {
 *   public &lt;T&gt; void method1() {} // OK
 *   public &lt;a&gt; void method2() {} // OK
 *   public &lt;K, V&gt; void method3() {} // OK
 *   public &lt;k, V&gt; void method4() {} // OK
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
 * @since 5.0
 */
public class MethodTypeParameterNameCheck
    extends AbstractNameCheck {

    /** Creates a new {@code MethodTypeParameterNameCheck} instance. */
    public MethodTypeParameterNameCheck() {
        super("^[A-Z]$");
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
            TokenTypes.TYPE_PARAMETER,
        };
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        final DetailAST location =
            ast.getParent().getParent();
        return location.getType() == TokenTypes.METHOD_DEF;
    }

}
