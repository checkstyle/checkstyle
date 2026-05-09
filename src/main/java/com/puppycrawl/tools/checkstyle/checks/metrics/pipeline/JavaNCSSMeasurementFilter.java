package com.puppycrawl.tools.checkstyle.checks.metrics.pipeline;

import java.util.ArrayDeque;
import java.util.Deque;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.ViolationMessage;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * JavaNCSS maintains a stack of counters and applies four independent thresholds.
 * Because the comparison uses multiple thresholds (file, class, record, method),
 * this filter performs both the measurement and the threshold steps and emits
 * {@code ViolationMessage}s directly. ThresholdFilter is therefore not used
 * in this pipeline.
 */
public final class JavaNCSSMeasurementFilter implements Filter<AstEvent, ViolationMessage> {

    private final int fileMaximum;
    private final int classMaximum;
    private final int recordMaximum;
    private final int methodMaximum;

    private final String msgMethod;
    private final String msgClass;
    private final String msgRecord;
    private final String msgFile;

    private Deque<Counter> counters;

    public JavaNCSSMeasurementFilter(int fileMaximum, int classMaximum, int recordMaximum, int methodMaximum,
                                     String msgMethod, String msgClass, String msgRecord, String msgFile) {
        this.fileMaximum = fileMaximum;
        this.classMaximum = classMaximum;
        this.recordMaximum = recordMaximum;
        this.methodMaximum = methodMaximum;
        this.msgMethod = msgMethod;
        this.msgClass = msgClass;
        this.msgRecord = msgRecord;
        this.msgFile = msgFile;
    }

    @Override
    public void process(Pipe<AstEvent> in, Pipe<ViolationMessage> out) {
        while (in.hasNext()) {
            final AstEvent event = in.read();
            if (event == null) {
                break;
            }

            final DetailAST ast = event.getNode();

            if (event.getPhase() == AstEvent.Phase.BEGIN_TREE) {
                counters = new ArrayDeque<>();
                counters.push(new Counter());
            }
            else if (event.getPhase() == AstEvent.Phase.VISIT) {
                final int tokenType = ast.getType();

                if (tokenType == TokenTypes.CLASS_DEF
                    || tokenType == TokenTypes.RECORD_DEF
                    || isMethodOrCtorOrInitDefinition(tokenType)) {
                    counters.push(new Counter());
                }

                if (isCountable(ast)) {
                    counters.forEach(Counter::increment);
                }
            }
            else if (event.getPhase() == AstEvent.Phase.LEAVE) {
                final int tokenType = ast.getType();

                if (isMethodOrCtorOrInitDefinition(tokenType)) {
                    final Counter counter = counters.pop();
                    final int count = counter.getCount();
                    if (count > methodMaximum) {
                        out.write(new ViolationMessage(ast.getLineNo(), ast.getColumnNo(), msgMethod, count, methodMaximum));
                    }
                }
                else if (tokenType == TokenTypes.CLASS_DEF) {
                    final Counter counter = counters.pop();
                    final int count = counter.getCount();
                    if (count > classMaximum) {
                        out.write(new ViolationMessage(ast.getLineNo(), ast.getColumnNo(), msgClass, count, classMaximum));
                    }
                }
                else if (tokenType == TokenTypes.RECORD_DEF) {
                    final Counter counter = counters.pop();
                    final int count = counter.getCount();
                    if (count > recordMaximum) {
                        out.write(new ViolationMessage(ast.getLineNo(), ast.getColumnNo(), msgRecord, count, recordMaximum));
                    }
                }
            }
            else if (event.getPhase() == AstEvent.Phase.FINISH_TREE) {
                final Counter counter = counters.pop();
                final int count = counter.getCount();
                if (count > fileMaximum) {
                    out.write(new ViolationMessage(ast.getLineNo(), ast.getColumnNo(), msgFile, count, fileMaximum));
                }
            }
        }
    }

    private static boolean isCountable(DetailAST ast) {
        boolean countable = true;
        final int tokenType = ast.getType();
        if (tokenType == TokenTypes.EXPR) {
            countable = isExpressionCountable(ast);
        }
        else if (tokenType == TokenTypes.VARIABLE_DEF) {
            countable = isVariableDefCountable(ast);
        }
        return countable;
    }

    private static boolean isVariableDefCountable(DetailAST ast) {
        boolean countable = false;
        final int parentType = ast.getParent().getType();

        if (parentType == TokenTypes.SLIST || parentType == TokenTypes.OBJBLOCK) {
            final DetailAST prevSibling = ast.getPreviousSibling();
            countable = prevSibling == null || prevSibling.getType() != TokenTypes.COMMA;
        }

        return countable;
    }

    private static boolean isExpressionCountable(DetailAST ast) {
        final int parentType = ast.getParent().getType();
        return switch (parentType) {
            case TokenTypes.SLIST, TokenTypes.LABELED_STAT, TokenTypes.LITERAL_FOR,
                 TokenTypes.LITERAL_DO,
                 TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_IF, TokenTypes.LITERAL_ELSE -> {
                final DetailAST prevSibling = ast.getPreviousSibling();
                yield prevSibling == null || prevSibling.getType() != TokenTypes.LPAREN;
            }
            default -> false;
        };
    }

    private static boolean isMethodOrCtorOrInitDefinition(int tokenType) {
        return tokenType == TokenTypes.METHOD_DEF
                || tokenType == TokenTypes.COMPACT_CTOR_DEF
                || tokenType == TokenTypes.CTOR_DEF
                || tokenType == TokenTypes.STATIC_INIT
                || tokenType == TokenTypes.INSTANCE_INIT;
    }

    private static final class Counter {
        private int count;

        void increment() {
            count++;
        }

        int getCount() {
            return count;
        }
    }
}
