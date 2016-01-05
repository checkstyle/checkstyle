////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

/**
 * Handler for lambda expressions.
 *
 * @author pietern
 */
public class LambdaHandler extends AbstractExpressionHandler {
    /**
     * Construct an instance of this handler with the given indentation check,
     * abstract syntax tree, and parent handler.
     *
     * @param indentCheck the indentation check
     * @param ast the abstract syntax tree
     * @param parent the parent handler
     */
    public LambdaHandler(IndentationCheck indentCheck,
                         DetailAST ast, AbstractExpressionHandler parent) {
        super(indentCheck, "lambda", ast, parent);
    }

    @Override
    public IndentLevel getSuggestedChildIndent(AbstractExpressionHandler child) {
        return getIndent();
    }

    @Override
    protected IndentLevel getIndentImpl() {
        if (getParent() instanceof MethodCallHandler) {
            return getParent().getSuggestedChildIndent(this);
        }

        DetailAST parent = getMainAst().getParent();
        if (getParent() instanceof NewHandler) {
            parent = parent.getParent();
        }

        // Use the start of the parent's line as the reference indentation level.
        IndentLevel level = new IndentLevel(getLineStart(parent));

        // If the start of the lambda is the first element on the line;
        // assume line wrapping with respect to its parent.
        final DetailAST firstChild = getMainAst().getFirstChild();
        if (getLineStart(firstChild) == firstChild.getColumnNo()) {
            level = new IndentLevel(level, getIndentCheck().getLineWrappingIndentation());
        }

        return level;
    }

    @Override
    public void checkIndentation() {
        // If the argument list is the first element on the line
        final DetailAST firstChild = getMainAst().getFirstChild();
        if (getLineStart(firstChild) == firstChild.getColumnNo()) {
            final IndentLevel level = getIndent();
            if (!level.isAcceptable(firstChild.getColumnNo())) {
                logError(firstChild, "arguments", firstChild.getColumnNo(), level);
            }
        }

        // If the "->" is the first element on the line, assume line wrapping.
        if (getLineStart(getMainAst()) == getMainAst().getColumnNo()) {
            final IndentLevel level =
                new IndentLevel(getIndent(), getIndentCheck().getLineWrappingIndentation());
            if (!level.isAcceptable(getMainAst().getColumnNo())) {
                logError(getMainAst(), "", getMainAst().getColumnNo(), level);
            }
        }
    }
}
