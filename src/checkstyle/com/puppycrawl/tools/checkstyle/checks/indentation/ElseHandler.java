////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2012  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Handler for else blocks.
 *
 * @author jrichard
 */
public class ElseHandler extends BlockParentHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public ElseHandler(IndentationCheck aIndentCheck,
        DetailAST aAst, ExpressionHandler aParent)
    {
        super(aIndentCheck, "else", aAst, aParent);
    }

    @Override
    protected void checkToplevelToken()
    {
        // check if else is nested with rcurly of if:
        //
        //  } else ...

        final DetailAST ifAST = getMainAst().getParent();
        if (ifAST != null) {
            final DetailAST slist = ifAST.findFirstToken(TokenTypes.SLIST);
            if (slist != null) {
                final DetailAST lcurly = slist.getLastChild();
                if ((lcurly != null)
                    && (lcurly.getLineNo() == getMainAst().getLineNo()))
                {
                    // indentation checked as part of LITERAL IF check
                    return;
                }
            }
        }
        super.checkToplevelToken();
    }

    @Override
    protected DetailAST getNonlistChild()
    {
        return getMainAst().getFirstChild();
    }
}
