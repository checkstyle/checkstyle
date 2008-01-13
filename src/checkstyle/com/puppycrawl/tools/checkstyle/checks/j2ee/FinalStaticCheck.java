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
package com.puppycrawl.tools.checkstyle.checks.j2ee;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks that all static fields are declared final.
 * That ensures consistent runtime semantics so that EJB containers have
 * the flexibility to distribute instances across multiple JVMs.
 * http://www.javaworld.com/javaworld/jw-08-2000/jw-0825-ejbrestrict.html
 * @author Rick Giles
 */
public class FinalStaticCheck
    extends Check
{
    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.VARIABLE_DEF};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void visitToken(DetailAST aAST)
    {
        if (Utils.isInEJB(aAST)
            && Utils.isStatic(aAST)
            && !Utils.isFinal(aAST))
        {
            final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
            log(
                nameAST.getLineNo(),
                nameAST.getColumnNo(),
                "nonfinalstatic.bean", nameAST.getText());
        }
    }
}
