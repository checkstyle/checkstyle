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
import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Utils;

/**
 * Checks the placement of left curly braces on other constructs apart from
 * methods and types.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class OtherLeftCurlyCheck
    extends LeftCurlyCheck
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.LITERAL_while,
                          JavaTokenTypes.LITERAL_try,
                          JavaTokenTypes.LITERAL_catch,
                          JavaTokenTypes.LITERAL_finally,
                          JavaTokenTypes.LITERAL_synchronized,
                          JavaTokenTypes.LITERAL_switch,
                          JavaTokenTypes.LITERAL_do,
                          JavaTokenTypes.LITERAL_if,
                          JavaTokenTypes.LITERAL_else,
                          JavaTokenTypes.LITERAL_for,
                          // TODO: need to handle....
                          //JavaTokenTypes.STATIC_INIT,
        };
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST startToken = aAST;
        final DetailAST brace;

        switch (aAST.getType()) {
            case JavaTokenTypes.LITERAL_while:
            case JavaTokenTypes.LITERAL_catch:
            case JavaTokenTypes.LITERAL_synchronized:
            case JavaTokenTypes.LITERAL_for:
                brace = Utils.getLastSibling(aAST.getFirstChild());
                break;
            case JavaTokenTypes.LITERAL_try:
            case JavaTokenTypes.LITERAL_finally:
            case JavaTokenTypes.LITERAL_do:
                brace = (DetailAST) aAST.getFirstChild();
                break;
            case JavaTokenTypes.LITERAL_else:
                final DetailAST candidate = (DetailAST) aAST.getFirstChild();
                if (candidate.getType() == JavaTokenTypes.SLIST) {
                    brace = candidate;
                }
                else {
                    // silently ignore
                    brace = null;
                }
                break;
            case JavaTokenTypes.LITERAL_switch:
            case JavaTokenTypes.LITERAL_if:
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
