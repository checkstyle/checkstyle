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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.checks.AbstractOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the options for wrapping on an operator.
 *
 * @author Rick Giles
 * @version 1.0
 */
public final class OperatorWrapOption
    extends AbstractOption
{
    /** maps from a string representation to an option */
    private static final Map STR_TO_OPT = new HashMap();

    /** Require that the operator is on a new line. */
    public static final OperatorWrapOption NL = new OperatorWrapOption("nl");
    /** Require that the operator is at the end of the line. */
    public static final OperatorWrapOption EOL = new OperatorWrapOption("eol");

    /**
     * Creates a new <code>OperatorWrapOption</code> instance.
     *
     * @param aStrRep the string representation
     */
    private OperatorWrapOption(String aStrRep)
    {
        super(aStrRep);
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.AbstractOption */
    protected Map getStrToOpt()
    {
        return STR_TO_OPT;
    }
}
