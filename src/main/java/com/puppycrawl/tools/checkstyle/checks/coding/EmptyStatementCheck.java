////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * <p>
 * Detects empty statements (standalone ';').
 * Empty statements often introduce bugs
 * that are hard to spot, such as in
 * </p>
 * <pre>
 * if (someCondition);
 *   doConditionalStuff();
 * doUnconditionalStuff();
 * </pre>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="EmptyStatement"/&gt;
 * </pre>
 * @author Rick Giles
 * @version 1.0
 */
public class EmptyStatementCheck extends Check
{
    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.EMPTY_STAT};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        log(aAST.getLineNo(), aAST.getColumnNo(), "empty.statement");
    }
}
