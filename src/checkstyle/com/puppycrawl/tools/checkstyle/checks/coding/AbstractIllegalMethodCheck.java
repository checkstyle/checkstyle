////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Provide support for checking for a method with a specified name and no
 * arguments.
 * @author Oliver Burn
 */
public abstract class AbstractIllegalMethodCheck extends Check
{
    /** Name of method to disallow. */
    private final String mMethodName;
    /** The error key to report with. */
    private final String mErrorKey;

    /**
     * Creates an instance.
     * @param aMethodName name of the method to disallow.
     * @param aErrorKey the error key to report with.
     */
    public AbstractIllegalMethodCheck(String aMethodName, String aErrorKey)
    {
        mMethodName = aMethodName;
        mErrorKey = aErrorKey;
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.METHOD_DEF};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST mid = aAST.findFirstToken(TokenTypes.IDENT);
        final String methodName = mid.getText();

        if (mMethodName.equals(methodName)) {

            final DetailAST params = aAST.findFirstToken(TokenTypes.PARAMETERS);
            final boolean hasEmptyParamList =
                !params.branchContains(TokenTypes.PARAMETER_DEF);

            if (hasEmptyParamList) {
                log(aAST.getLineNo(), mErrorKey);
            }
        }
    }
}
