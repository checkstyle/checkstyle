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
import com.puppycrawl.tools.checkstyle.JavaTokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class WhitespaceAroundCheck extends Check
{
    public int[] getDefaultTokens()
    {
        // TODO: add all operators
        return new int[] {JavaTokenTypes.ASSIGN};
    }

    public void visitToken(DetailAST aAST)
    {
        final String[] lines = getLines();
        final String line = lines[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        final int after = aAST.getColumnNo() + aAST.getText().length();

        // TODO: i18n
        if ((before >= 0) && !Character.isWhitespace(line.charAt(before))) {
            log(aAST.getLineNo(), "NO LEADING SPACE for " + aAST.getText());
        }

        if ((after < line.length())
            && !Character.isWhitespace(line.charAt(after)))
        {
            log(aAST.getLineNo(), "NO TRAILING SPACE for " + aAST.getText());
        }
    }
}
