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

/**
 * Checks the placement of right curly braces.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @author lkuehne
 * @version 1.0
 */
public class RightCurlyCheck
    extends AbstractOptionCheck
{
    /**
     * Sets the right curly otion to same.
     */  
    public RightCurlyCheck()
    {
        super(RightCurlyOption.SAME);
    }
    
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_TRY,
                          TokenTypes.LITERAL_CATCH,
                          TokenTypes.LITERAL_ELSE};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        // Attempt to locate the tokens to do the check
        DetailAST rcurly = null;
        DetailAST nextToken = null;
        if (aAST.getType() == TokenTypes.LITERAL_ELSE) {
            nextToken = aAST;
            final DetailAST thenAST = aAST.getPreviousSibling();
            rcurly = thenAST.getLastChild();
        }
        else if (aAST.getType() == TokenTypes.LITERAL_CATCH) {
            nextToken = (DetailAST) aAST.getNextSibling();
            rcurly = aAST.getLastChild().getLastChild();
        }
        else if (aAST.getType() == TokenTypes.LITERAL_TRY) {
            DetailAST firstChild = (DetailAST) aAST.getFirstChild();
            nextToken = (DetailAST) firstChild.getNextSibling();
            rcurly = firstChild.getLastChild();
        }

        // If have both tokens, perform the check
        if ((rcurly != null) && (nextToken != null)) {
            if ((getAbstractOption() == RightCurlyOption.SAME)
                && (rcurly.getLineNo() != nextToken.getLineNo()))
            {
                log(rcurly.getLineNo(), rcurly.getColumnNo(),
                    "line.same", "}");
            }
            else if ((getAbstractOption() == RightCurlyOption.ALONE)
                       && (rcurly.getLineNo() == nextToken.getLineNo()))
            {
                log(rcurly.getLineNo(), rcurly.getColumnNo(),
                    "line.alone", "}");
            }
        }
    }
}
