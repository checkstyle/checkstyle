////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2010  Oliver Burn
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

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FullIdent;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Throwing java.lang.Error or java.lang.RuntimeException
 * is almost never acceptable.
 * @author Oliver Burn
 */
public final class IllegalThrowsCheck extends AbstractIllegalCheck
{
    /** Creates new instance of the check. */
    public IllegalThrowsCheck()
    {
        super(new String[] {"Error",
                            "RuntimeException", "Throwable",
                            "java.lang.Error",
                            "java.lang.RuntimeException",
                            "java.lang.Throwable",
        });
    }

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.LITERAL_THROWS};
    }

    @Override
    public int[] getRequiredTokens()
    {
        return getDefaultTokens();
    }

    @Override
    public void visitToken(DetailAST aDetailAST)
    {
        DetailAST token = aDetailAST.getFirstChild();
        while (token != null) {
            if (token.getType() != TokenTypes.COMMA) {
                final FullIdent ident = FullIdent.createFullIdent(token);
                if (isIllegalClassName(ident.getText())) {
                    log(token, "illegal.throw", ident.getText());
                }
            }

            token = token.getNextSibling();
        }
    }
}
