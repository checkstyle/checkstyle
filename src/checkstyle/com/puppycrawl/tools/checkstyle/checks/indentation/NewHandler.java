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
 * Handler for operator new.
 *
 * @author o_sukhodolsky
 */
public class NewHandler extends ExpressionHandler
{
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param aIndentCheck   the indentation check
     * @param aAST           the abstract syntax tree
     * @param aParent        the parent handler
     */
    public NewHandler(IndentationCheck aIndentCheck,
                      DetailAST aAST,
                      ExpressionHandler aParent)
    {
        super(aIndentCheck, "operator new", aAST, aParent);
    }

    @Override
    public void checkIndentation()
    {
        final DetailAST type = getMainAst().getFirstChild();
        checkExpressionSubtree(type, getLevel(), false, false);

        final DetailAST lparen = getMainAst().findFirstToken(TokenTypes.LPAREN);
        final DetailAST rparen = getMainAst().findFirstToken(TokenTypes.RPAREN);
        checkLParen(lparen);

        if ((rparen == null) || (lparen == null)
            || (rparen.getLineNo() == lparen.getLineNo()))
        {
            return;
        }

        // if this method name is on the same line as a containing
        // method, don't indent, this allows expressions like:
        //    method("my str" + method2(
        //        "my str2"));
        // as well as
        //    method("my str" +
        //        method2(
        //            "my str2"));
        //

        checkExpressionSubtree(
            getMainAst().findFirstToken(TokenTypes.ELIST),
            new IndentLevel(getLevel(), getBasicOffset()),
            false, true);

        checkRParen(lparen, rparen);
    }

    @Override
    protected IndentLevel getLevelImpl()
    {
        // if our expression isn't first on the line, just use the start
        // of the line
        if (getLineStart(getMainAst()) != getMainAst().getColumnNo()) {
            return new IndentLevel(getLineStart(getMainAst()));
        }
        return super.getLevelImpl();
    }

    @Override
    protected boolean shouldIncreaseIndent()
    {
        return false;
    }
}
