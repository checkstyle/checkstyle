////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2007  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.Iterator;
import java.util.Set;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.GenericIllegalRegexpCheck;

/**
 * <p>
 * Checks for illegal token text.
 * </p>
 * <p> An example of how to configure the check to forbid String literals
 * containing <code>"a href"</code> is:
 * </p>
 * <pre>
 * &lt;module name="IllegalTokenText"&gt;
 *     &lt;property name="tokens" value="STRING_LITERAL"/&gt;
 *     &lt;property name="format" value="a href"/&gt;
 * &lt;/module&gt;
 * </pre>
 * <p> An example of how to configure the check to forbid leading zeros in an
 * integer literal, other than zero and a hex literal is:
 * </p>
 * <pre>
 * &lt;module name="IllegalTokenText"&gt;
 *     &lt;property name="tokens" value="NUM_INT,NUM_LONG"/&gt;
 *     &lt;property name="format" value="^0[^lx]"/&gt;
 *     &lt;property name="ignoreCase" value="true"/&gt;
 * &lt;/module&gt;
 * </pre>
 * @author Rick Giles
 */
public class IllegalTokenTextCheck
    extends GenericIllegalRegexpCheck
{
    /**  {@inheritDoc} */
    public void beginTree(DetailAST aRootAST)
    {
    }

    /** {@inheritDoc} */
    public int[] getAcceptableTokens()
    {
        // Any tokens set by property 'tokens' are acceptable
        final Set tokenNames = getTokenNames();
        final int[] result = new int[tokenNames.size()];
        int i = 0;
        final Iterator it = tokenNames.iterator();
        while (it.hasNext()) {
            final String name = (String) it.next();
            result[i] = TokenTypes.getTokenId(name);
            i++;
        }
        return result;
    }
    /**
     * {@inheritDoc}
     */
    public void visitToken(DetailAST aAST)
    {
        final String text = aAST.getText();
        if (getRegexp().matcher(text).find()) {
            String message = getMessage();
            if ("".equals(message)) {
                message = "illegal.token.text";
            }
            log(
                aAST.getLineNo(),
                aAST.getColumnNo(),
                message,
                getFormat());
        }
    }
}
