///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.indentation;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for operator new.
 */
public class NewHandler extends AbstractExpressionHandler {

    /** The AST which is handled by this handler. */
    private final DetailAST mainAst;

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
        super(indentCheck, "new", ast, parent);
        mainAst = ast;
    }

    @Override
    public void checkIndentation() {
        // if new is on the line start and it is not the part of assignment.
        if (isOnStartOfLine(mainAst)) {
            final int columnNo = expandedTabsColumnNo(mainAst);
            final IndentLevel level = getIndentImpl();

            final boolean forceStrictCondition = getIndentCheck().isForceStrictCondition();
            if (forceStrictCondition && !level.isAcceptable(columnNo)
                || !forceStrictCondition && level.isGreaterThan(columnNo)) {
                logError(mainAst, "", columnNo, level);
            }
        }

        final DetailAST firstChild = mainAst.getFirstChild();
        if (firstChild != null) {
            checkExpressionSubtree(firstChild, getIndent(), false, false);
        }

        final DetailAST lparen = mainAst.findFirstToken(TokenTypes.LPAREN);
        checkLeftParen(lparen);
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        final int offset;
        if (TokenUtil.isOfType(child.getMainAst(), TokenTypes.OBJBLOCK)) {
            offset = getBasicOffset();
        }
        else {
            offset = getLineWrappingIndent();
        }
        return new IndentLevel(getIndent(), offset);
    }

    @Override
    protected IndentLevel getIndentImpl() {
        IndentLevel result;
        // if our expression isn't first on the line, just use the start
        // of the line
        if (getLineStart(mainAst) == mainAst.getColumnNo()) {
            result = super.getIndentImpl();

            final boolean isLineWrappedNew = TokenUtil.isOfType(mainAst.getParent().getParent(),
                                        TokenTypes.ASSIGN, TokenTypes.LITERAL_RETURN);

            if (isLineWrappedNew || doesChainedMethodNeedsLineWrapping()) {
                result = new IndentLevel(result, getLineWrappingIndent());
            }
        }
        else {
            result = new IndentLevel(getLineStart(mainAst));
        }

        return result;
    }

    /**
     * A shortcut for {@code IndentationCheck} property.
     *
     * @return value of lineWrappingIndentation property
     *         of {@code IndentationCheck}
     */
    private int getLineWrappingIndent() {
        return getIndentCheck().getLineWrappingIndentation();
    }

    @Override
    protected boolean shouldIncreaseIndent() {
        return false;
    }

    /**
     * The function checks if the new keyword is a child of chained method calls,
     * it checks if the new is directly followed by equal operator or return operator.
     *
     * @return true if the new it is chained method calls and new keyword is directly followed
     *         by assign or return
     */
    private boolean doesChainedMethodNeedsLineWrapping() {
        DetailAST ast = mainAst.getParent();

        while (TokenUtil.isOfType(ast, TokenTypes.DOT, TokenTypes.METHOD_CALL, TokenTypes.EXPR)) {
            ast = ast.getParent();
        }

        return TokenUtil.isOfType(ast, TokenTypes.ASSIGN, TokenTypes.LITERAL_RETURN);
    }

}
