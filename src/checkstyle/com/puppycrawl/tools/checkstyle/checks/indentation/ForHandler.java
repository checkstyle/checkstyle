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
 * Handler for for loops.
 *
 * @author jrichard
 */
public class ForHandler extends BlockParentHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAst           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public ForHandler(IndentationCheck aIndentCheck,
        DetailAST aAst, ExpressionHandler aParent)
    {
        super(aIndentCheck, "for", aAst, aParent);
    }

    /**
     * Check the indentation of the parameters of the 'for' loop.
     */
    private void checkForParams()
    {
        final IndentLevel expected =
            new IndentLevel(getLevel(), getBasicOffset());
        final DetailAST init = getMainAst().findFirstToken(TokenTypes.FOR_INIT);

        if (init != null) {
            checkExpressionSubtree(init, expected, false, false);

            final DetailAST cond =
                getMainAst().findFirstToken(TokenTypes.FOR_CONDITION);
            checkExpressionSubtree(cond, expected, false, false);

            final DetailAST iter =
                getMainAst().findFirstToken(TokenTypes.FOR_ITERATOR);
            checkExpressionSubtree(iter, expected, false, false);
        }
        // for each
        else {
            final DetailAST forEach =
                getMainAst().findFirstToken(TokenTypes.FOR_EACH_CLAUSE);
            checkExpressionSubtree(forEach, expected, false, false);
        }
    }

    @Override
    public void checkIndentation()
    {
        checkForParams();
        super.checkIndentation();
    }

    @Override
    public IndentLevel suggestedChildLevel(ExpressionHandler aChild)
    {
        if (aChild instanceof ElseHandler) {
            return getLevel();
        }
        return super.suggestedChildLevel(aChild);
    }
}
