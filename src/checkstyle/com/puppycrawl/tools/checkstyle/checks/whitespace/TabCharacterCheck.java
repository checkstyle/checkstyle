////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2008  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * <p>
 * Reports tab characters ('\t') in the source code.
 * </p>
 *
 * <p>
 * Rationale:
 * <ul>
 * <li>Developers should not need to configure the tab width of their
 * text editors in order to be able to read source code.</li>
 * <li>From the Apache jakarta coding standards:
 * In a distributed development environment, when the
 * cvs commit messages get sent to a mailing list, they are almost
 * impossible to read if you use tabs.</li>
 * </ul>
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="TabCharacter"/&gt;
 * </pre>
 * @author Lars Kühne
 */
public class TabCharacterCheck
        extends Check
{
    @Override
    public int[] getDefaultTokens()
    {
        return new int[0];
    }

    @Override
    public void beginTree(DetailAST aRootAST)
    {
        final String[] lines = getLines();
        for (int i = 0; i < lines.length; i++) {
            final int tabPosition = lines[i].indexOf('\t');
            if (tabPosition != -1) {
                log(i + 1, tabPosition, "containsTab");
            }
        }
    }
}
