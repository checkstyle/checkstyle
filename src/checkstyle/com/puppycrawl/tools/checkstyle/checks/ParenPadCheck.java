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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Utils;
import org.apache.commons.beanutils.ConversionException;

/**
 * Checks the padding of parenthesis.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class ParenPadCheck
    extends Check
{
    /** the policy to enforce */
    private PadOption mOption = PadOption.NOSPACE;

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
     * Set the option to enforce.
     * @param aOption string to decode option from
     * @throws ConversionException if unable to decode
     */
    public void setOption(String aOption)
    {
        mOption = PadOption.decode(aOption);
        if (mOption == null) {
            throw new ConversionException("unable to parse " + aOption);
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
            if ((PadOption.NOSPACE == mOption)
                && (Character.isWhitespace(line.charAt(after))))
            {
                log(aAST.getLineNo(), after, "ws.followed", "(");
            }
            else if ((PadOption.SPACE == mOption)
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
            if ((PadOption.NOSPACE == mOption)
                && Character.isWhitespace(line.charAt(before))
                && !Utils.whitespaceBefore(before, line))
            {
                log(aAST.getLineNo(), before, "ws.preceeded", ")");
            }
            else if ((PadOption.SPACE == mOption)
                && !Character.isWhitespace(line.charAt(before))
                && (line.charAt(before) != '('))
            {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                    "ws.notPreceeded", ")");
            }
        }
    }
}
