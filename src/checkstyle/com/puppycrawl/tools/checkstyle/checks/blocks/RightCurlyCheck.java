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
 * Checks the placement of right curly braces (<span class="code">'}'</span>)
 * for <span class="code"> else</span>,<span class="code">try</span>, and
 * <span class="code">catch</span> tokens.
 * </p>
 *
 * <subsection name="Properties">
 * <table>
 *   <tr>
 *    <th>name</th>
 *    <th>description</th>
 *    <th>type</th>
 *    <th>default value</th>
 *   </tr>
 *   <tr>
 *     <td>option</td>
 *     <td>
 *       policy on placement of a right curly brace
 *       (<span class="code">'}'</span>)
 *     </td>
 *     <td>
 *       <a href="property_types.html#rcurly">right curly brace policy</a>
 *     </td>
 *     <td><span class="default">same</span></td>
 *   </tr>
 *   <tr>
 *     <td>tokens</td>
 *     <td>blocks to check</td>
 *
 *     <td>
 *       subset of tokens
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">LITERAL_CATCH</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">LITERAL_ELSE</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRY">LITERAL_TRY</a>
 *     </td>
 *
 *     <td>
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_CATCH">LITERAL_CATCH</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">LITERAL_ELSE</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_TRY">LITERAL_TRY</a>
 *     </td>
 *   </tr>
 * </table>
 * </subsection>
 *
 * <subsection name="Examples">
 * <p> To configure the check: </p>
 *
 * <source>
 * &lt;module name=&quot;RightCurly&quot;/&gt;
 * </source>
 *
 * <p>
 * To configure the check with policy <span class="code">alone</span> for
 * <span class="code"> else</span> tokens:
 * </p>
 *
 * <source>
 * &lt;module name=&quot;RightCurly&quot;&gt;
 *     &lt;property name=&quot;option&quot; value=&quot;alone&quot;/&gt;
 *     &lt;property name=&quot;tokens&quot; value=&quot;LITERAL_ELSE&quot;/&gt;
 * &lt;/module&gt;
 * </source>
 * </subsection>
 * <subsection name="Package">
 *   <p> com.puppycrawl.tools.checkstyle.checks.blocks </p>
 * </subsection>
 * <subsection name="Parent Module">
 *   <p> <a href="config.html#TreeWalker">TreeWalker</a> </p>
 * </subsection>
 *
 * @author Oliver Burn
 * @author lkuehne
 * @version 1.0
 */
public class RightCurlyCheck
    extends AbstractOptionCheck
{
    /**
     * Sets the right curly option to same.
     */
    public RightCurlyCheck()
    {
        super(RightCurlyOption.SAME);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_ELSE,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        // Attempt to locate the tokens to do the check
        DetailAST rcurly = null;
        DetailAST nextToken = null;
        if (aAST.getType() == TokenTypes.LITERAL_ELSE) {
            nextToken = aAST;
            final DetailAST thenAST = aAST.getPreviousSibling();
            rcurly = thenAST.getLastChild();
        }
        else if (aAST.getType() == TokenTypes.LITERAL_CATCH) {
            nextToken = (DetailAST) aAST.getNextSibling();
            rcurly = aAST.getLastChild().getLastChild();
        }
        else if (aAST.getType() == TokenTypes.LITERAL_TRY) {
            final DetailAST firstChild = (DetailAST) aAST.getFirstChild();
            nextToken = (DetailAST) firstChild.getNextSibling();
            rcurly = firstChild.getLastChild();
        }

        // handle if-then-else without curlies:
        // if (cond)
        //     return 1;
        // else
        //     return 2;
        if (rcurly == null || rcurly.getType() != TokenTypes.RCURLY) {
            return;
        }

        // If have both tokens, perform the check
        if (nextToken != null) {
            if ((getAbstractOption() == RightCurlyOption.SAME)
                && (rcurly.getLineNo() != nextToken.getLineNo()))
            {
                log(rcurly.getLineNo(), rcurly.getColumnNo(),
                    "line.same", "}");
            }
            else if ((getAbstractOption() == RightCurlyOption.ALONE)
                       && (rcurly.getLineNo() == nextToken.getLineNo()))
            {
                log(rcurly.getLineNo(), rcurly.getColumnNo(),
                    "line.alone", "}");
            }
        }
    }
}
