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

import java.util.Set;
import java.util.HashSet;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;

/**
 * Check that reports empty if/try/catch/finally blocks.
 *
 * @author Lars Kühne
 */
public class EmptyBlockCheck extends Check
{
    private final Set mCheckFor = new HashSet();

    public EmptyBlockCheck()
    {
        mCheckFor.add("if");
        mCheckFor.add("try");
        mCheckFor.add("catch");
        mCheckFor.add("finally");
        // TODO: currently there is no way to differenciate between if and
        // else is not available as a parent token, instead if has two
        // statement children needs grammar change or workaround here to make
        // config simple
    }

    // TODO: overwrite mCheckFor based on user settings in config file

    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.SLIST};
    }

    public void visitToken(DetailAST aAST)
    {
        // defend against users that change the token set in the config file.
        if (aAST.getType() != JavaTokenTypes.SLIST) {
            return;
        }

        if (aAST.getChildCount() == 0) {
            DetailAST parent = aAST.getParent();
            String parentText = parent.getText();
            if (mCheckFor.contains(parentText)) {
                // TODO: i18n
                log(aAST.getLineNo(), "empty " + parentText + " block");
            }
        }

    }
}
