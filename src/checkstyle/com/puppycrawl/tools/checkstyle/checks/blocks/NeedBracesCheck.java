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
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for braces around code blocks.
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
 *     <td>tokens</td>
 *     <td>blocks to check</td>
 *
 *     <td>
 *       subset of tokens
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_DO">LITERAL_DO</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_ELSE">LITERAL_ELSE</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_IF">LITERAL_IF</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_FOR">LITERAL_FOR</a>,
 *       <a href="api/com/puppycrawl/tools/checkstyle/api/TokenTypes.html#LITERAL_WHILE">LITERAL_WHILE</a>
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
 * &lt;module name=&quot;NeedBraces&quot;/&gt;
 * </source>
 *
 * <p>
 * To configure the check for <span class="code">if</span> and
 * <span class="code"> else</span> blocks:
 * </p>
 *
 * <source>
 * &lt;module name=&quot;NeedBraces&quot;&gt;
 *     &lt;property name=&quot;tokens&quot;
 *               value=&quot;LITERAL_IF, LITERAL_ELSE&quot;/&gt;
 * &lt;/module&gt;
 * </source>
 * </subsection>
 *
 * <subsection name="Package">
 * <p> com.puppycrawl.tools.checkstyle.checks.blocks </p>
 * </subsection>
 *
 * <subsection name="Parent Module">
 * <p> <a href="config.html#TreeWalker">TreeWalker</a> </p>
 * </subsection>
 *
 * @author Rick Giles
 * @version 1.0
 */
public class NeedBracesCheck
    extends Check
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_WHILE,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST slistAST = aAST.findFirstToken(TokenTypes.SLIST);
        boolean isElseIf = false;
        if ((aAST.getType() == TokenTypes.LITERAL_ELSE)
            && (aAST.findFirstToken(TokenTypes.LITERAL_IF) != null))
        {
            isElseIf = true;
        }
        if ((slistAST == null) && !isElseIf) {
            log(aAST.getLineNo(), "needBraces", aAST.getText());
        }
    }
}
