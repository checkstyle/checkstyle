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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;

/**
 * <p>
 * Checks for empty blocks.
 * </p>
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
 *     <td>option</td>
 *     <td>policy on block contents</td>
 *     <td><a href="property_types.html#block">block policy</a></td>
 *     <td><span class="default">stmt</span></td>
 *   </tr>
 *   <tr>
 *     <td>tokens</td>
 *     <td>blocks to check</td>
 *
 *     <td>
 *      subset of tokens <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">LITERAL_CATCH</a>,
 *      <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_DO">LITERAL_DO</a>,
 *      <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">LITERAL_ELSE</a>,
 *      <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FINALLY">LITERAL_FINALLY</a>,
 *      <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">LITERAL_IF</a>,
 *      <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FOR">LITERAL_FOR</a>,
 *      <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRY">LITERAL_TRY</a>,
 *      <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_WHILE">LITERAL_WHILE</a>,
 *      <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#INSTANCE_INIT">INSTANCE_INIT</a>
 *      <a
 *      href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#STATIC_INIT">STATIC_INIT</a>
 *     </td>
 *
 *     <td>all tokens</td>
 *   </tr>
 * </table>
 * </subsection>
 *
 * <subsection name="Examples">
 * <p> To configure the check: </p>
 *
 * <source>
 * &lt;module name=&quot;EmptyBlock&quot;/&gt;
 * </source>
 *
 * <p>
 * To configure the check for the <span class="code">text</span>
 * policy and only <span class="code"> catch</span> blocks:
 * </p>
 *
 * <source>
 * &lt;module name=&quot;EmptyBlock&quot;&gt;
 *     &lt;property name=&quot;option&quot; value=&quot;text&quot;/&gt;
 *     &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_CATCH&quot;/&gt;
 * &lt;/module&gt;
 * </source>
 * </subsection>
 *
 * <subsection name="Package">
 *   <p> com.puppycrawl.tools.checkstyle.checks.blocks </p>
 * </subsection>
 *
 * <subsection name="Parent Module">
 *   <p> <a href="config.html#TreeWalker">TreeWalker</a> </p>
 * </subsection>
 *
 * @author Lars Kühne
 */
public class EmptyBlockCheck
    extends AbstractOptionCheck
{
    /**
     * Creates a new <code>EmptyBlockCheck</code> instance.
     */
    public EmptyBlockCheck()
    {
        super(BlockOption.STMT);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            // TODO: need to handle....
            //TokenTypes.LITERAL_SWITCH,
            //TODO: does this handle TokenTypes.LITERAL_SYNCHRONIZED?
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST slistAST = aAST.findFirstToken(TokenTypes.SLIST);
        if (slistAST != null) {
            if (getAbstractOption() == BlockOption.STMT) {
                if (slistAST.getChildCount() <= 1) {
                    log(slistAST.getLineNo(),
                        slistAST.getColumnNo(),
                        "block.noStmt",
                        aAST.getText());
                }
            }
            else if (getAbstractOption() == BlockOption.TEXT) {
                if (!hasText(slistAST)) {
                    log(slistAST.getLineNo(),
                        slistAST.getColumnNo(),
                        "block.empty",
                        aAST.getText());
                }
            }
        }
    }

    /**
     * @param aSlistAST a <code>DetailAST</code> value
     * @return whether the SLIST token contains any text.
     */
    private boolean hasText(final DetailAST aSlistAST)
    {
        boolean retVal = false;

        final DetailAST rcurlyAST = aSlistAST.findFirstToken(TokenTypes.RCURLY);
        if (rcurlyAST != null) {
            final int slistLineNo = aSlistAST.getLineNo();
            final int slistColNo = aSlistAST.getColumnNo();
            final int rcurlyLineNo = rcurlyAST.getLineNo();
            final int rcurlyColNo = rcurlyAST.getColumnNo();
            final String[] lines = getLines();
            if (slistLineNo == rcurlyLineNo) {
                // Handle braces on the same line
                final String txt = lines[slistLineNo - 1]
                    .substring(slistColNo + 1, rcurlyColNo);
                if (txt.trim().length() != 0) {
                    retVal = true;
                }
            }
            else {
                // check only whitespace of first & last lines
                if ((lines[slistLineNo - 1]
                     .substring(slistColNo + 1).trim().length() != 0)
                    || (lines[rcurlyLineNo - 1]
                        .substring(0, rcurlyColNo).trim().length() != 0))
                {
                    retVal = true;
                }
                else {
                    // check if all lines are also only whitespace
                    for (int i = slistLineNo; i < (rcurlyLineNo - 1); i++) {
                        if (lines[i].trim().length() > 0) {
                            retVal = true;
                            break;
                        }
                    }
                }
            }
        }
        return retVal;
    }
}
