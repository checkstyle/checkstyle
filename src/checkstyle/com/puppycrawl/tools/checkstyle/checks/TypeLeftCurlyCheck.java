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
 * <p>
 * Checks the placement of left curly braces on types.
 * The policy to verify is specified using the {@link
 * LeftCurlyOption} class and defaults to {@link LeftCurlyOption#EOL}. Policies
 * {@link LeftCurlyOption#EOL} and {@link LeftCurlyOption#NLOW} take into
 * account property maxLineLength. The default value for maxLineLength is 80. 
 * </p>
 * <p>
 * An example of how to configure the check is:
 * </p>
 * <pre>
 * &lt;config name="TypeLeftCurlyCheck"/&gt;
 * </pre> 
 * <p>
 * An example of how to configure the check with policy
 * {@link LeftCurlyOption#NLOW} and maxLineLength 120 is:
 * </p>
 * <pre>
 * &lt;config name="TypeLeftCurlyCheck"&gt;
 *     &lt;property name="option" value="nlow"/&gt;
 *     &lt;property name="maxLineLength" value="120"/&gt;
 * &lt;/config&gt;
 * </pre> 
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @author lkuehne
 * @version 1.0
 */
public class TypeLeftCurlyCheck
    extends AbstractLeftCurlyCheck
{
    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.INTERFACE_DEF,
                          TokenTypes.CLASS_DEF};
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Check */
    public void visitToken(DetailAST aAST)
    {
        final DetailAST brace =
                (DetailAST) aAST.getLastChild().getFirstChild();
        // TODO: should check for modifiers
        final DetailAST startToken =
            (DetailAST) aAST.getFirstChild().getNextSibling();

        verifyBrace(brace, startToken);
    }
}
