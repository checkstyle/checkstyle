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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

import org.apache.commons.lang3.tuple.Pair;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Checks the npath complexity against a specified limit (default = 200). The npath metric computes
 * the number of possible execution paths through a function. Similar to the cyclomatic complexity
 * but also takes into account the nesting of conditional statements and multi-part boolean
 * expressions.
 * @author <a href="mailto:simon@redhillconsulting.com.au">Simon Harris</a>
 * @author o_sukhodolsky
 * @author attatrol
 */
public final class NPathComplexityCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties" file.
     */
    public static final String MSG_KEY = "npathComplexity";

    /**
     * Default allowed complexity.
     */
    private static final int DEFAULT_MAX = 200;

    /**
     * The initial current value.
     */
    private static final BigInteger INITIAL_VALUE = BigInteger.ONE;

    /**
     * Stack of NP values for ranges.
     */
    private final Deque<BigInteger> rangeValueStack = new ArrayDeque<>();

    /**
     * Stack of NP values for expressions.
     */
    private final Deque<Integer> expressionValueStack = new ArrayDeque<>();

    /**
     * Spatial range of the last processed expression. Used for checking that ternary operation
     * which is a part of expression won't be processed for second time.
     */
    private final SpatialRange processedExpressionRange = new SpatialRange();

    /**
     * NP value for current range.
     */
    private BigInteger currentRangeValue = INITIAL_VALUE;

    /**
     * Threshold to report error for.
     */
    private int max = DEFAULT_MAX;

    /**
     * Set the maximum threshold allowed.
     * @param max
     *        the maximum threshold
     */
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public int[] getDefaultTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.CASE_GROUP,
            TokenTypes.QUESTION,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_DEFAULT,
        };
    }

    @Override
    public int[] getAcceptableTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.CASE_GROUP,
            TokenTypes.QUESTION,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_DEFAULT,
        };
    }

    @Override
    public int[] getRequiredTokens() {
        return new int[] {
            TokenTypes.CTOR_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        processedExpressionRange.resetRange();
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_IF:
                visitConditional(ast, 1);
                break;
            case TokenTypes.LITERAL_SWITCH:
                visitConditional(ast, 0);
                break;
            case TokenTypes.QUESTION:
                if (!processedExpressionRange.belongsToRange(ast)) {
                    visitUnitaryOperator(ast, 1);
                }
                break;
            case TokenTypes.LITERAL_RETURN:
                visitUnitaryOperator(ast, 0);
                break;
            case TokenTypes.LITERAL_ELSE:
            case TokenTypes.LITERAL_DEFAULT:
                visitBranch(0);
                break;
            case TokenTypes.CASE_GROUP:
                visitCaseGroup(ast);
                break;
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                visitMethodDef();
                break;
            default:
                break;
        }
    }

    @Override
    public void leaveToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_FOR:
            case TokenTypes.LITERAL_IF:
            case TokenTypes.LITERAL_SWITCH:
            case TokenTypes.LITERAL_RETURN:
                leaveConditional();
                break;
            case TokenTypes.QUESTION:
                if (!processedExpressionRange.belongsToRange(ast)) {
                    leaveConditional();
                }
                break;
            case TokenTypes.LITERAL_ELSE:
            case TokenTypes.CASE_GROUP:
            case TokenTypes.LITERAL_DEFAULT:
                leaveBranch();
                break;
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
                leaveMethodDef(ast);
                break;
            default:
                break;
        }
    }

    /**
     * Visits if, while, do-while, for and switch tokens - all of them have expression in
     * parentheses which is used for calculation.
     * @param ast
     *        visited token.
     * @param basicBranchingFactor
     *        default number of branches added.
     */
    private void visitConditional(DetailAST ast, int basicBranchingFactor) {
        int expressionValue = basicBranchingFactor;
        DetailAST bracketed;
        for (bracketed = ast.findFirstToken(TokenTypes.LPAREN).getNextSibling();
                bracketed.getType() != TokenTypes.RPAREN;
                bracketed = bracketed.getNextSibling()) {
            expressionValue += countLogicalOperators(bracketed);
        }
        processedExpressionRange.setRange(ast, bracketed);
        pushValue(expressionValue);
    }

    /**
     * Visits ternary operator (?:) and return tokens. They differ from those processed by
     * visitConditional method in that their expression isn't bracketed.
     * @param ast
     *        visited token.
     * @param basicBranchingFactor
     *        number of branches inherently added by this token.
     */
    private void visitUnitaryOperator(DetailAST ast, int basicBranchingFactor) {
        final int expressionValue = basicBranchingFactor + countLogicalOperators(ast);
        processedExpressionRange.setRange(ast, getLastToken(ast));
        pushValue(expressionValue);
    }

    /**
     * Visits else, default and indirectly case group tokens.
     * @param branchMultiplier
     *        number of additional branches added by this token.
     */
    private void visitBranch(Integer branchMultiplier) {
        pushValue(branchMultiplier);
    }

    /**
     * Visits case group token.
     * @param ast
     *        visited token.
     */
    private void visitCaseGroup(DetailAST ast) {
        final int caseNumber = countCaseTokens(ast);
        visitBranch(caseNumber);
    }

    /**
     * Process the start of the method definition.
     */
    private void visitMethodDef() {
        pushValue(0);
    }

    /**
     * Leaves while, do, for, if, ternary (?::), return or switch.
     */
    private void leaveConditional() {
        final Pair<BigInteger, BigInteger> valuePair = popValue();
        final BigInteger basicRangeValue = valuePair.getLeft();
        final BigInteger expressionValue = valuePair.getRight();
        currentRangeValue = currentRangeValue.add(expressionValue).multiply(basicRangeValue);
    }

    /**
     * Leaves else, default or case group tokens.
     */
    private void leaveBranch() {
        final Pair<BigInteger, BigInteger> valuePair = popValue();
        final BigInteger basicRangeValue = valuePair.getLeft();
        final BigInteger expressionValue = valuePair.getRight();
        currentRangeValue = currentRangeValue
                .subtract(BigInteger.ONE).add(basicRangeValue).add(expressionValue);
    }

    /**
     * Process the end of a method definition.
     * @param ast
     *        the token type representing the method definition
     */
    private void leaveMethodDef(DetailAST ast) {
        final BigInteger bigIntegerMax = BigInteger.valueOf(max);
        if (currentRangeValue.compareTo(bigIntegerMax) > 0) {
            log(ast, MSG_KEY, currentRangeValue, bigIntegerMax);
        }
        popValue();
        currentRangeValue = INITIAL_VALUE;
    }

    /**
     * Pushes the current range value on the range value stack. Pushes this token expression value
     * on the expression value stack.
     * @param expressionValue
     *        value of expression calculated for current token.
     */
    private void pushValue(Integer expressionValue) {
        rangeValueStack.push(currentRangeValue);
        expressionValueStack.push(expressionValue);
        currentRangeValue = INITIAL_VALUE;
    }

    /**
     * Pops values from both stack of expression values and stack of range values.
     * @return pair of head values from both of the stacks.
     */
    private Pair<BigInteger, BigInteger> popValue() {
        final int expressionValue = expressionValueStack.pop();
        return Pair.of(rangeValueStack.pop(),
                BigInteger.valueOf(expressionValue));
    }

    /**
     * Calculates number of logical operators, including inline ternary operatior, for a token.
     * @param ast
     *        inspected token.
     * @return number of logical operators.
     */
    private int countLogicalOperators(DetailAST ast) {
        int number = 0;
        for (DetailAST child = ast.getFirstChild(); child != null;
                child = child.getNextSibling()) {
            final int type = child.getType();
            if (type == TokenTypes.LOR || type == TokenTypes.LAND) {
                number++;
            }
            else if (type == TokenTypes.QUESTION) {
                number += 2;
            }
            number += countLogicalOperators(child);
        }
        return number;
    }

    /**
     * Finds a leaf, which is the most distant from the root.
     * @param ast
     *        the root of tree.
     * @return the leaf.
     */
    private DetailAST getLastToken(DetailAST ast) {
        final DetailAST lastChild = ast.getLastChild();
        final DetailAST result;
        if (lastChild.getFirstChild() == null) {
            result = lastChild;
        }
        else {
            result = getLastToken(lastChild);
        }
        return result;
    }

    /**
     * Counts number of case tokens subject to a case group token.
     * @param ast
     *        case group token.
     * @return number of case tokens.
     */
    private int countCaseTokens(DetailAST ast) {
        int counter = 0;
        for (DetailAST iterator = ast.getFirstChild(); iterator != null;
                iterator = iterator.getNextSibling()) {
            if (iterator.getType() == TokenTypes.LITERAL_CASE) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * A spatial range between tokens. Used to prevent inline ternary operator from being processed
     * twice.
     * @author atta_troll
     */
    private static class SpatialRange {
        /**
         * Start line of the range.
         */
        private int startLineNo;

        /**
         * Start column of the range.
         */
        private int startColumnNo;

        /**
         * End line of the range.
         */
        private int endLineNo;

        /**
         * End column of the range.
         */
        private int endColumnNo;

        /**
         * Sets range between two tokens.
         * @param startToken
         *        starting token of the range.
         * @param endToken
         *        ending token of the range.
         */
        public void setRange(DetailAST startToken, DetailAST endToken) {
            if (!belongsToRange(startToken)) {
                startLineNo = startToken.getLineNo();
                startColumnNo = startToken.getColumnNo();
            }
            if (!belongsToRange(endToken)) {
                endLineNo = endToken.getLineNo();
                endColumnNo = endToken.getColumnNo();
            }
        }

        /**
         * Sets range to the start of the file.
         */
        public void resetRange() {
            startLineNo = 0;
            startColumnNo = 0;
            endLineNo = 0;
            endColumnNo = 0;
        }

        /**
         * Checks if token belongs to the range.
         * @param ast
         *        inspected token.
         * @return true, if token belongs to the range.
         */
        public boolean belongsToRange(DetailAST ast) {
            final int lineNo = ast.getLineNo();
            final int columnNo = ast.getColumnNo();
            boolean inRange = true;
            if (lineNo < startLineNo
                    || lineNo > endLineNo
                    || lineNo == endLineNo
                    && (columnNo <= startColumnNo || columnNo > endColumnNo)) {
                inRange = false;
            }
            return inRange;
        }
    }
}
