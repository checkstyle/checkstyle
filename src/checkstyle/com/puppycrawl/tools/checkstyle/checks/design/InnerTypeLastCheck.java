////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.design;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * <p>
 * Check nested (internal) classes/interfaces are declared at the bottom of the
 * class after all method and field declarations.
 * </p>
 *
 * @author <a href="mailto:ryly@mail.ru">Ruslan Dyachenko</a>
 */
public class InnerTypeLastCheck extends Check
{
    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
    }

    /** Meet a root class. */
    private boolean mRootClass = true;

    @Override
    public void visitToken(DetailAST aAST)
    {
        /** First root class */
        if (mRootClass) {
            mRootClass = false;
        }
        else {
            DetailAST nextSibling = aAST.getNextSibling();
            while (null != nextSibling
                && ((nextSibling.getType() != TokenTypes.CLASS_DEF)
                    || (nextSibling.getType() != TokenTypes.INTERFACE_DEF)))
            {
                if (nextSibling.getType() == TokenTypes.VARIABLE_DEF
                        || nextSibling.getType() == TokenTypes.METHOD_DEF)
                {
                    log(nextSibling.getLineNo(), nextSibling.getColumnNo(),
                        "arrangement.members.before.inner");
                }
                nextSibling = nextSibling.getNextSibling();
            }
        }
    }

    @Override
    public void leaveToken(DetailAST aAST)
    {
        /** Is this a root class */
        if (null == aAST.getParent()) {
            mRootClass = true;
        }
    }
}
