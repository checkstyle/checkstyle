////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Detects inline conditionals.
 *
 * An example inline conditional is this:
 * <pre>
 * String a = getParameter("a");
 * String b = (a==null || a.length<1) ? null : a.substring(1);
 * </pre>
 *
 * Rationale: Some developers find inline conditionals hard to read,
 * so their company's coding standards forbids them.
 *
 * @author lkuehne
 * @version $Revision: 1.1 $
 */
public class AvoidInlineConditionalsCheck extends Check
{
    /** avoids multiple instantiations of equivalten arrays */
    private static final int[] DEFAULT_TOKENS =
        new int[]{TokenTypes.QUESTION};

    /** @see Check */
    public int[] getDefaultTokens()
    {
        return DEFAULT_TOKENS;
    }

    /** @see Check */
    public int[] getRequiredTokens()
    {
        return DEFAULT_TOKENS;
    }

    /** @see Check */
    public void visitToken(DetailAST aAST)
    {
        // the only place a QUESTION token can occur is in inline conditionals
        // so no need to do any further tricks here - pretty trivial Check!

        log(aAST.getLineNo(), aAST.getColumnNo(),
            "Avoid inline conditionals.");
    }
}
