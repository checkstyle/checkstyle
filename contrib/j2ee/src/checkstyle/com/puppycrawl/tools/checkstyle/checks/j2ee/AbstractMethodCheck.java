////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
 * Abstract class for method checks
 * @author Rick Giles
 */
public class AbstractMethodCheck
    extends Check
{
    /**
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.api.Check
     */
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    /**
     * Checks method requirements:
     * <ul>
     * <li>The access control modifier must be <code>public</code>.</li>
     * <li>The method modifier cannot be <code>final</code>.
     * </ul>
     * @param aAST METHOD_DEF node for method definition to check.
     * @param aAllowFinal if false, the method cannot be final.
     */
    protected void checkMethod(DetailAST aAST, boolean aAllowFinal)
    {
        final DetailAST nameAST = aAST.findFirstToken(TokenTypes.IDENT);
        final String nameMessage = "Method " + nameAST.getText();
        if (!Utils.isPublic(aAST)) {
            log(nameAST.getLineNo(), nameAST.getColumnNo(),
                "nonpublic.bean", nameMessage);
        }
        if (!aAllowFinal && Utils.isFinal(aAST)) {
            log(nameAST.getLineNo(), nameAST.getColumnNo(),
                "illegalmodifier.bean",
                new Object[] {nameMessage, "final"});
        }
        if (Utils.isStatic(aAST)) {
            log(nameAST.getLineNo(), nameAST.getColumnNo(),
                "illegalmodifier.bean",
                new Object[] {nameMessage, "static"});
        }
    }
}
