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
 * Handler for lambda expressions.
 *
 */
public class LambdaHandler extends AbstractExpressionHandler {
    /**
     * Checks whether the lambda is correctly indented, this variable get its value from checking
     * the lambda handler's indentation, and it is being used in aligning the lambda's children.
     * A true value depicts lambda is correctly aligned without giving any errors.
     * This is updated to false where there is any Indentation error log.
     */
    private boolean isLambdaCorrectlyIndented = true;

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
        IndentLevel childIndent = getIndent();
        if (isLambdaCorrectlyIndented) {
            childIndent = IndentLevel.addAcceptable(childIndent, getLineStart(getMainAst()),
                    getLineStart(getMainAst().getFirstChild()));
        }

        return childIndent;
    }

    /**
     * {@inheritDoc}.
     *
     * @noinspection MethodWithMultipleReturnPoints
     */
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
        if (getLineStart(firstChild) == expandedTabsColumnNo(firstChild)) {
            level = new IndentLevel(level, getIndentCheck().getLineWrappingIndentation());
        }

        return level;
    }

    @Override
    public void checkIndentation() {
        // If the argument list is the first element on the line
        final DetailAST firstChild = getMainAst().getFirstChild();
        final DetailAST parent = getMainAst().getParent();

        if (parent.getType() != TokenTypes.SWITCH_RULE
                && getLineStart(firstChild) == expandedTabsColumnNo(firstChild)) {
            final int firstChildColumnNo = expandedTabsColumnNo(firstChild);
            final IndentLevel level = getIndent();

            if (isNonAcceptableIndent(firstChildColumnNo, level)) {
                isLambdaCorrectlyIndented = false;
                logError(firstChild, "arguments", firstChildColumnNo, level);
            }
        }

        // If the "->" is the first element on the line, assume line wrapping.
        final int mainAstColumnNo = expandedTabsColumnNo(getMainAst());
        if (mainAstColumnNo == getLineStart(getMainAst())) {
            final IndentLevel level =
                new IndentLevel(getIndent(), getIndentCheck().getLineWrappingIndentation());

            if (isNonAcceptableIndent(mainAstColumnNo, level)) {
                isLambdaCorrectlyIndented = false;
                logError(getMainAst(), "", mainAstColumnNo, level);
            }
        }
    }

    private boolean isNonAcceptableIndent(int astColumnNo, IndentLevel level) {
        return astColumnNo < level.getFirstIndentLevel()
            || getIndentCheck().isForceStrictCondition()
               && !level.isAcceptable(astColumnNo);
    }

}
