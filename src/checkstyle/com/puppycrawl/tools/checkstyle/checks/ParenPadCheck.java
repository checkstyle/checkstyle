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

import com.puppycrawl.tools.checkstyle.JavaTokenTypes;
import com.puppycrawl.tools.checkstyle.PadOption;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.Utils;
import org.apache.commons.beanutils.ConversionException;

public class ParenPadCheck
    extends Check
{
    private PadOption mOption = PadOption.NOSPACE;

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.RPAREN,
                          JavaTokenTypes.LPAREN,
                          JavaTokenTypes.CTOR_CALL,
                          JavaTokenTypes.SUPER_CTOR_CALL,
                          JavaTokenTypes.TYPECAST, // TODO: treat this?
                          JavaTokenTypes.METHOD_CALL,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        if (aAST.getType() == JavaTokenTypes.RPAREN) {
            processRight(aAST);
        }
        else {
            processLeft(aAST);
        }
    }

    public void setOption(String aOption)
    {
        mOption = PadOption.decode(aOption);
        if (mOption == null) {
            throw new ConversionException("unable to parse " + aOption);
        }
    }

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
