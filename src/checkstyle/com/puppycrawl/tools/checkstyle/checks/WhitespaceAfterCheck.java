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
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Checks for whitespace after a token.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @author Rick Giles
 * @version 1.0
 */
public class WhitespaceAfterCheck
    extends Check
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.COMMA,            // ','
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.COMMA,            // ','
            TokenTypes.TYPECAST,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {

        final String[] lines = getLines();
        final Object[] message;
        final DetailAST targetAST;
        if (aAST.getType() == TokenTypes.TYPECAST) {
            targetAST = aAST.findFirstToken(TokenTypes.RPAREN);
            // TODO: i18n
            message = new Object[]{"cast"};
        }
        else {
            targetAST = aAST;
            message = new Object[]{aAST.getText()};
        }
        final String line = lines[targetAST.getLineNo() - 1];
        final int after =
            targetAST.getColumnNo() + targetAST.getText().length();

        if ((after < line.length())
            && !Character.isWhitespace(line.charAt(after)))
        {
            log(targetAST.getLineNo(),
                targetAST.getColumnNo() + targetAST.getText().length(),
                "ws.notFollowed",
                message);
        }
    }
}
