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
import org.apache.commons.beanutils.ConversionException;

/**
 * Checks the placement of right curly braces.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
public class RightCurlyCheck
    extends Check
{
    /** the policy to enforce */
    private RightCurlyOption mOption = RightCurlyOption.SAME;

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {JavaTokenTypes.LITERAL_try,
                          JavaTokenTypes.LITERAL_catch,
                          JavaTokenTypes.LITERAL_else};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        // Attempt to locate the tokens to do the check
        DetailAST rcurly = null;
        DetailAST nextToken = null;
        if (aAST.getType() == JavaTokenTypes.LITERAL_else) {
            nextToken = aAST;
            rcurly = Utils.getLastSibling(
                aAST.getParent().getFirstChild().getNextSibling()
                .getNextSibling().getNextSibling().getFirstChild());
        }
        else if (aAST.getType() == JavaTokenTypes.LITERAL_catch) {
            nextToken = (DetailAST) aAST.getNextSibling();
            rcurly = Utils.getLastSibling(
                Utils.getLastSibling(aAST.getFirstChild()).getFirstChild());
        }
        else if (aAST.getType() == JavaTokenTypes.LITERAL_try) {
            nextToken = (DetailAST) aAST.getFirstChild().getNextSibling();
            rcurly = Utils.getLastSibling(aAST.getFirstChild().getFirstChild());
        }

        // If have both tokens, perform the check
        if ((rcurly != null) && (nextToken != null)) {
            if ((mOption == RightCurlyOption.SAME)
                && (rcurly.getLineNo() != nextToken.getLineNo()))
            {
                log(rcurly.getLineNo(), rcurly.getColumnNo(),
                    "line.same", "}");
            }
            else if ((mOption == RightCurlyOption.ALONE)
                       && (rcurly.getLineNo() == nextToken.getLineNo()))
            {
                log(rcurly.getLineNo(), rcurly.getColumnNo(),
                    "line.alone", "}");
            }
        }
    }

    /**
     * Describe <code>setOption</code> method here.
     *
     * @param aFromStr a <code>String</code> value
     * @throws ConversionException if unable to decode
     */
    public void setOption(String aFromStr)
    {
        mOption = RightCurlyOption.decode(aFromStr);
        if (mOption == null) {
            throw new ConversionException("unable to decode " + aFromStr);
        }
    }
}
