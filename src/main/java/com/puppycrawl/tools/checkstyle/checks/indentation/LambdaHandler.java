///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

import javax.annotation.Nullable;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Handler for lambda expressions.
 *
 */
public class LambdaHandler extends AbstractExpressionHandler {
    /**
     * Indentation error message key for lambda arguments.
     */
    private static final String ARGUMENTS_KEY = "arguments";

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
            // If the lambda is correctly indented, include its line start as acceptable to
            // avoid false positives. When "forceStrictCondition" is off, we allow indents
            // larger than expected (e.g., 12 instead of 6 or 8). These larger indents are
            // accepted but not recorded, so child indent suggestions may be inaccurate.
            // Adding the actual line start ensures the tool recognizes the lambda's real indent
            // context.
            childIndent = IndentLevel.addAcceptable(childIndent, getLineStart(getMainAst()));

            if (isSameLineAsSwitch(child.getMainAst()) || child instanceof SlistHandler) {
                // Lambda with block body (enclosed in {})
                childIndent = IndentLevel.addAcceptable(childIndent,
                    getLineStart(getMainAst().getFirstChild()));
            }
            else {
                // Single-expression lambda (no {} block):
                // assume line wrapping and add additional indentation
                // for the statement in the next line.
                childIndent = new IndentLevel(childIndent,
                        getIndentCheck().getLineWrappingIndentation());
            }
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

        final IndentLevel result;
        final DetailAST enumConstDef = findParentEnumConstantDef();
        if (enumConstDef != null) {
            result = getEnumConstantBasedIndent(enumConstDef);
        }
        else {
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
            result = level;
        }

        return result;
    }

    @Override
    public void checkIndentation() {
        final DetailAST mainAst = getMainAst();
        final DetailAST firstChild = mainAst.getFirstChild();

        // If the "->" has no children, it is a switch
        // rule lambda (i.e. 'case ONE -> 1;')
        final boolean isSwitchRuleLambda = firstChild == null;

        checkFirstChildIndentation(firstChild, isSwitchRuleLambda);

        // If the "->" is the first element on the line, assume line wrapping.
        final int mainAstColumnNo = expandedTabsColumnNo(mainAst);
        final boolean isLineWrappedLambda = mainAstColumnNo == getLineStart(mainAst);

        if (isLineWrappedLambda) {
            checkLineWrappedLambda(isSwitchRuleLambda, mainAstColumnNo);
        }

        checkLambdaBody(mainAst, isSwitchRuleLambda, isLineWrappedLambda);

        checkSwitchRuleLambdaNextSibling(mainAst, isSwitchRuleLambda, isLineWrappedLambda);
    }

    /**
     * Checks the indentation of the first child of the lambda expression.
     *
     * @param firstChild the first child of the lambda
     * @param isSwitchRuleLambda whether this is a switch rule lambda
     */
    private void checkFirstChildIndentation(DetailAST firstChild, boolean isSwitchRuleLambda) {
        if (isSwitchRuleLambda) {
            return;
        }

        if (getLineStart(firstChild) == expandedTabsColumnNo(firstChild)) {
            final int firstChildColumnNo = expandedTabsColumnNo(firstChild);
            final IndentLevel level = getIndent();

            if (isNonAcceptableIndent(firstChildColumnNo, level)) {
                isLambdaCorrectlyIndented = false;
                logError(firstChild, ARGUMENTS_KEY, firstChildColumnNo, level);
            }
        }
    }

    /**
     * Checks the indentation of the lambda body.
     *
     * @param mainAst the main lambda AST node
     * @param isSwitchRuleLambda whether this is a switch rule lambda
     * @param isLineWrappedLambda whether the lambda is line-wrapped
     */
    private void checkLambdaBody(DetailAST mainAst, boolean isSwitchRuleLambda,
                                  boolean isLineWrappedLambda) {
        if (isSwitchRuleLambda) {
            return;
        }

        final DetailAST lambdaBody = mainAst.getLastChild();
        if (lambdaBody.getType() == TokenTypes.SLIST) {
            return;
        }

        final int bodyType = lambdaBody.getType();
        final int actualBodyType = getActualBodyType(lambdaBody, bodyType);

        if (shouldCheckBodyIndentation(actualBodyType)) {
            checkNonBlockLambdaBodyIndentation(mainAst, lambdaBody, isLineWrappedLambda);
        }
    }

    /**
     * Gets the actual body type, unwrapping EXPR nodes if necessary.
     *
     * @param lambdaBody the lambda body AST node
     * @param bodyType the initial body type
     * @return the actual body type
     */
    private int getActualBodyType(DetailAST lambdaBody, int bodyType) {
        if (bodyType == TokenTypes.EXPR) {
            return lambdaBody.getFirstChild().getType();
        }
        return bodyType;
    }

    /**
     * Determines whether the body indentation should be checked based on its type.
     *
     * @param actualBodyType the actual type of the lambda body
     * @return true if indentation should be checked
     */
    private boolean shouldCheckBodyIndentation(int actualBodyType) {
        return actualBodyType != TokenTypes.LITERAL_NEW;
    }

    /**
     * Checks the indentation of non-block lambda bodies.
     *
     * @param mainAst the main lambda AST node
     * @param lambdaBody the lambda body AST node
     * @param isLineWrappedLambda whether the lambda is line-wrapped
     */
    private void checkNonBlockLambdaBodyIndentation(DetailAST mainAst, DetailAST lambdaBody,
                                                     boolean isLineWrappedLambda) {
        final DetailAST firstBodyToken = getFirstAstNode(lambdaBody);
        IndentLevel bodyIndent = getIndent();

        if (isLambdaCorrectlyIndented) {
            bodyIndent = calculateBodyIndent(mainAst, bodyIndent, isLineWrappedLambda);
        }

        validateBodyIndentation(firstBodyToken, bodyIndent);
    }

    /**
     * Calculates the expected indentation level for the lambda body.
     *
     * @param mainAst the main lambda AST node
     * @param bodyIndent the base body indentation
     * @param isLineWrappedLambda whether the lambda is line-wrapped
     * @return the calculated indentation level
     */
    private IndentLevel calculateBodyIndent(DetailAST mainAst, IndentLevel bodyIndent,
                                            boolean isLineWrappedLambda) {
        IndentLevel result = IndentLevel.addAcceptable(bodyIndent, getLineStart(mainAst));
        result = new IndentLevel(result, getIndentCheck().getLineWrappingIndentation());

        if (isLineWrappedLambda) {
            final int additionalIndent = getIndent().getFirstIndentLevel()
                + getIndentCheck().getBasicOffset();
            result = IndentLevel.addAcceptable(result, additionalIndent);
        }

        return result;
    }

    /**
     * Validates the indentation of the first token in the lambda body.
     *
     * @param firstBodyToken the first token in the lambda body
     * @param bodyIndent the expected indentation level
     */
    private void validateBodyIndentation(DetailAST firstBodyToken, IndentLevel bodyIndent) {
        final int firstBodyLine = firstBodyToken.getLineNo();
        final int firstBodyCol = expandedTabsColumnNo(firstBodyToken);
        final int firstBodyLineStart = getLineStart(firstBodyLine);

        if (firstBodyCol == firstBodyLineStart && !bodyIndent.isAcceptable(firstBodyLineStart)) {
            logError(firstBodyToken, ARGUMENTS_KEY, firstBodyLineStart, bodyIndent);
        }
    }

    /**
     * Checks the next sibling for switch rule lambda expressions.
     *
     * @param mainAst the main lambda AST node
     * @param isSwitchRuleLambda whether this is a switch rule lambda
     * @param isLineWrappedLambda whether the lambda is line-wrapped
     */
    private void checkSwitchRuleLambdaNextSibling(DetailAST mainAst, boolean isSwitchRuleLambda,
                                                   boolean isLineWrappedLambda) {
        if (!isSwitchRuleLambda) {
            return;
        }

        final DetailAST nextSibling = mainAst.getNextSibling();
        if (nextSibling.getType() == TokenTypes.EXPR
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

    /**
     * Checks if the current LAMBDA node is placed on the same line
     * as the given SWITCH_LITERAL node.
     *
     * @param node the SWITCH_LITERAL node to compare with
     * @return true if the current LAMBDA node is on the same line
     *     as the given SWITCH_LITERAL node
     */
    private boolean isSameLineAsSwitch(DetailAST node) {
        return node.getType() == TokenTypes.LITERAL_SWITCH
            && TokenUtil.areOnSameLine(getMainAst(), node);
    }

    /**
     * Finds the parent ENUM_CONSTANT_DEF node if this lambda is an argument of an enum constant.
     *
     * @return the ENUM_CONSTANT_DEF node if found, null otherwise
     */
    @Nullable
    private DetailAST findParentEnumConstantDef() {
        DetailAST result = null;
        DetailAST parent = getMainAst().getParent();
        while (parent != null) {
            if (parent.getType() == TokenTypes.ENUM_CONSTANT_DEF) {
                result = parent;
                break;
            }
            parent = parent.getParent();
        }
        return result;
    }

    /**
     * Calculates the expected indentation for a lambda inside enum constant arguments.
     * The expected indent is the enum constant's indent plus line wrapping indentation.
     *
     * @param enumConstDef the ENUM_CONSTANT_DEF node
     * @return the expected indentation level
     */
    private IndentLevel getEnumConstantBasedIndent(DetailAST enumConstDef) {
        final int enumConstIndent = getLineStart(enumConstDef);
        final IndentLevel baseLevel = new IndentLevel(enumConstIndent);
        return new IndentLevel(baseLevel, getIndentCheck().getLineWrappingIndentation());
    }
}
