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
package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * <p>
 * Finds nested blocks. Rationale, nested blocks are often leftovers from the
 * debugging process, they confuse the reader.
 * </p>
 *
 * <p>
 * For example this Check finds the obsolete braces in
 * </p>
 *
 * <source>
 * public void guessTheOutput()
 * {
 *     int whichIsWich = 0;
 *     {
 *         int whichIsWhich = 2;
 *     }
 *     System.out.println("value = " + whichIsWhich);
 * }
 * </source>
 *
 * <p> and debugging / refactoring leftovers such as </p>
 *
 * <source>
 * // if (conditionThatIsNotUsedAnyLonger)
 * {
 *     System.out.println("unconditional");
 * }
 * </source>
 *
 * <p>
 * A case in a switch statement does not implicitly form a block.  Thus to
 * be able to introduce local variables that have case scope it is
 * necessary to open a nested block. This is supported, set the
 * allowInSwitchCase property to true and include all statements of the
 * case in the block.
 * </p>
 *
 * <source>
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
 * </source>
 *
 * <subsection name="Properties">
 * <table>
 *   <tr>
 *     <th>name</th>
 *     <th>description</th>
 *     <th>type</th>
 *     <th>default value</th>
 *   </tr>
 *   <tr>
 *     <td>allowInSwitchCase</td>
 *     <td>Allow nested blocks in case statements</td>
 *     <td><a href="property_types.html#boolean">boolean</a></td>
 *     <td><span class="default">false</span></td>
 *   </tr>
 * </table>
 * </subsection>
 *
 * <subsection name="Examples">
 * <p> To configure the check: </p>
 * <source>
 * &lt;module name=&quot;AvoidNestedBlocks&quot;/&gt;
 * </source>
 * </subsection>
 *
 * <subsection name="Package">
 * <p> com.puppycrawl.tools.checkstyle.checks.blocks </p>
 * </subsection>
 *
 * <subsection name="Parent Module">
 * <p> <a href="config.html#treewalker">TreeWalker</a> </p>
 * </subsection>
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

    /** @see Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.SLIST};
    }

    /** @see Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST parent = aAST.getParent();
        if (parent.getType() == TokenTypes.SLIST) {
            if (mAllowInSwitchCase
                    && parent.getParent().getType() == TokenTypes.CASE_GROUP
                    && parent.getNumberOfChildren() == 1)
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
