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
 * @author Andrei Selkin
 */
public final class OneStatementPerLineCheck extends Check {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "multiple.statements.line";

    /**
     * hold the line-number where the last statement ended.
     */
    private int lastStatementEnd = -1;

    /**
     * The for-header usually has 3 statements on one line, but THIS IS OK.
     */
    private boolean inForHeader;

    @Override
    public int[] getDefaultTokens() {
        return getAcceptableTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[]{
            TokenTypes.SEMI, TokenTypes.FOR_INIT,
            TokenTypes.FOR_ITERATOR,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        inForHeader = false;
        lastStatementEnd = -1;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.SEMI:
                if (lastStatementEnd == ast.getLineNo()
                    && !inForHeader) {
                    //Two semicolons on the same line
                    log(ast, MSG_KEY);
                }
                else if (isMultilineStatement(ast)) {
                    final DetailAST prevSibling = ast.getPreviousSibling();
                    if (lastStatementEnd == prevSibling.getLineNo()) {
                        //Two statements on the same line
                        //and one of them is multiline
                        log(ast, MSG_KEY);
                    }
                }
                break;
            case TokenTypes.FOR_INIT:
                inForHeader = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.FOR_ITERATOR:
                inForHeader = false;
                break;
            case TokenTypes.SEMI:
                lastStatementEnd = ast.getLineNo();
                break;
            default:
                break;
        }
    }

    /**
     * Checks whether statement is multiline.
     * @param ast token for the statement.
     * @return true if one statement is distributed over two or more lines.
     */
    private static boolean isMultilineStatement(DetailAST ast) {
        boolean multiline = false;
        if (ast.getPreviousSibling() != null) {
            final DetailAST prevSibling = ast.getPreviousSibling();
            if (prevSibling.getLineNo() != ast.getLineNo()
                    && ast.getParent() != null) {
                multiline = true;
            }
        }
        return multiline;
    }
}
