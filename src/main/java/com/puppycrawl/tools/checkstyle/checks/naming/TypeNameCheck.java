////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

/**
 * <p>
 * Checks that type names conform to a format specified
 * by the format property. The format is a
 * {@link java.util.regex.Pattern regular expression} and defaults to
 * <strong>^[A-Z][a-zA-Z0-9]*$</strong>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="TypeName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that begin with
 * a lower case letter, followed by letters, digits, and underscores is:
 * </p>
 * <pre>
 * &lt;module name="TypeName"&gt;
 *    &lt;property name="format" value="^[a-z](_?[a-zA-Z0-9]+)*$"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Oliver Burn
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
        return CommonUtils.EMPTY_INT_ARRAY;
    }
}
