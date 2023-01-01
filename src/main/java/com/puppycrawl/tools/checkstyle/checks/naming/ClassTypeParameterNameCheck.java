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
 * Checks that class type parameter names conform to a specified pattern.
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
 * &lt;module name="ClassTypeParameterName"/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class MyClass1&lt;T&gt; {}        // OK
 * class MyClass2&lt;t&gt; {}        // violation
 * class MyClass3&lt;abc&gt; {}      // violation
 * class MyClass4&lt;LISTENER&gt; {} // violation
 * class MyClass5&lt;RequestT&gt; {} // violation
 * </pre>
 * <p>
 * To configure the check for names that are uppercase word:
 * </p>
 * <pre>
 * &lt;module name="ClassTypeParameterName"&gt;
 *   &lt;property name="format" value="^[A-Z]{2,}$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class MyClass1&lt;T&gt; {}        // violation
 * class MyClass2&lt;t&gt; {}        // violation
 * class MyClass3&lt;abc&gt; {}      // violation
 * class MyClass4&lt;LISTENER&gt; {} // OK
 * class MyClass5&lt;RequestT&gt; {} // violation
 * </pre>
 * <p>
 * To configure the check for names that are camel case word with T as suffix (
 * <a href="https://checkstyle.org/styleguides/google-java-style-20180523/javaguide.html#s5.2.8-type-variable-names">
 * Google Style</a>):
 * </p>
 * <pre>
 * &lt;module name="ClassTypeParameterName"&gt;
 *   &lt;property name="format" value="(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * class MyClass1&lt;T&gt; {}        // violation
 * class MyClass2&lt;t&gt; {}        // violation
 * class MyClass3&lt;abc&gt; {}      // violation
 * class MyClass4&lt;LISTENER&gt; {} // violation
 * class MyClass5&lt;RequestT&gt; {} // OK
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
public class ClassTypeParameterNameCheck
    extends AbstractNameCheck {

    /** Creates a new {@code ClassTypeParameterNameCheck} instance. */
    public ClassTypeParameterNameCheck() {
        super("^[A-Z]$");
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public final int[] getAcceptableTokens() {
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
        return location.getType() == TokenTypes.CLASS_DEF;
    }

}
