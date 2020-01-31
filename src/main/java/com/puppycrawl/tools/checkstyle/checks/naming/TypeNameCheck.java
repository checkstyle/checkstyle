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

package com.puppycrawl.tools.checkstyle.checks.naming;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * <p>
 * Checks that type names conform to a specified pattern.
 * </p>
 * <ul>
 * <li>
 * Property {@code format} - Specifies valid identifiers. Default value is
 * {@code "^[A-Z][a-zA-Z0-9]*$"}.
 * </li>
 * <li>
 * Property {@code applyToPublic} - Controls whether to apply the check to public member.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToProtected} - Controls whether to apply the check to protected member.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToPackage} - Controls whether to apply the check to package-private member.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code applyToPrivate} - Controls whether to apply the check to private member.
 * Default value is {@code true}.
 * </li>
 * <li>
 * Property {@code tokens} - tokens to check Default value is:
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#CLASS_DEF">
 * CLASS_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INTERFACE_DEF">
 * INTERFACE_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ENUM_DEF">
 * ENUM_DEF</a>,
 * <a href="https://checkstyle.org/apidocs/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#ANNOTATION_DEF">
 * ANNOTATION_DEF</a>.
 * </li>
 * </ul>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name=&quot;TypeName&quot;/&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * public interface FirstName {} // OK
 * protected class SecondName {} // OK
 * enum Third_Name {} // violation, name 'Third_Name' must match pattern '^[A-Z][a-zA-Z0-9]*$'
 * private class FourthName_ {} // violation, name 'FourthName_'
 *                              // must match pattern '^[A-Z][a-zA-Z0-9]*$'
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with
 * a lower case letter, followed by letters, digits, and underscores.
 * Also, suppress the check from being applied to protected and private type:
 * </p>
 * <pre>
 * &lt;module name=&quot;TypeName&quot;&gt;
 *   &lt;property name=&quot;format&quot; value=&quot;^[a-z](_?[a-zA-Z0-9]+)*$&quot;/&gt;
 *   &lt;property name=&quot;applyToProtected&quot; value=&quot;false&quot;/&gt;
 *   &lt;property name=&quot;applyToPrivate&quot; value=&quot;false&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * public interface firstName {} // OK
 * public class SecondName {} // violation, name 'SecondName'
 *                            // must match pattern '^[a-z](_?[a-zA-Z0-9]+)*$'
 * protected class ThirdName {} // OK
 * private class FourthName {} // OK
 * </pre>
 * <p>
 * The following configuration element ensures that interface names begin with {@code "I_"},
 * followed by letters and digits:
 * </p>
 * <pre>
 * &lt;module name=&quot;TypeName&quot;&gt;
 *   &lt;property name=&quot;format&quot;
 *     value=&quot;^I_[a-zA-Z0-9]*$&quot;/&gt;
 *   &lt;property name=&quot;tokens&quot;
 *     value=&quot;INTERFACE_DEF&quot;/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p>Code Example:</p>
 * <pre>
 * public interface I_firstName {} // OK
 * interface SecondName {} // violation, name 'SecondName'
 *                         // must match pattern '^I_[a-zA-Z0-9]*$'
 * </pre>
 *
 * @since 3.0
 */
public class TypeNameCheck
    extends AbstractAccessControlNameCheck {

    /**
     * Default pattern for type name.
     */
    public static final String DEFAULT_PATTERN = "^[A-Z][a-zA-Z0-9]*$";

    /**
     * Creates a new {@code TypeNameCheck} instance.
     */
    public TypeNameCheck() {
        super(DEFAULT_PATTERN);
    }

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {TokenTypes.CLASS_DEF,
                          TokenTypes.INTERFACE_DEF,
                          TokenTypes.ENUM_DEF,
                          TokenTypes.ANNOTATION_DEF,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return CommonUtil.EMPTY_INT_ARRAY;
    }

}
