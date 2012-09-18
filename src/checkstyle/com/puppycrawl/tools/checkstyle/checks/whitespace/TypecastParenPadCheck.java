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
package com.puppycrawl.tools.checkstyle.checks.whitespace;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>Checks the padding of parentheses for typecasts. That is whether a space
 * is required after a left parenthesis and before a right parenthesis, or such
 * spaces are forbidden.
 * <p>
 * </p>
 * The policy to verify is specified using the {@link PadOption} class and
 * defaults to {@link PadOption#NOSPACE}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="TypecastParenPad"/&gt;
 * </pre>
 * <p>
 * An example of how to configure the check to require spaces for the
 * parentheses of constructor, method, and super constructor invocations is:
 * </p>
 * <pre>
 * &lt;module name="TypecastParenPad"&gt;
 *     &lt;property name="option" value="space"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Oliver Burn
 * @version 1.0
 */
public class TypecastParenPadCheck extends AbstractParenPadCheck
{
    @Override
    public int[] getRequiredTokens()
    {
        return new int[] {TokenTypes.RPAREN, TokenTypes.TYPECAST};
    }

    @Override
    public int[] getDefaultTokens()
    {
        return getRequiredTokens();
    }

    @Override
    public void visitToken(DetailAST aAST)
    {
        // Strange logic in this method to guard against checking RPAREN tokens
        // that are not associated with a TYPECAST token.
        if (aAST.getType() == TokenTypes.TYPECAST) {
            processLeft(aAST);
        }
        else if ((aAST.getParent() != null)
                 && (aAST.getParent().getType() == TokenTypes.TYPECAST)
                 && (aAST.getParent().findFirstToken(TokenTypes.RPAREN)
                     == aAST))
        {
            processRight(aAST);
        }
    }
}
