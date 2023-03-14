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
 * Checks that record component names conform to a specified pattern.
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
 * &lt;module name="RecordComponentName"/&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * record MyRecord1(String value, int otherComponentName) {} // OK
 * record MyRecord2(String... Values) {} // violation, the record component name
 *                                     // should match the regular expression "^[a-z][a-zA-Z0-9]*$"
 * record MyRecord3(double my_number) {} // violation, the record component name
 *                                     // should match the regular expression "^[a-z][a-zA-Z0-9]*$"
 * </pre>
 * <p>
 * An example of how to configure the check for names that are only letters in lowercase:
 * </p>
 * <p>Configuration:</p>
 * <pre>
 * &lt;module name="RecordComponentName"&gt;
 *   &lt;property name="format" value="^[a-z]+$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Example:</p>
 * <pre>
 * record MyRecord1(String value, int other) {} // OK
 * record MyRecord2(String... strings) {} // OK
 * record MyRecord3(double myNumber) {} // violation, the record component name
 *                              // should match the regular expression "^[a-z]+$"
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
 * @since 8.40
 */
public class RecordComponentNameCheck extends AbstractNameCheck {

    /** Creates a new {@code RecordComponentNameCheck} instance. */
    public RecordComponentNameCheck() {
        super("^[a-z][a-zA-Z0-9]*$");
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
            TokenTypes.RECORD_COMPONENT_DEF,
        };
    }

    @Override
    protected final boolean mustCheckName(DetailAST ast) {
        return true;
    }

}
