////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * This metric measures the number of instantiations of other classes
 * within the given class. This type of coupling is
 * not caused by inheritance or the object oriented paradigm.
 * Generally speaking, any abstract data type with other
 * abstract data types as members has data abstraction coupling; therefore,
 * if a class has a local variable that is an
 * instantiation (object) of another class, there is data abstraction coupling.
 * The higher the DAC, the more complex the
 * data structure (classes) of the system.
 *
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author o_sukhodolsky
 */
public final class ClassDataAbstractionCouplingCheck
    extends AbstractClassCouplingCheck
{
    /** Default allowed complexity. */
    private static final int DEFAULT_MAX = 7;

    /** Creates bew instance of the check. */
    public ClassDataAbstractionCouplingCheck()
    {
        super(DEFAULT_MAX);
        setTokens(new String[] {"LITERAL_NEW"});
    }

    /** {@inheritDoc} */
    public int[] getRequiredTokens()
    {
        return new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.LITERAL_NEW,
        };
    }

    /** {@inheritDoc} */
    protected String getLogMessageId()
    {
        return "classDataAbstractionCoupling";
    }
}
