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
        setIllegalClassNames(new String[] {"Exception", "Error",
                                           "RuntimeException", "Throwable",
                                           "java.lang.Error",
                                           "java.lang.Exception",
                                           "java.lang.RuntimeException",
                                           "java.lang.Throwable",
        });
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
        final DetailAST paramDef =
            aDetailAST.findFirstToken(TokenTypes.PARAMETER_DEF);
        final DetailAST excType = paramDef.findFirstToken(TokenTypes.TYPE);
        final FullIdent ident = CheckUtils.createFullType(excType);

        if (isIllegalClassName(ident.getText())) {
            log(aDetailAST, "illegal.catch", ident.getText());
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

    /**
     * Set the list of illegal exception classes.
     * @param aClassNames array of illegal exception classes
     */
    public void setIllegalClassNames(String[] aClassNames)
    {
        mIllegalClassNames.clear();
        for (int i = 0; i < aClassNames.length; i++) {
            final String name = aClassNames[i];
            mIllegalClassNames.add(name);
            final int lastDot = name.lastIndexOf(".");
            if (lastDot > 0 && lastDot < (name.length() - 1)) {
                final String shortName =
                    name.substring(name.lastIndexOf(".") + 1);
                mIllegalClassNames.add(shortName);
            }
        }
    }
}
