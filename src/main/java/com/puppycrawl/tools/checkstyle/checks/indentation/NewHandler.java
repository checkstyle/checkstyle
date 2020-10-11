////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
 */
public class NewHandler extends AbstractExpressionHandler {

    /**
     * The Assignment operator.
     */
    private static final String ASSIGNMENT = "=";

    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck   the indentation check
     * @param ast           the abstract syntax tree
     * @param parent        the parent handler
     */
    public NewHandler(IndentationCheck indentCheck,
                      DetailAST ast,
                      AbstractExpressionHandler parent) {
        super(indentCheck, "operator new", ast, parent);
    }

    @Override
    public void checkIndentation() {
        final DetailAST firstChild = getMainAst().getFirstChild();
        final DetailAST mainAst = getMainAst();

        // if new is on the line start and it is not the part of assignment.
        if (isOnStartOfLine(mainAst)
                && !getMainAst().getParent().getParent().getText().equals(ASSIGNMENT)) {
            final int columnNo = expandedTabsColumnNo(mainAst);
            final IndentLevel level = getIndentImpl();

            if (columnNo < level.getFirstIndentLevel()) {
                logError(mainAst, "", columnNo, level);
            }
        }

        if (firstChild != null) {
            checkExpressionSubtree(firstChild, getIndent(), false, false);
        }

        final DetailAST lparen = mainAst.findFirstToken(TokenTypes.LPAREN);
        checkLeftParen(lparen);
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        final IndentLevel childIndent;
        if (child.getMainAst().getType() == TokenTypes.OBJBLOCK) {
            childIndent = new IndentLevel(getIndent(), getBasicOffset());
        }
        else {
            childIndent = new IndentLevel(getIndent(),
                    getIndentCheck().getLineWrappingIndentation());
        }
        return childIndent;
    }

    @Override
    protected IndentLevel getIndentImpl() {
        IndentLevel result;
        // if our expression isn't first on the line, just use the start
        // of the line
        if (getLineStart(getMainAst()) == getMainAst().getColumnNo()) {
            result = super.getIndentImpl();

            if (getMainAst().getParent().getParent().getText().equals(ASSIGNMENT)) {
                result = new IndentLevel(result, getIndentCheck().getLineWrappingIndentation());
            }
        }
        else {
            result = new IndentLevel(getLineStart(getMainAst()));
        }

        return result;
    }

    @Override
    protected boolean shouldIncreaseIndent() {
        return false;
    }

}
