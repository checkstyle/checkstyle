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
 * Checks wrapping for operators.
 *
 * @author Rick Giles
 * @version 1.0
 */
public class OperatorWrapCheck
    extends AbstractOptionCheck
{
    /**
     * Sets the operator wrap otion to new line.
     */  
    public OperatorWrapCheck()
    {
        super(OperatorWrapOption.NL);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.QUESTION,          // '?'
            TokenTypes.COLON,             // ':' (not reported for a case)
            TokenTypes.EQUAL,             // "=="
            TokenTypes.NOT_EQUAL,         // "!="
            TokenTypes.DIV,               // '/'
            TokenTypes.PLUS,              //' +' (unary plus is UNARY_PLUS)
            TokenTypes.MINUS,             // '-' (unary minus is UNARY_MINUS)
            TokenTypes.STAR,              // '*'
            TokenTypes.MOD,               // '%'
            TokenTypes.SR,                // ">>"
            TokenTypes.BSR,               // ">>>"
            TokenTypes.GE,                // ">="
            TokenTypes.GT,                // ">"
            TokenTypes.SL,                // "<<"
            TokenTypes.LE,                // "<="
            TokenTypes.LT,                // '<'
            TokenTypes.BXOR,              // '^'
            TokenTypes.BOR,               // '|'
            TokenTypes.LOR,               // "||"
            TokenTypes.BAND,              // '&'
            TokenTypes.LAND,              // "&&"
            TokenTypes.LITERAL_INSTANCEOF,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final AbstractOption wOp = getAbstractOption();

        final String text = aAST.getText();
        final int colNo = aAST.getColumnNo();
        final int lineNo = aAST.getLineNo();
        final String currentLine = getLines()[lineNo - 1];
        // TODO: Handle comments before and after operator
        // Check if rest of line is whitespace, and not just the operator
        // by itself. This last bit is to handle the operator on a line by
        // itself.
        if (wOp == OperatorWrapOption.NL
            && !text.equals(currentLine.trim())
            && (currentLine.substring(colNo + text.length())
                .trim().length() == 0))
        {
            log(lineNo, colNo, "line.new", text);
        }
        else if (wOp == OperatorWrapOption.EOL
                  && Utils.whitespaceBefore(colNo - 1, currentLine))
        {
            log(lineNo, colNo, "line.previous", text);
        }
    }
}
