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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * <p>Checks the padding of parentheses; that is whether a space is required
 * after a left parenthesis and before a right parenthesis, or such spaces are
 * forbidden.
 * The policy to verify is specified using the {@link PadOption} class and
 * defaults to {@link PadOption#NOSPACE}.
 * </p>
 * <p> By default the check will check parentheses that occur with the following
 * tokens:
 *  {@link TokenTypes#CTOR_CALL CTOR_CALL},
 *  {@link TokenTypes#LPAREN LPAREN},
 *  {@link TokenTypes#METHOD_CALL METHOD_CALL},
 *  {@link TokenTypes#RPAREN RPAREN},
 *  {@link TokenTypes#SUPER_CTOR_CALL SUPER_CTOR_CALL},
 *  {@link TokenTypes#TYPECAST TYPECAST}.
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;module name="ParenPad"/&gt;
 * </pre> 
 * <p>
 * An example of how to configure the check to require spaces for the
 * parentheses of constructor, method, and super constructor invocations is:
 * </p>
 * <pre>
 * &lt;module name="ParenPad"&gt;
 *     &lt;property name="tokens"
 *               value="CTOR_CALL, METHOD_CALL, SUPER_CTOR_CALL"/&gt;
 *     &lt;property name="option" value="space"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class ParenPadCheck
    extends AbstractOptionCheck
{
    /**
     * Sets the paren pad otion to nospace.
     */  
    public ParenPadCheck()
    {
        super(PadOption.NOSPACE);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.RPAREN,
                          TokenTypes.LPAREN,
                          TokenTypes.CTOR_CALL,
                          TokenTypes.SUPER_CTOR_CALL,
                          TokenTypes.TYPECAST, // TODO: treat this?
                          TokenTypes.METHOD_CALL,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == TokenTypes.RPAREN) {
            processRight(aAST);
        }
        else {
            processLeft(aAST);
        }
    }

    /**
     * Process a token representing a left parentheses.
     * @param aAST the token representing a left parentheses
     */
    private void processLeft(DetailAST aAST)
    {
        final String line = getLines()[aAST.getLineNo() - 1];
        final int after = aAST.getColumnNo() + 1;
        if (after < line.length()) {
            if ((PadOption.NOSPACE == getAbstractOption())
                && (Character.isWhitespace(line.charAt(after))))
            {
                log(aAST.getLineNo(), after, "ws.followed", "(");
            }
            else if ((PadOption.SPACE == getAbstractOption())
                     && !Character.isWhitespace(line.charAt(after))
                     && (line.charAt(after) != ')'))
            {
                log(aAST.getLineNo(), after, "ws.notFollowed", "(");
            }
        }
    }

    /**
     * Process a token representing a right parentheses.
     * @param aAST the token representing a right parentheses
     */
    private void processRight(DetailAST aAST)
    {
        final String line = getLines()[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        if (before >= 0) {
            if ((PadOption.NOSPACE == getAbstractOption())
                && Character.isWhitespace(line.charAt(before))
                && !Utils.whitespaceBefore(before, line))
            {
                log(aAST.getLineNo(), before, "ws.preceeded", ")");
            }
            else if ((PadOption.SPACE == getAbstractOption())
                && !Character.isWhitespace(line.charAt(before))
                && (line.charAt(before) != '('))
            {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                    "ws.notPreceeded", ")");
            }
        }
    }
}
