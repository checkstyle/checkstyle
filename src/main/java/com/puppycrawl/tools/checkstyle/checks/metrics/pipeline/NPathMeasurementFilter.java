package com.puppycrawl.tools.checkstyle.checks.metrics.pipeline;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Computes NPATH complexity. Algorithm preserved
 * byte-for-byte from the original {@code NPathComplexityCheck}; only the call
 * shape changes (emit {@link Measurement} instead of {@code log(..)}).
 */
public final class NPathMeasurementFilter implements Filter<AstEvent, Measurement> {

    private static final int[] CASE_LABEL_TOKENS = {
        TokenTypes.EXPR,
        TokenTypes.PATTERN_DEF,
        TokenTypes.PATTERN_VARIABLE_DEF,
        TokenTypes.RECORD_PATTERN_DEF,
    };

    private static final BigInteger INITIAL_VALUE = BigInteger.ZERO;

    private final int max;
    private final String messageKey;

    private final Deque<BigInteger> rangeValues = new ArrayDeque<>();
    private final Deque<Integer> expressionValues = new ArrayDeque<>();
    private final Deque<Boolean> afterValues = new ArrayDeque<>();
    private final TokenEnd processingTokenEnd = new TokenEnd();

    private BigInteger currentRangeValue = INITIAL_VALUE;
    private boolean branchVisited;

    public NPathMeasurementFilter(int max, String messageKey) {
        this.max = max;
        this.messageKey = messageKey;
    }

    @Override
    public void process(Pipe<AstEvent> in, Pipe<Measurement> out) {
        while (in.hasNext()) {
            final AstEvent event = in.read();
            if (event == null) {
                break;
            }

            final DetailAST ast = event.getNode();

            if (event.getPhase() == AstEvent.Phase.VISIT) {
                switch (ast.getType()) {
                    case TokenTypes.LITERAL_IF, TokenTypes.LITERAL_SWITCH,
                         TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_DO,
                         TokenTypes.LITERAL_FOR -> visitConditional(ast, 1);

                    case TokenTypes.QUESTION -> visitUnitaryOperator(ast, 2);

                    case TokenTypes.LITERAL_RETURN -> visitUnitaryOperator(ast, 0);

                    case TokenTypes.LITERAL_WHEN -> visitWhenExpression(ast, 1);

                    case TokenTypes.CASE_GROUP -> {
                        final int caseNumber = countCaseTokens(ast);
                        branchVisited = true;
                        pushValue(caseNumber);
                    }

                    case TokenTypes.SWITCH_RULE -> {
                        final int caseConstantNumber = countCaseConstants(ast);
                        branchVisited = true;
                        pushValue(caseConstantNumber);
                    }

                    case TokenTypes.LITERAL_ELSE -> {
                        branchVisited = true;
                        if (currentRangeValue.equals(BigInteger.ZERO)) {
                            currentRangeValue = BigInteger.ONE;
                        }
                        pushValue(0);
                    }

                    case TokenTypes.LITERAL_TRY,
                         TokenTypes.LITERAL_CATCH,
                         TokenTypes.LITERAL_DEFAULT -> pushValue(1);

                    case TokenTypes.CTOR_DEF,
                         TokenTypes.METHOD_DEF,
                         TokenTypes.INSTANCE_INIT,
                         TokenTypes.STATIC_INIT,
                         TokenTypes.COMPACT_CTOR_DEF -> pushValue(0);

                    default -> {
                        // do nothing
                    }
                }
            }
            else if (event.getPhase() == AstEvent.Phase.LEAVE) {
                switch (ast.getType()) {
                    case TokenTypes.LITERAL_WHILE,
                         TokenTypes.LITERAL_DO,
                         TokenTypes.LITERAL_FOR,
                         TokenTypes.LITERAL_IF,
                         TokenTypes.LITERAL_SWITCH,
                         TokenTypes.LITERAL_WHEN -> leaveConditional();

                    case TokenTypes.LITERAL_TRY -> leaveMultiplyingConditional();

                    case TokenTypes.LITERAL_RETURN,
                         TokenTypes.QUESTION -> leaveUnitaryOperator();

                    case TokenTypes.LITERAL_CATCH -> leaveAddingConditional();

                    case TokenTypes.LITERAL_DEFAULT -> leaveBranch();

                    case TokenTypes.LITERAL_ELSE,
                         TokenTypes.CASE_GROUP,
                         TokenTypes.SWITCH_RULE -> {
                        leaveBranch();
                        branchVisited = false;
                    }

                    case TokenTypes.CTOR_DEF,
                         TokenTypes.METHOD_DEF,
                         TokenTypes.INSTANCE_INIT,
                         TokenTypes.STATIC_INIT,
                         TokenTypes.COMPACT_CTOR_DEF -> leaveMethodDef(ast, out);

                    default -> {
                        // do nothing
                    }
                }
            }
        }
    }

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

    private void visitWhenExpression(DetailAST ast, int basicBranchingFactor) {
        final int expressionValue = basicBranchingFactor + countConditionalOperators(ast);
        processingTokenEnd.setToken(getLastToken(ast));
        pushValue(expressionValue);
    }

    private void visitUnitaryOperator(DetailAST ast, int basicBranchingFactor) {
        final boolean isAfter = processingTokenEnd.isAfter(ast);
        afterValues.push(isAfter);
        if (!isAfter) {
            processingTokenEnd.setToken(getLastToken(ast));
            final int expressionValue = basicBranchingFactor + countConditionalOperators(ast);
            pushValue(expressionValue);
        }
    }

    private void leaveUnitaryOperator() {
        if (Boolean.FALSE.equals(afterValues.pop())) {
            final Values valuePair = popValue();
            BigInteger basicRangeValue = valuePair.rangeValue();
            BigInteger expressionValue = valuePair.expressionValue();
            if (expressionValue.equals(BigInteger.ZERO)) {
                expressionValue = BigInteger.ONE;
            }
            if (basicRangeValue.equals(BigInteger.ZERO)) {
                basicRangeValue = BigInteger.ONE;
            }
            currentRangeValue = currentRangeValue.add(expressionValue).multiply(basicRangeValue);
        }
    }

    private void leaveConditional() {
        final Values valuePair = popValue();
        final BigInteger expressionValue = valuePair.expressionValue();
        BigInteger basicRangeValue = valuePair.rangeValue();
        if (currentRangeValue.equals(BigInteger.ZERO)) {
            currentRangeValue = BigInteger.ONE;
        }
        if (basicRangeValue.equals(BigInteger.ZERO)) {
            basicRangeValue = BigInteger.ONE;
        }
        currentRangeValue = currentRangeValue.add(expressionValue).multiply(basicRangeValue);
    }

    private void leaveBranch() {
        final Values valuePair = popValue();
        final BigInteger basicRangeValue = valuePair.rangeValue();
        final BigInteger expressionValue = valuePair.expressionValue();
        if (branchVisited && currentRangeValue.equals(BigInteger.ZERO)) {
            currentRangeValue = BigInteger.ONE;
        }
        currentRangeValue = currentRangeValue.subtract(BigInteger.ONE)
                .add(basicRangeValue)
                .add(expressionValue);
    }

    private void leaveMethodDef(DetailAST ast, Pipe<Measurement> out) {
        final BigInteger bigIntegerMax = BigInteger.valueOf(max);
        out.write(new Measurement(ast, ast.getLineNo(), ast.getColumnNo(),
                currentRangeValue.longValue(), messageKey, currentRangeValue, bigIntegerMax));
        popValue();
        currentRangeValue = INITIAL_VALUE;
    }

    private void leaveAddingConditional() {
        currentRangeValue = currentRangeValue.add(popValue().rangeValue().add(BigInteger.ONE));
    }

    private void pushValue(Integer expressionValue) {
        rangeValues.push(currentRangeValue);
        expressionValues.push(expressionValue);
        currentRangeValue = INITIAL_VALUE;
    }

    private Values popValue() {
        final int expressionValue = expressionValues.pop();
        return new Values(rangeValues.pop(), BigInteger.valueOf(expressionValue));
    }

    private void leaveMultiplyingConditional() {
        currentRangeValue = currentRangeValue.add(BigInteger.ONE)
                .multiply(popValue().rangeValue().add(BigInteger.ONE));
    }

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

    private static int countCaseConstants(DetailAST ast) {
        int counter = 0;
        final DetailAST literalCase = ast.getFirstChild();

        for (DetailAST node = literalCase.getFirstChild(); node != null;
                    node = node.getNextSibling()) {
            if (TokenUtil.isOfType(node, CASE_LABEL_TOKENS)) {
                counter++;
            }
        }

        return counter;
    }

    private static final class TokenEnd {
        private int endLineNo;
        private int endColumnNo;

        void setToken(DetailAST endToken) {
            if (!isAfter(endToken)) {
                endLineNo = endToken.getLineNo();
                endColumnNo = endToken.getColumnNo();
            }
        }

        void reset() {
            endLineNo = 0;
            endColumnNo = 0;
        }

        boolean isAfter(DetailAST ast) {
            final int lineNo = ast.getLineNo();
            final int columnNo = ast.getColumnNo();
            return lineNo <= endLineNo
                && (lineNo != endLineNo || columnNo <= endColumnNo);
        }
    }

    private record Values(BigInteger rangeValue, BigInteger expressionValue) {
    }
}
