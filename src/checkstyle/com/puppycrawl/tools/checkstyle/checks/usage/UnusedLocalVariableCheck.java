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
package com.puppycrawl.tools.checkstyle.checks.usage;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.ScopeUtils;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;


/**
 * <p>Checks that a local variable is read.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="usage.UnusedLocalVariable"/&gt;
 * </pre>
 *
 * @author Rick Giles
 */
public class UnusedLocalVariableCheck extends AbstractUsageCheck
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.VARIABLE_DEF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.usage.AbstractUsageCheck */
    public String getErrorKey()
    {
        return "unused.local";
    }

    /** @see com.puppycrawl.tools.checkstyle.checks.usage.AbstractUsageCheck */
    public boolean mustCheckReferenceCount(DetailAST aAST)
    {
        return ScopeUtils.isLocalVariableDef(aAST);
    }
}
