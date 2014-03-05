////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Finds nested blocks.
 *
 * <p>
 * For example this Check flags confusing code like
 * </p>
 * <pre>
 * public void guessTheOutput()
 * {
 *     int whichIsWhich = 0;
 *     {
 *         int whichIsWhich = 2;
 *     }
 *     System.out.println("value = " + whichIsWhich);
 * }
 * </pre>
 *
 * and debugging / refactoring leftovers such as
 *
 * <pre>
 * // if (someOldCondition)
 * {
 *     System.out.println("unconditional");
 * }
 * </pre>
 *
 * <p>
 * A case in a switch statement does not implicitly form a block.
 * Thus to be able to introduce local variables that have case scope
 * it is necessary to open a nested block. This is supported, set
 * the allowInSwitchCase property to true and include all statements
 * of the case in the block.
 * </p>
 *
 * <pre>
 * switch (a)
 * {
 *     case 0:
 *         // Never OK, break outside block
 *         {
 *             x = 1;
 *         }
 *         break;
 *     case 1:
 *         // Never OK, statement outside block
 *         System.out.println("Hello");
 *         {
 *             x = 2;
 *             break;
 *         }
 *     case 1:
 *         // OK if allowInSwitchCase is true
 *         {
 *             System.out.println("Hello");
 *             x = 2;
 *             break;
 *         }
 * }
 * </pre>
 *
 * @author lkuehne
 */
public class AvoidNestedBlocksCheck extends Check
{
    /**
     * Whether nested blocks are allowed if they are the
     * only child of a switch case.
     */
    private boolean mAllowInSwitchCase;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.SLIST};
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        final DetailAST parent = aAST.getParent();
        if (parent.getType() == TokenTypes.SLIST) {
            if (mAllowInSwitchCase
                    && (parent.getParent().getType() == TokenTypes.CASE_GROUP)
                    && (parent.getNumberOfChildren() == 1))
            {
                return;
            }
            log(aAST.getLineNo(), aAST.getColumnNo(), "block.nested");
        }
    }

    /**
     * Setter for allowInSwitchCase property.
     * @param aAllowInSwitchCase whether nested blocks are allowed
     *                 if they are the only child of a switch case.
     */
    public void setAllowInSwitchCase(boolean aAllowInSwitchCase)
    {
        mAllowInSwitchCase = aAllowInSwitchCase;
    }
}
