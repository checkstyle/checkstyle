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
 * Checks for whitespace around a token.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class WhitespaceAroundCheck
    extends Check
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.RCURLY,            // '}'
            TokenTypes.QUESTION,          // '?'
            TokenTypes.COLON,             // ':' TODO: dont flag after "case"
            TokenTypes.ASSIGN,            // '='
            TokenTypes.EQUAL,             // "=="
            TokenTypes.NOT_EQUAL,         // "!="
            TokenTypes.DIV,               // '/'
            TokenTypes.DIV_ASSIGN,        // "/="
            TokenTypes.PLUS,              //' +' (unaray plus is UNARY_PLUS)
            TokenTypes.PLUS_ASSIGN,       // "+="
            TokenTypes.MINUS,             // '-' (unary minus is UNARY_MINUS)
            TokenTypes.MINUS_ASSIGN,      //"-="
            TokenTypes.STAR,              // '*'
            TokenTypes.STAR_ASSIGN,       // "*="
            TokenTypes.MOD,               // '%'
            TokenTypes.MOD_ASSIGN,        // "%="
            TokenTypes.SR,                // ">>"
            TokenTypes.SR_ASSIGN,         // ">>="
            TokenTypes.BSR,               // ">>>"
            TokenTypes.BSR_ASSIGN,        // ">>>="
            TokenTypes.GE,                // ">="
            TokenTypes.GT,                // ">"
            TokenTypes.SL,                // "<<"
            TokenTypes.SL_ASSIGN,         // "<<="
            TokenTypes.LE,                // "<="
            TokenTypes.LT,                // '<'
            TokenTypes.BXOR,              // '^'
            TokenTypes.BXOR_ASSIGN,       // "^="
            TokenTypes.BOR,               // '|'
            TokenTypes.BOR_ASSIGN,        // "|="
            TokenTypes.LOR,               // "||"
            TokenTypes.BAND,              // '&'
            TokenTypes.BAND_ASSIGN,       // "&="
            TokenTypes.LAND,              // "&&"
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.ASSERT     // TODO: why is it not LITERAL_assert?
                                  // maybe it's a bug in the grammar?
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        // Check for RCURLY in array initializer
        if ((aAST.getType() == TokenTypes.RCURLY)
            && (aAST.getParent().getType() == TokenTypes.ARRAY_INIT))
        {
            return;
        }

        // Check for import pkg.name.*;
        if ((aAST.getType() == TokenTypes.STAR)
            && (aAST.getParent().getType() == TokenTypes.DOT))
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
            && !((aAST.getType() == TokenTypes.LITERAL_RETURN)
                 && (aAST.getFirstChild().getType() == TokenTypes.SEMI)))
        {
            log(aAST.getLineNo(), aAST.getColumnNo() + aAST.getText().length(),
                    "ws.notFollowed", new Object[]{aAST.getText()});
        }
    }
}
