package com.puppycrawl.tools.checkstyle.checks.metrics.pipeline;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;
import com.puppycrawl.tools.checkstyle.utils.CheckUtil;

/**
 * Computes boolean expression complexity. Algorithm preserved
 * byte-for-byte from the original {@code BooleanExpressionComplexityCheck}; only the call
 * shape changes (emit {@link Measurement} instead of {@code log(..)}).
 */
public final class BooleanExpressionMeasurementFilter implements Filter<AstEvent, Measurement> {

    private final int max;
    private final String messageKey;

    private final Deque<Context> contextStack = new ArrayDeque<>();
    private Context context = new Context(false);

    public BooleanExpressionMeasurementFilter(int max, String messageKey) {
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
                         TokenTypes.COMPACT_CTOR_DEF -> visitMethodDef(ast);

                    case TokenTypes.EXPR -> visitExpr();

                    case TokenTypes.BOR -> {
                        if (!isPipeOperator(ast) && !isPassedInParameter(ast)) {
                            context.visitBooleanOperator();
                        }
                    }

                    case TokenTypes.BAND,
                         TokenTypes.BXOR -> {
                        if (!isPassedInParameter(ast)) {
                            context.visitBooleanOperator();
                        }
                    }

                    case TokenTypes.LAND,
                         TokenTypes.LOR -> context.visitBooleanOperator();

                    default -> {
                        // Not all accepted tokens need handling in VISIT
                    }
                }
            }
            else if (event.getPhase() == AstEvent.Phase.LEAVE) {
                switch (ast.getType()) {
                    case TokenTypes.CTOR_DEF,
                         TokenTypes.METHOD_DEF,
                         TokenTypes.COMPACT_CTOR_DEF -> leaveMethodDef();

                    case TokenTypes.EXPR -> leaveExpr(ast, out);

                    default -> {
                        // Do nothing
                    }
                }
            }
        }
    }

    private static boolean isPassedInParameter(DetailAST logicalOperator) {
        return logicalOperator.getParent().getParent().getType() == TokenTypes.ELIST;
    }

    private static boolean isPipeOperator(DetailAST binaryOr) {
        return binaryOr.getParent().getType() == TokenTypes.TYPE;
    }

    private void visitMethodDef(DetailAST ast) {
        contextStack.push(context);
        final boolean check = !CheckUtil.isEqualsMethod(ast);
        context = new Context(check);
    }

    private void leaveMethodDef() {
        context = contextStack.pop();
    }

    private void visitExpr() {
        contextStack.push(context);
        context = new Context(context.isChecking());
    }

    private void leaveExpr(DetailAST ast, Pipe<Measurement> out) {
        context.checkCount(ast, out, max, messageKey);
        context = contextStack.pop();
    }

    private static final class Context {
        private final boolean checking;
        private int count;

        private Context(boolean checking) {
            this.checking = checking;
        }

        boolean isChecking() {
            return checking;
        }

        void visitBooleanOperator() {
            ++count;
        }

        void checkCount(DetailAST ast, Pipe<Measurement> out, int max, String messageKey) {
            if (checking) {
                final DetailAST parentAST = ast.getParent();
                out.write(new Measurement(parentAST, parentAST.getLineNo(), parentAST.getColumnNo(),
                        count, messageKey, count, max));
            }
        }
    }
}
