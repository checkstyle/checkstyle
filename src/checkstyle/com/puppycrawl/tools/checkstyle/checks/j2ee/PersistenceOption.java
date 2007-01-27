////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2007  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.j2ee;

import com.puppycrawl.tools.checkstyle.checks.AbstractOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the policy for checking entity bean restrictions according
 * to whether persistence is bean-managed, container-managed, or mixed.
 * @author Rick Giles
 */
public final class PersistenceOption
    extends AbstractOption
{
    /** maps from a string representation to an option */
    private static final Map STR_TO_OPT = new HashMap();

    /**
     * Represents the policy that the persistence management may be
     * bean-managed or container-managed.
     */
    public static final PersistenceOption MIXED =
        new PersistenceOption("mixed");

    /**
     * Represents the bean-managed persistence policy.
     */
    public static final PersistenceOption BEAN =
        new PersistenceOption("bean");

    /**
     * Represents the container-managed persistence policy.
     */
    public static final PersistenceOption CONTAINER =
        new PersistenceOption("container");

    /**
     * Creates a new <code>PersistenceOption</code> instance.
     *
     * @param aStrRep the string representation
     */
    private PersistenceOption(String aStrRep)
    {
        super(aStrRep);
    }

    /** {@inheritDoc} */
    protected Map getStrToOpt()
    {
        return STR_TO_OPT;
    }
}
