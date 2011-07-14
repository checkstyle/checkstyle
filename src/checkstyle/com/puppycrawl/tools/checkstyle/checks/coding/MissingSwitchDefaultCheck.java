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

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck;

/**
 * <p>
 * Checks that switch statement has &quot;default&quot; clause.
 * </p>
 * <p>
 * Rationale: It's usually a good idea to introduce a
 * default case in every switch statement. Even if
 * the developer is sure that all currently possible
 * cases are covered, this should be expressed in the
 * default branch, e.g. by using an assertion. This way
 * the code is protected aginst later changes, e.g.
 * introduction of new types in an enumeration type.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="MissingSwitchDefault"/&gt;
 * </pre>
 * @author o_sukhodolsky
 */
public class MissingSwitchDefaultCheck extends DescendantTokenCheck
{
    /** Creates new instance of the check. */
    public MissingSwitchDefaultCheck()
    {
        setLimitedTokens(new String[] {
            TokenTypes.getTokenName(TokenTypes.LITERAL_DEFAULT),
        });
        setMinimumNumber(1);
        setMaximumDepth(2);
        setMinimumMessage("missing.switch.default");
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[]{TokenTypes.LITERAL_SWITCH};
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return getDefaultTokens();
    }
}
