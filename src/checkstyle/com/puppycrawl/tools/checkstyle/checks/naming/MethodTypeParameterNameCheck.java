////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

/**
 * <p>
 * Checks that class type parameter names conform to a format specified
 * by the format property.  The format is a
 * {@link java.util.regex.Pattern regular expression} and defaults to
 * <strong>^[A-Z]$</strong>.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="MethodTypeParameterName"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check for names that are only a single
 * letter is
 * </p>
 * <pre>
 * &lt;module name="MethodTypeParameterName"&gt;
 *    &lt;property name="format" value="^[a-zA-Z]$"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Travis Schneeberger
 * @version 1.0
 */
public class MethodTypeParameterNameCheck
    extends AbstractTypeParameterNameCheck
{
    /** Creates a new <code>MethodTypeParameterNameCheck</code> instance. */
    public MethodTypeParameterNameCheck()
    {
        super("^[A-Z]$");
    }

    @Override
    protected final int getLocation()
    {
        return TokenTypes.METHOD_DEF;
    }
}
