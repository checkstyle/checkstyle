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

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Check the number of parameters a method or constructor has.
 *
 * @author Oliver Burn
 * @version 1.0
 */
public class ParameterNumberCheck
    extends Check
{
    /** the maximum number of allowed parameters */
    private int mMax = 7;

    /**
     * Sets the maximum number of allowed parameters
     * @param aMax the max allowed parameters
     */
    public void setMax(int aMax)
    {
        mMax = aMax;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.METHOD_DEF, TokenTypes.CTOR_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST params =
            Utils.findFirstToken(aAST.getFirstChild(),
                                 TokenTypes.PARAMETERS);
        final int count = Utils.countTokens(params.getFirstChild(),
                                            TokenTypes.PARAMETER_DEF);
        if (count > mMax) {
            final DetailAST name = Utils.findFirstToken(aAST.getFirstChild(),
                                                        TokenTypes.IDENT);
            log(name.getLineNo(), name.getColumnNo(),
                "maxParam", new Integer(mMax));
        }
    }
}
