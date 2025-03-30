///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
     * @noinspectionreason MethodWithMultipleReturnPoints - indentation is complex and
     *      tightly coupled, thus making this method difficult to refactor
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
        final DetailAST mainAst = getMainAst();
        final DetailAST firstChild = mainAst.getFirstChild();

        // If the "->" has no children, it is a switch
        // rule lambda (i.e. 'case ONE -> 1;')
        final boolean isSwitchRuleLambda = firstChild == null;

        if (!isSwitchRuleLambda
            && getLineStart(firstChild) == expandedTabsColumnNo(firstChild)) {
            final int firstChildColumnNo = expandedTabsColumnNo(firstChild);
            final IndentLevel level = getIndent();

            if (isNonAcceptableIndent(firstChildColumnNo, level)) {
                isLambdaCorrectlyIndented = false;
                logError(firstChild, "arguments", firstChildColumnNo, level);
            }
        }

        // If the "->" is the first element on the line, assume line wrapping.
        final int mainAstColumnNo = expandedTabsColumnNo(mainAst);
        final boolean isLineWrappedLambda = mainAstColumnNo == getLineStart(mainAst);
        if (isLineWrappedLambda) {
            checkLineWrappedLambda(isSwitchRuleLambda, mainAstColumnNo);
        }

        final DetailAST nextSibling = mainAst.getNextSibling();

        if (isSwitchRuleLambda
                && nextSibling.getType() == TokenTypes.EXPR
                && !TokenUtil.areOnSameLine(mainAst, nextSibling)) {
            // Likely a single-statement switch rule lambda without curly braces, e.g.:
            // case ONE ->
            //      1;
            checkSingleStatementSwitchRuleIndentation(isLineWrappedLambda);
        }
    }

    /**
     * Checks that given indent is acceptable or not.
     *
     * @param astColumnNo indent value to check
     * @param level indent level
     * @return true if indent is not acceptable
     */
    private boolean isNonAcceptableIndent(int astColumnNo, IndentLevel level) {
        return astColumnNo < level.getFirstIndentLevel()
            || getIndentCheck().isForceStrictCondition()
               && !level.isAcceptable(astColumnNo);
    }

    /**
     * This method checks a line wrapped lambda, whether it is a lambda
     * expression or switch rule lambda.
     *
     * @param isSwitchRuleLambda if mainAst is a switch rule lambda
     * @param mainAstColumnNo the column number of the lambda we are checking
     */
    private void checkLineWrappedLambda(final boolean isSwitchRuleLambda,
                                        final int mainAstColumnNo) {
        final IndentLevel level;
        final DetailAST mainAst = getMainAst();

        if (isSwitchRuleLambda) {
            // We check the indentation of the case literal or default literal
            // on the previous line and use that to determine the correct
            // indentation for the line wrapped "->"
            final DetailAST previousSibling = mainAst.getPreviousSibling();
            final int previousLineStart = getLineStart(previousSibling);

            level = new IndentLevel(new IndentLevel(previousLineStart),
                    getIndentCheck().getLineWrappingIndentation());
        }
        else {
            level = new IndentLevel(getIndent(),
                getIndentCheck().getLineWrappingIndentation());
        }

        if (isNonAcceptableIndent(mainAstColumnNo, level)) {
            isLambdaCorrectlyIndented = false;
            logError(mainAst, "", mainAstColumnNo, level);
        }
    }

    /**
     * Checks the indentation of statements inside a single-statement switch rule
     * when the statement is not on the same line as the lambda operator ({@code ->}).
     * This applies to single-statement switch rules without curly braces {@code {}}.
     * Example:
     * <pre>
     * case ONE {@code ->}
     *     1;
     * </pre>
     *
     * @param isLambdaFirstInLine if {@code ->} is the first element on the line
     */
    private void checkSingleStatementSwitchRuleIndentation(boolean isLambdaFirstInLine) {
        final DetailAST mainAst = getMainAst();
        IndentLevel level = getParent().getSuggestedChildIndent(this);

        if (isLambdaFirstInLine) {
            // If the lambda operator (`->`) is at the start of the line, assume line wrapping
            // and add additional indentation for the statement in the next line.
            level = new IndentLevel(level, getIndentCheck().getLineWrappingIndentation());
        }

        // The first line should not match if the switch rule statement starts on the same line
        // as "->" but continues onto the next lines as part of a single logical expression.
        final DetailAST nextSibling = mainAst.getNextSibling();
        final boolean firstLineMatches = getFirstLine(nextSibling) != mainAst.getLineNo();
        checkExpressionSubtree(nextSibling, level, firstLineMatches, false);
    }
}
