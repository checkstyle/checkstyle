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
import com.puppycrawl.tools.checkstyle.api.Utils;
import com.puppycrawl.tools.checkstyle.checks.AbstractOptionCheck;

/**
 * <p>Abstract class for checking the padding of parentheses. That is whether a
 * space is required after a left parenthesis and before a right parenthesis,
 * or such spaces are forbidden.
 * </p>
 * @author Oliver Burn
 * @version 1.0
 */
abstract class AbstractParenPadCheck
    extends AbstractOptionCheck<PadOption>
{
    /**
     * Sets the paren pad otion to nospace.
     */
    AbstractParenPadCheck()
    {
        super(PadOption.NOSPACE, PadOption.class);
    }

    /**
     * Process a token representing a left parentheses.
     * @param aAST the token representing a left parentheses
     */
    protected void processLeft(DetailAST aAST)
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
    protected void processRight(DetailAST aAST)
    {
        final String line = getLines()[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        if (before >= 0) {
            if ((PadOption.NOSPACE == getAbstractOption())
                && Character.isWhitespace(line.charAt(before))
                && !Utils.whitespaceBefore(before, line))
            {
                log(aAST.getLineNo(), before, "ws.preceded", ")");
            }
            else if ((PadOption.SPACE == getAbstractOption())
                && !Character.isWhitespace(line.charAt(before))
                && (line.charAt(before) != '('))
            {
                log(aAST.getLineNo(), aAST.getColumnNo(),
                    "ws.notPreceded", ")");
            }
        }
    }
}
