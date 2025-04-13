////
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
///

package com.puppycrawl.tools.checkstyle.checks.metrics;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

import com.puppycrawl.tools.checkstyle.FileStatefulCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * <div>
 * Checks the NPATH complexity against a specified limit.
 * </div>
 *
 * <p>
 * The NPATH metric computes the number of possible execution paths through a
 * function(method). It takes into account the nesting of conditional statements
 * and multipart boolean expressions (A &amp;&amp; B, C || D, E ? F :G and
 * their combinations).
 * </p>
 *
 * <p>
 * The NPATH metric was designed base on Cyclomatic complexity to avoid problem
 * of Cyclomatic complexity metric like nesting level within a function(method).
 * </p>
 *
 * <p>
 * Metric was described at <a href="http://dl.acm.org/citation.cfm?id=42379">
 * "NPATH: a measure of execution pathcomplexity and its applications"</a>.
 * If you need detailed description of algorithm, please read that article,
 * it is well written and have number of examples and details.
 * </p>
 *
 * <p>
 * Here is some quotes:
 * </p>
 * <blockquote>
 * An NPATH threshold value of 200 has been established for a function.
 * The value 200 is based on studies done at AT&amp;T Bell Laboratories [1988 year].
 * </blockquote>
 * <blockquote>
 * Some of the most effective methods of reducing the NPATH value include:
 * <ul>
 * <li>
 * distributing functionality;
 * </li>
 * <li>
 * implementing multiple if statements as a switch statement;
 * </li>
 * <li>
 * creating a separate function for logical expressions with a high count of
 * variables and (&amp;&amp;) and or (||) operators.
 * </li>
 * </ul>
 * </blockquote>
 * <blockquote>
 * Although strategies to reduce the NPATH complexity of functions are important,
 * care must be taken not to distort the logical clarity of the software by
 * applying a strategy to reduce the complexity of functions. That is, there is
 * a point of diminishing return beyond which a further attempt at reduction of
 * complexity distorts the logical clarity of the system structure.
 * </blockquote>
 * <table>
 * <caption>Examples</caption>
 * <thead><tr><th>Structure</th><th>Complexity expression</th></tr></thead>
 * <tr><td>if ([expr]) { [if-range] }</td><td>NP(if-range) + 1 + NP(expr)</td></tr>
 * <tr><td>if ([expr]) { [if-range] } else { [else-range] }</td>
 * <td>NP(if-range)+ NP(else-range) + NP(expr)</td></tr>
 * <tr><td>while ([expr]) { [while-range] }</td><td>NP(while-range) + NP(expr) + 1</td></tr>
 * <tr><td>do { [do-range] } while ([expr])</td><td>NP(do-range) + NP(expr) + 1</td></tr>
 * <tr><td>for([expr1]; [expr2]; [expr3]) { [for-range] }</td>
 * <td>NP(for-range) + NP(expr1)+ NP(expr2) + NP(expr3) + 1</td></tr>
 * <tr><td>switch ([expr]) { case : [case-range] default: [default-range] }</td>
 * <td>S(i=1:i=n)NP(case-range[i]) + NP(default-range) + NP(expr)</td></tr>
 * <tr><td>when[expr]</td><td>NP(expr) + 1</td></tr>
 * <tr><td>[expr1] ? [expr2] : [expr3]</td><td>NP(expr1) + NP(expr2) + NP(expr3) + 2</td></tr>
 * <tr><td>goto label</td><td>1</td></tr><tr><td>break</td><td>1</td></tr>
 * <tr><td>Expressions</td>
 * <td>Number of &amp;&amp; and || operators in expression. No operators - 0</td></tr>
 * <tr><td>continue</td><td>1</td></tr><tr><td>return</td><td>1</td></tr>
 * <tr><td>Statement (even sequential statements)</td><td>1</td></tr>
 * <tr><td>Empty block {}</td><td>1</td></tr><tr><td>Function call</td><td>1</td>
 * </tr><tr><td>Function(Method) declaration or Block</td><td>P(i=1:i=N)NP(Statement[i])</td></tr>
 * </table>
 *
 * <p>
 * <b>Rationale:</b> Nejmeh says that his group had an informal NPATH limit of
 * 200 on individual routines; functions(methods) that exceeded this value were
 * candidates for further decomposition - or at least a closer look.
 * <b>Please do not be fanatic with limit 200</b> - choose number that suites
 * your project style. Limit 200 is empirical number base on some sources of at
 * AT&amp;T Bell Laboratories of 1988 year.
 * </p>
 * <ul>
 * <li>
 * Property {@code max} - Specify the maximum threshold allowed.
 * Type is {@code int}.
 * Default value is {@code 200}.
 * </li>
 * </ul>
 *
 * <p>
 * Parent is {@code com.puppycrawl.tools.checkstyle.TreeWalker}
 * </p>
 *
 * <p>
 * Violation Message Keys:
 * </p>
 * <ul>
 * <li>
 * {@code npathComplexity}
 * </li>
 * </ul>
 *
 * @since 3.4
 */
// -@cs[AbbreviationAsWordInName] Can't change check name
@FileStatefulCheck
public final class NPathComplexityCheck extends AbstractCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "npathComplexity";

    /** Tokens that are considered as case labels. */
    private static final int[] CASE_LABEL_TOKENS = {
        TokenTypes.EXPR,
        TokenTypes.PATTERN_DEF,
        TokenTypes.PATTERN_VARIABLE_DEF,
        TokenTypes.RECORD_PATTERN_DEF,
    };

    /** Default allowed complexity. */
    private static final int DEFAULT_MAX = 200;

    /** The initial current value. */
    private static final BigInteger INITIAL_VALUE = BigInteger.ZERO;

    /**
     * Stack of NP values for ranges.
     */
    private final Deque<BigInteger> rangeValues = new ArrayDeque<>();

    /** Stack of NP values for expressions. */
    private final Deque<Integer> expressionValues = new ArrayDeque<>();

    /** Stack of belongs to range values for question operator. */
    private final Deque<Boolean> afterValues = new ArrayDeque<>();

    /**
     * Range of the last processed expression. Used for checking that ternary operation
     * which is a part of expression won't be processed for second time.
     */
    private final TokenEnd processingTokenEnd = new TokenEnd();

    /** NP value for current range. */
    private BigInteger currentRangeValue;

    /** Specify the maximum threshold allowed. */
    private int max = DEFAULT_MAX;

    /** True, when branch is visited, but not leaved. */
    private boolean branchVisited;

    /**
     * Setter to specify the maximum threshold allowed.
     *
     * @param max the maximum threshold
     * @since 3.4
     */
    public void setMax(int max) {
        this.max = max;
    }

    @Override
    public int[] getDefaultTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getAcceptableTokens() {
        return getRequiredTokens();
    }

    @Override
    public int[] getRequiredTokens() {
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
            TokenTypes.LITERAL_TRY,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.QUESTION,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.COMPACT_CTOR_DEF,
            TokenTypes.SWITCH_RULE,
            TokenTypes.LITERAL_WHEN,
        };
    }

    @Override
    public void beginTree(DetailAST rootAST) {
        rangeValues.clear();
        expressionValues.clear();
        afterValues.clear();
        processingTokenEnd.reset();
        currentRangeValue = INITIAL_VALUE;
        branchVisited = false;
    }

    @Override
    public void visitToken(DetailAST ast) {
        switch (ast.getType()) {
            case TokenTypes.LITERAL_IF:
            case TokenTypes.LITERAL_SWITCH:
            case TokenTypes.LITERAL_WHILE:
            case TokenTypes.LITERAL_DO:
            case TokenTypes.LITERAL_FOR:
                visitConditional(ast, 1);
                break;
            case TokenTypes.QUESTION:
                visitUnitaryOperator(ast, 2);
                break;
            case TokenTypes.LITERAL_RETURN:
                visitUnitaryOperator(ast, 0);
                break;
            case TokenTypes.LITERAL_WHEN:
                visitWhenExpression(ast, 1);
                break;
            case TokenTypes.CASE_GROUP:
                final int caseNumber = countCaseTokens(ast);
                branchVisited = true;
                pushValue(caseNumber);
                break;
            case TokenTypes.SWITCH_RULE:
                final int caseConstantNumber = countCaseConstants(ast);
                branchVisited = true;
                pushValue(caseConstantNumber);
                break;
            case TokenTypes.LITERAL_ELSE:
                branchVisited = true;
                if (currentRangeValue.equals(BigInteger.ZERO)) {
                    currentRangeValue = BigInteger.ONE;
                }
                pushValue(0);
                break;
            case TokenTypes.LITERAL_TRY:
            case TokenTypes.LITERAL_CATCH:
            case TokenTypes.LITERAL_DEFAULT:
                pushValue(1);
                break;
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
            case TokenTypes.COMPACT_CTOR_DEF:
                pushValue(0);
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
            case TokenTypes.LITERAL_WHEN:
                leaveConditional();
                break;
            case TokenTypes.LITERAL_TRY:
                leaveMultiplyingConditional();
                break;
            case TokenTypes.LITERAL_RETURN:
            case TokenTypes.QUESTION:
                leaveUnitaryOperator();
                break;
            case TokenTypes.LITERAL_CATCH:
                leaveAddingConditional();
                break;
            case TokenTypes.LITERAL_DEFAULT:
                leaveBranch();
                break;
            case TokenTypes.LITERAL_ELSE:
            case TokenTypes.CASE_GROUP:
            case TokenTypes.SWITCH_RULE:
                leaveBranch();
                branchVisited = false;
                break;
            case TokenTypes.CTOR_DEF:
            case TokenTypes.METHOD_DEF:
            case TokenTypes.INSTANCE_INIT:
            case TokenTypes.STATIC_INIT:
            case TokenTypes.COMPACT_CTOR_DEF:
                leaveMethodDef(ast);
                break;
            default:
                break;
        }
    }

    /**
     * Visits if, while, do-while, for and switch tokens - all of them have expression in
     * parentheses which is used for calculation.
     *
     * @param ast visited token.
     * @param basicBranchingFactor default number of branches added.
     */
    private void visitConditional(DetailAST ast, int basicBranchingFactor) {
        int expressionValue = basicBranchingFactor;
        DetailAST bracketed;
        for (bracketed = ast.findFirstToken(TokenTypes.LPAREN);
             bracketed.getType() != TokenTypes.RPAREN;
             bracketed = bracketed.getNextSibling()) {
            expressionValue += countConditionalOperators(bracketed);
        }
        processingTokenEnd.setToken(bracketed);
        pushValue(expressionValue);
    }

    /**
     * Visits when expression token. There is no guarantee that when expression will be
     * bracketed, so we don't use visitConditional method.
     *
     * @param ast visited token.
     * @param basicBranchingFactor default number of branches added.
     */
    private void visitWhenExpression(DetailAST ast, int basicBranchingFactor) {
        final int expressionValue = basicBranchingFactor + countConditionalOperators(ast);
        processingTokenEnd.setToken(getLastToken(ast));
        pushValue(expressionValue);
    }

    /**
     * Visits ternary operator (?:) and return tokens. They differ from those processed by
     * visitConditional method in that their expression isn't bracketed.
     *
     * @param ast visited token.
     * @param basicBranchingFactor number of branches inherently added by this token.
     */
    private void visitUnitaryOperator(DetailAST ast, int basicBranchingFactor) {
        final boolean isAfter = processingTokenEnd.isAfter(ast);
        afterValues.push(isAfter);
        if (!isAfter) {
            processingTokenEnd.setToken(getLastToken(ast));
            final int expressionValue = basicBranchingFactor + countConditionalOperators(ast);
            pushValue(expressionValue);
        }
    }

    /**
     * Leaves ternary operator (?:) and return tokens.
     */
    private void leaveUnitaryOperator() {
        if (Boolean.FALSE.equals(afterValues.pop())) {
            final Values valuePair = popValue();
            BigInteger basicRangeValue = valuePair.getRangeValue();
            BigInteger expressionValue = valuePair.getExpressionValue();
            if (expressionValue.equals(BigInteger.ZERO)) {
                expressionValue = BigInteger.ONE;
            }
            if (basicRangeValue.equals(BigInteger.ZERO)) {
                basicRangeValue = BigInteger.ONE;
            }
            currentRangeValue = currentRangeValue.add(expressionValue).multiply(basicRangeValue);
        }
    }

    /** Leaves while, do, for, if, ternary (?::), return or switch. */
    private void leaveConditional() {
        final Values valuePair = popValue();
        final BigInteger expressionValue = valuePair.getExpressionValue();
        BigInteger basicRangeValue = valuePair.getRangeValue();
        if (currentRangeValue.equals(BigInteger.ZERO)) {
            currentRangeValue = BigInteger.ONE;
        }
        if (basicRangeValue.equals(BigInteger.ZERO)) {
            basicRangeValue = BigInteger.ONE;
        }
        currentRangeValue = currentRangeValue.add(expressionValue).multiply(basicRangeValue);
    }

    /** Leaves else, default or case group tokens. */
    private void leaveBranch() {
        final Values valuePair = popValue();
        final BigInteger basicRangeValue = valuePair.getRangeValue();
        final BigInteger expressionValue = valuePair.getExpressionValue();
        if (branchVisited && currentRangeValue.equals(BigInteger.ZERO)) {
            currentRangeValue = BigInteger.ONE;
        }
        currentRangeValue = currentRangeValue.subtract(BigInteger.ONE)
            .add(basicRangeValue)
            .add(expressionValue);
    }

    /**
     * Process the end of a method definition.
     *
     * @param ast the token type representing the method definition
     */
    private void leaveMethodDef(DetailAST ast) {
        final BigInteger bigIntegerMax = BigInteger.valueOf(max);
        if (currentRangeValue.compareTo(bigIntegerMax) > 0) {
            log(ast, MSG_KEY, currentRangeValue, bigIntegerMax);
        }
        popValue();
        currentRangeValue = INITIAL_VALUE;
    }

    /** Leaves catch. */
    private void leaveAddingConditional() {
        currentRangeValue = currentRangeValue.add(popValue().getRangeValue().add(BigInteger.ONE));
    }

    /**
     * Pushes the current range value on the range value stack. Pushes this token expression value
     * on the expression value stack.
     *
     * @param expressionValue value of expression calculated for current token.
     */
    private void pushValue(Integer expressionValue) {
        rangeValues.push(currentRangeValue);
        expressionValues.push(expressionValue);
        currentRangeValue = INITIAL_VALUE;
    }

    /**
     * Pops values from both stack of expression values and stack of range values.
     *
     * @return pair of head values from both of the stacks.
     */
    private Values popValue() {
        final int expressionValue = expressionValues.pop();
        return new Values(rangeValues.pop(), BigInteger.valueOf(expressionValue));
    }

    /** Leaves try. */
    private void leaveMultiplyingConditional() {
        currentRangeValue = currentRangeValue.add(BigInteger.ONE)
            .multiply(popValue().getRangeValue().add(BigInteger.ONE));
    }

    /**
     * Calculates number of conditional operators, including inline ternary operator, for a token.
     *
     * @param ast inspected token.
     * @return number of conditional operators.
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.23">
     *     Java Language Specification, &sect;15.23</a>
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.24">
     *     Java Language Specification, &sect;15.24</a>
     * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.25">
     *     Java Language Specification, &sect;15.25</a>
     */
    private static int countConditionalOperators(DetailAST ast) {
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
            number += countConditionalOperators(child);
        }
        return number;
    }

    /**
     * Finds a leaf, which is the most distant from the root.
     *
     * @param ast the root of tree.
     * @return the leaf.
     */
    private static DetailAST getLastToken(DetailAST ast) {
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
     *
     * @param ast case group token.
     * @return number of case tokens.
     */
    private static int countCaseTokens(DetailAST ast) {
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
     * Counts number of case constants tokens in a switch labeled rule.
     *
     * @param ast switch rule token.
     * @return number of case constant tokens.
     */
    private static int countCaseConstants(DetailAST ast) {
        final AtomicInteger counter = new AtomicInteger();
        final DetailAST literalCase = ast.getFirstChild();

        for (DetailAST node = literalCase.getFirstChild(); node != null;
             node = node.getNextSibling()) {
            if (TokenUtil.isOfType(node, CASE_LABEL_TOKENS)) {
                counter.getAndIncrement();
            }
        }

        return counter.get();
    }

    /**
     * Coordinates of token end. Used to prevent inline ternary
     * operator from being processed twice.
     */
    private static final class TokenEnd {

        /** End line of token. */
        private int endLineNo;

        /** End column of token. */
        private int endColumnNo;

        /**
         * Sets end coordinates from given token.
         *
         * @param endToken token.
         */
        public void setToken(DetailAST endToken) {
            if (!isAfter(endToken)) {
                endLineNo = endToken.getLineNo();
                endColumnNo = endToken.getColumnNo();
            }
        }

        /** Sets end token coordinates to the start of the file. */
        public void reset() {
            endLineNo = 0;
            endColumnNo = 0;
        }

        /**
         * Checks if saved coordinates located after given token.
         *
         * @param ast given token.
         * @return true, if saved coordinates located after given token.
         */
        public boolean isAfter(DetailAST ast) {
            final int lineNo = ast.getLineNo();
            final int columnNo = ast.getColumnNo();
            return lineNo <= endLineNo
                && (lineNo != endLineNo
                || columnNo <= endColumnNo);
        }

    }

    /**
     * Class that store range value and expression value.
     */
    private static final class Values {

        /** NP value for range. */
        private final BigInteger rangeValue;

        /** NP value for expression. */
        private final BigInteger expressionValue;

        /**
         * Constructor that assigns all of class fields.
         *
         * @param valueOfRange NP value for range
         * @param valueOfExpression NP value for expression
         */
        private Values(BigInteger valueOfRange, BigInteger valueOfExpression) {
            rangeValue = valueOfRange;
            expressionValue = valueOfExpression;
        }

        /**
         * Returns NP value for range.
         *
         * @return NP value for range
         */
        public BigInteger getRangeValue() {
            return rangeValue;
        }

        /**
         * Returns NP value for expression.
         *
         * @return NP value for expression
         */
        public BigInteger getExpressionValue() {
            return expressionValue;
        }

    }

}
