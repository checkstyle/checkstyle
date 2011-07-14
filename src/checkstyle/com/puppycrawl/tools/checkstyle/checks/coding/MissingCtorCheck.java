////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck;

/**
 * <p>
 * Checks that classes (except abstract one) define a ctor and don't rely
 * on the default one.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="MissingCtor"/&gt;
 * </pre>
 *
 * @author o_sukhodolsky
 */
public class MissingCtorCheck extends DescendantTokenCheck
{
    /** Creates new instance of the check. */
    public MissingCtorCheck()
    {
        setLimitedTokens(new String[] {
            TokenTypes.getTokenName(TokenTypes.CTOR_DEF),
        });
        setMinimumNumber(1);
        setMaximumDepth(2);
        setMinimumMessage("missing.ctor");
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.CLASS_DEF};
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST modifiers = aAST.findFirstToken(TokenTypes.MODIFIERS);
        if ((modifiers != null)
            && modifiers.branchContains(TokenTypes.ABSTRACT))
        {
            // should apply the check to abtract class
            return;
        }

        super.visitToken(aAST);
    }
}
