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
package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Catching java.lang.Exception, java.lang.Error or java.lang.RuntimeException
 * is almost never acceptable.
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * TODO: Merge some code and make a base class with IllegalThrowsCheck
 */
public final class IllegalCatchCheck extends Check
{
    /** Illegal class names */
    private final Set mIllegalClassNames = new HashSet();

    /** Creates new instance of the check. */
    public IllegalCatchCheck()
    {
        mIllegalClassNames.add("Exception");
        mIllegalClassNames.add("Error");
        mIllegalClassNames.add("RuntimeException");
        mIllegalClassNames.add("Throwable");
        mIllegalClassNames.add("java.lang.Error");
        mIllegalClassNames.add("java.lang.Exception");
        mIllegalClassNames.add("java.lang.RuntimeException");
        mIllegalClassNames.add("java.lang.Throwable");
    }

    /** @see Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_CATCH};
    }

    /** @see Check */
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    /** @see Check */
    public void visitToken(DetailAST aDetailAST)
    {
        DetailAST paramDef =
            aDetailAST.findFirstToken(TokenTypes.PARAMETER_DEF);
        DetailAST excType = paramDef.findFirstToken(TokenTypes.TYPE);
        FullIdent ident = CheckUtils.createFullType(excType);

        if (isIllegalClassName(ident.getText())) {
            log(aDetailAST.getLineNo(),
                aDetailAST.getColumnNo(),
                "illegal.catch", ident.getText());
        }
    }

    /**
     * Checks if given exception class is illegal.
     * @param aIdent ident to check.
     * @return true if given ident is illegal.
     */
    private boolean isIllegalClassName(String aIdent)
    {
        return mIllegalClassNames.contains(aIdent);
    }
}
