package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.Measurement;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Computes the number of executable statements. Algorithm preserved
 * byte-for-byte from the original {@code ExecutableStatementCountCheck}; only the call
 * shape changes (emit {@link Measurement} instead of {@code log(..)}).
 */
public final class ExecutableStatementCountMeasurementFilter implements Filter<AstEvent, Measurement> {

    private final int max;
    private final String messageKey;

    private final Deque<Context> contextStack = new ArrayDeque<>();
    private Context context = new Context(null);

    public ExecutableStatementCountMeasurementFilter(int max, String messageKey) {
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
                if (isContainerNode(ast)) {
                    contextStack.push(context);
                    context = new Context(ast);
                }
                else if (ast.getType() == TokenTypes.SLIST) {
                    final DetailAST contextAST = context.getAST();
                    DetailAST parent = ast;
                    while (parent != null && !isContainerNode(parent)) {
                        parent = parent.getParent();
                    }
                    if (parent == contextAST) {
                        context.addCount(ast.getChildCount() / 2);
                    }
                }
            }
            else if (event.getPhase() == AstEvent.Phase.LEAVE) {
                if (isContainerNode(ast)) {
                    final int count = context.getCount();
                    out.write(new Measurement(ast, ast.getLineNo(), ast.getColumnNo(),
                            count, messageKey, count, max));
                    context = contextStack.pop();
                }
            }
        }
    }

    private static boolean isContainerNode(DetailAST node) {
        final int type = node.getType();
        return type == TokenTypes.METHOD_DEF || type == TokenTypes.LAMBDA
            || type == TokenTypes.CTOR_DEF || type == TokenTypes.INSTANCE_INIT
            || type == TokenTypes.STATIC_INIT || type == TokenTypes.COMPACT_CTOR_DEF;
    }

    private static final class Context {
        private final DetailAST ast;
        private int count;

        Context(DetailAST ast) {
            this.ast = ast;
        }

        void addCount(int addition) {
            count += addition;
        }

        DetailAST getAST() {
            return ast;
        }

        int getCount() {
            return count;
        }
    }
}
