////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2005  Oliver Burn
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

    /** {@inheritDoc} */
    public void checkIndentation()
    {
        DetailAST type = (DetailAST) getMainAst().getFirstChild();
        checkExpressionSubtree(type, getLevel(), false, false);

        checkLParen();
        DetailAST rparen = getMainAst().findFirstToken(TokenTypes.RPAREN);
        DetailAST lparen = getMainAst().findFirstToken(TokenTypes.LPAREN);

        if (rparen == null || lparen == null
            || rparen.getLineNo() == lparen.getLineNo())
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

        checkRParen();
    }

    /** {@inheritDoc} */
    protected IndentLevel getLevelImpl()
    {
        // if our expression isn't first on the line, just use the start
        // of the line
        int lineStart = getLineStart(getMainAst());
        if (lineStart != getMainAst().getColumnNo()) {
            return new IndentLevel(lineStart);
        }
        return super.getLevelImpl();
    }

    /**
     * Check the indentation of the left parenthesis.
     */
    private void checkLParen()
    {
        final DetailAST lparen =
            getMainAst().findFirstToken(TokenTypes.LPAREN);

        if (lparen == null) {
            return;
        }

        final int columnNo = expandedTabsColumnNo(lparen);

        if (getLevel().accept(columnNo) || !startsLine(lparen)) {
            return;
        }

        logError(lparen, "lparen", columnNo);
    }

    /**
     * Check the indentation of the right parenthesis.
     */
    private void checkRParen()
    {
        // the rparen can either be at the correct indentation, or on
        // the same line as the lparen
        final DetailAST rparen =
            getMainAst().findFirstToken(TokenTypes.RPAREN);
        if (rparen == null) {
            return;
        }
        final int columnNo = expandedTabsColumnNo(rparen);

        if (getLevel().accept(columnNo) || !startsLine(rparen)) {
            return;
        }

        logError(rparen, "rparen", columnNo);
    }

    /** {@inheritDoc} */
    protected boolean shouldIncreaseIndent()
    {
        return false;
    }
}
