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

package com.puppycrawl.tools.checkstyle.checks.blocks;


import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Checks for braces around code blocks.
 * </p>
 * <p> By default the check will check the following blocks:
 *  {@link TokenTypes#LITERAL_DO LITERAL_DO},
 *  {@link TokenTypes#LITERAL_ELSE LITERAL_ELSE},
 *  {@link TokenTypes#LITERAL_FOR LITERAL_FOR},
 *  {@link TokenTypes#LITERAL_IF LITERAL_IF},
 *  {@link TokenTypes#LITERAL_WHILE LITERAL_WHILE}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="NeedBraces"/&gt;
 * </pre>
 * <p> An example of how to configure the check for <code>if</code> and
 * <code>else</code> blocks is:
 * </p>
 * <pre>
 * &lt;module name="NeedBraces"&gt;
 *     &lt;property name="tokens" value="LITERAL_IF, LITERAL_ELSE"/&gt;
 * &lt;/module&gt;
 * </pre>
 *
 * @author Rick Giles
 * @version 1.0
 */
public class NeedBracesCheck extends Check
{
    @Override
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

    @Override
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
