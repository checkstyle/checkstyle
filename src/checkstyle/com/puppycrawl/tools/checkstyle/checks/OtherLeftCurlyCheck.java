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

import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Checks the placement of left curly braces on other constructs apart from
 * methods and types.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @author lkuehne
 * @version 1.0
 */
public class OtherLeftCurlyCheck
    extends LeftCurlyCheck
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_WHILE,
                          TokenTypes.LITERAL_TRY,
                          TokenTypes.LITERAL_CATCH,
                          TokenTypes.LITERAL_FINALLY,
                          TokenTypes.LITERAL_SYNCHRONIZED,
                          TokenTypes.LITERAL_SWITCH,
                          TokenTypes.LITERAL_DO,
                          TokenTypes.LITERAL_IF,
                          TokenTypes.LITERAL_ELSE,
                          TokenTypes.LITERAL_FOR,
                          // TODO: need to handle....
                          //TokenTypes.STATIC_INIT,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST startToken = aAST;
        final DetailAST brace;

        switch (aAST.getType()) {
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.LITERAL_SYNCHRONIZED:
            case TokenTypes.LITERAL_FOR:
                brace = aAST.getLastChild();
                break;
            case TokenTypes.LITERAL_TRY:
            case TokenTypes.LITERAL_FINALLY:
            case TokenTypes.LITERAL_DO:
                brace = (DetailAST) aAST.getFirstChild();
                break;
            case TokenTypes.LITERAL_ELSE:
                final DetailAST candidate = (DetailAST) aAST.getFirstChild();
                if (candidate.getType() == TokenTypes.SLIST) {
                    brace = candidate;
                }
                else {
                    // silently ignore
                    brace = null;
                }
                break;
            case TokenTypes.LITERAL_SWITCH:
            case TokenTypes.LITERAL_IF:
                brace = (DetailAST) aAST.getFirstChild().getNextSibling()
                    .getNextSibling().getNextSibling();
                break;
            default:
                brace = null;
        }

        if (brace != null) {
            verifyBrace(brace, startToken);
        }
    }
}
