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
 * Check that method/constructor parameters are final.
 * The user can set the token set to METHOD_DEF, CONSTRUCTOR_DEF, or both
 * (default), to control the scope of this check.
 *
 * @author lkuehne
 */
public class FinalParametersCheck extends Check
{
    /** @see Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF};
    }

    /** @see Check */
    public void visitToken(DetailAST aAST)
    {
        // don't flag interfaces
        final DetailAST container = aAST.getParent().getParent();
        if (container.getType() == TokenTypes.INTERFACE_DEF) {
            return;
        }

        // exit on fast lane if there is nothing to check here
        if (!aAST.branchContains(TokenTypes.PARAMETER_DEF)) {
            return;
        }

        // we can now be sure that there is at least one parameter

        DetailAST parameters = aAST.findFirstToken(TokenTypes.PARAMETERS);
        DetailAST child = (DetailAST) parameters.getFirstChild();
        while (child != null) {
            // childs are PARAMETER_DEF and COMMA
            if (child.getType() == TokenTypes.PARAMETER_DEF
                    && !child.branchContains(TokenTypes.FINAL))
            {
                DetailAST paramName = child.findFirstToken(TokenTypes.IDENT);
                // TODO: i18n
                log(child.getLineNo(), child.getColumnNo(),
                    "Parameter " + paramName.getText() + " should be final.");
            }
            child = (DetailAST) child.getNextSibling();
        }


    }
}
