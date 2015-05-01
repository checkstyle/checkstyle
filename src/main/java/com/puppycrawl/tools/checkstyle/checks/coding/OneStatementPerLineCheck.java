////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Restricts the number of statements per line to one.
 * @author Alexander Jesse
 * @author Oliver Burn
 */
public final class OneStatementPerLineCheck extends Check
{

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "multiple.statements.line";

    /** hold the line-number where the last statement ended. */
    private int lastStatementEnd = -1;
    /** tracks the depth of EXPR tokens. */
    private int exprDepth;

    /**
     * The for-header usually has 3 statements on one line, but THIS IS OK.
     */
    private boolean inForHeader;

    @Override
    public int[] getDefaultTokens()
    {
        return new int[] {
            TokenTypes.EXPR, TokenTypes.SEMI, TokenTypes.FOR_INIT,
            TokenTypes.FOR_ITERATOR,
        };
    }

    @Override
    public int[] getAcceptableTokens()
    {
        return new int[] {
            TokenTypes.EXPR, TokenTypes.SEMI, TokenTypes.FOR_INIT,
            TokenTypes.FOR_ITERATOR,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST)
    {
        exprDepth = 0;
        inForHeader = false;
        lastStatementEnd = -1;
    }

    @Override
    public void visitToken(DetailAST ast)
    {
        switch (ast.getType()) {
            case TokenTypes.EXPR:
                visitExpr(ast);
                break;
            case TokenTypes.SEMI:
                visitSemi(ast);
                break;
            case TokenTypes.FOR_INIT:
                inForHeader = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void leaveToken(DetailAST ast)
    {
        switch (ast.getType()) {
            case TokenTypes.FOR_ITERATOR:
                inForHeader = false;
                break;
            case TokenTypes.EXPR:
                exprDepth--;
                break;
            default:
                break;
        }
    }

    /**
     * Mark the state-change for the statement (entering) and remember the
     * first line of the last statement. If the first line of the new
     * statement is the same as the last line of the last statement and we are
     * not within a for-statement, then the rule is violated.
     * @param ast token for the {@link TokenTypes#EXPR}.
     */
    private void visitExpr(DetailAST ast)
    {
        exprDepth++;
        if (exprDepth == 1
                && !inForHeader
                && lastStatementEnd == ast.getLineNo())
        {
            log(ast, MSG_KEY);
        }
    }

    /**
     * Mark the state-change for the statement (leaving) and remember the last
     * line of the last statement.
     * @param ast for the {@link TokenTypes#SEMI}.
     */
    private void visitSemi(DetailAST ast)
    {
        if (exprDepth == 0) {
            lastStatementEnd = ast.getLineNo();
        }
    }
}
