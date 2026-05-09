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
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * Computes cyclomatic complexity. Algorithm preserved
 * byte-for-byte from the original {@code CyclomaticComplexityCheck}; only the call
 * shape changes (emit {@link Measurement} instead of {@code log(..)}).
 */
public final class CyclomaticMeasurementFilter implements Filter<AstEvent, Measurement> {

    private static final BigInteger INITIAL_VALUE = BigInteger.ONE;

    private final boolean switchBlockAsSingleDecisionPoint;
    private final int max;
    private final String messageKey;

    private final Deque<BigInteger> valueStack = new ArrayDeque<>();
    private BigInteger currentValue = INITIAL_VALUE;

    public CyclomaticMeasurementFilter(boolean switchBlockAsSingleDecisionPoint, int max, String messageKey) {
        this.switchBlockAsSingleDecisionPoint = switchBlockAsSingleDecisionPoint;
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
                    case TokenTypes.CTOR_DEF,
                         TokenTypes.METHOD_DEF,
                         TokenTypes.INSTANCE_INIT,
                         TokenTypes.STATIC_INIT,
                         TokenTypes.COMPACT_CTOR_DEF -> visitMethodDef();
                    default -> visitTokenHook(ast);
                }
            }
            else if (event.getPhase() == AstEvent.Phase.LEAVE) {
                switch (ast.getType()) {
                    case TokenTypes.CTOR_DEF,
                         TokenTypes.METHOD_DEF,
                         TokenTypes.INSTANCE_INIT,
                         TokenTypes.STATIC_INIT,
                         TokenTypes.COMPACT_CTOR_DEF -> leaveMethodDef(ast, out);
                    default -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    private void visitTokenHook(DetailAST ast) {
        if (switchBlockAsSingleDecisionPoint) {
            if (!ScopeUtil.isInBlockOf(ast, TokenTypes.LITERAL_SWITCH)) {
                incrementCurrentValue(BigInteger.ONE);
            }
        }
        else if (ast.getType() != TokenTypes.LITERAL_SWITCH) {
            incrementCurrentValue(BigInteger.ONE);
        }
    }

    private void incrementCurrentValue(BigInteger amount) {
        currentValue = currentValue.add(amount);
    }

    private void pushValue() {
        valueStack.push(currentValue);
        currentValue = INITIAL_VALUE;
    }

    private void popValue() {
        currentValue = valueStack.pop();
    }

    private void visitMethodDef() {
        pushValue();
    }

    private void leaveMethodDef(DetailAST ast, Pipe<Measurement> out) {
        final BigInteger bigIntegerMax = BigInteger.valueOf(max);
        out.write(new Measurement(ast, ast.getLineNo(), ast.getColumnNo(),
                currentValue.intValue(), messageKey, currentValue, bigIntegerMax));
        popValue();
    }
}
