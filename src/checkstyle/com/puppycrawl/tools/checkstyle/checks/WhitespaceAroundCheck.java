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
import com.puppycrawl.tools.checkstyle.Java14TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Checks for whitespace around a token.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class WhitespaceAroundCheck
    extends Check
    implements Java14TokenTypes
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            RCURLY,            // '}'
            QUESTION,          // '?'
            COLON,             // ':' TODO: dont flag after "case"
            ASSIGN,            // '='
            EQUAL,             // "=="
            NOT_EQUAL,         // "!="
            DIV,               // '/'
            DIV_ASSIGN,        // "/="
            PLUS,              //' +' (unaray plus is UNARY_PLUS)
            PLUS_ASSIGN,       // "+="
            MINUS,             // '-' (unary minus is UNARY_MINUS)
            MINUS_ASSIGN,      //"-="
            STAR,              // '*'
            STAR_ASSIGN,       // "*="
            MOD,               // '%'
            MOD_ASSIGN,        // "%="
            SR,                // ">>"
            SR_ASSIGN,         // ">>="
            BSR,               // ">>>"
            BSR_ASSIGN,        // ">>>="
            GE,                // ">="
            GT,                // ">"
            SL,                // "<<"
            SL_ASSIGN,         // "<<="
            LE,                // "<="
            LT,                // '<'
            BXOR,              // '^'
            BXOR_ASSIGN,       // "^="
            BOR,               // '|'
            BOR_ASSIGN,        // "|="
            LOR,               // "||"
            BAND,              // '&'
            BAND_ASSIGN,       // "&="
            LAND,              // "&&"
            LITERAL_catch,
            LITERAL_do,
            LITERAL_else,
            LITERAL_finally,
            LITERAL_for,
            LITERAL_if,
            LITERAL_return,
            LITERAL_synchronized,
            LITERAL_try,
            LITERAL_while,
            ASSERT                // TODO: why is it not LITERAL_assert?
                                  // maybe it's a bug in the grammar?
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        // Check for RCURLY in array initializer
        if ((aAST.getType() == RCURLY)
            && (aAST.getParent().getType() == ARRAY_INIT))
        {
            return;
        }

        final String[] lines = getLines();
        final String line = lines[aAST.getLineNo() - 1];
        final int before = aAST.getColumnNo() - 1;
        final int after = aAST.getColumnNo() + aAST.getText().length();

        if ((before >= 0) && !Character.isWhitespace(line.charAt(before))) {
            log(aAST.getLineNo(), aAST.getColumnNo(),
                    "ws.notPreceeded", new Object[]{aAST.getText()});
        }

        if ((after < line.length())
            && !Character.isWhitespace(line.charAt(after))
            && !((aAST.getType() == LITERAL_return)
                 && (aAST.getFirstChild() == null)))
        {
            log(aAST.getLineNo(), aAST.getColumnNo() + aAST.getText().length(),
                    "ws.notFollowed", new Object[]{aAST.getText()});
        }
    }
}
