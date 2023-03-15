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
 * Checks that interface type parameter names conform to a specified pattern.
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
 * &lt;module name="InterfaceTypeParameterName"/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * interface FirstInterface&lt;T&gt; {} // OK
 * interface SecondInterface&lt;t&gt; {} // violation, name 't' must match pattern '^[A-Z]$'
 * </pre>
 * <p>
 * An example of how to configure the check for names that are only a single
 * letter is:
 * </p>
 * <pre>
 * &lt;module name="InterfaceTypeParameterName"&gt;
 *    &lt;property name="format" value="^[a-zA-Z]$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * interface FirstInterface&lt;T&gt; {} // OK
 * interface SecondInterface&lt;t&gt; {} // OK
 * interface ThirdInterface&lt;type&gt; {} // violation, name 'type' must
 *                                         // match pattern '^[a-zA-Z]$'
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
 * @since 5.8
 */
public class InterfaceTypeParameterNameCheck
    extends AbstractNameCheck {

    /** Creates a new {@code InterfaceTypeParameterNameCheck} instance. */
    public InterfaceTypeParameterNameCheck() {
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
        return location.getType() == TokenTypes.INTERFACE_DEF;
    }

}
