package com.puppycrawl.tools.checkstyle.checks.sizes.pipeline;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Map;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.ViolationMessage;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;
import com.puppycrawl.tools.checkstyle.utils.ScopeUtil;

/**
 * MethodCount maintains a stack of per-scope counters and applies five
 * independent thresholds (private/package/protected/public/total). Because the
 * comparison is inherently per-counter, this filter performs both the
 * measurement and the threshold steps and emits {@code ViolationMessage}s
 * directly. ThresholdFilter is therefore not used in this pipeline.
 */
public final class MethodCountMeasurementFilter implements Filter<AstEvent, ViolationMessage> {

    private final int maxPrivate;
    private final int maxPackage;
    private final int maxProtected;
    private final int maxPublic;
    private final int maxTotal;
    private final String msgPrivate;
    private final String msgPackage;
    private final String msgProtected;
    private final String msgPublic;
    private final String msgTotal;

    private final Deque<Counter> counters = new ArrayDeque<>();

    public MethodCountMeasurementFilter(int maxPrivate, int maxPackage, int maxProtected,
                                        int maxPublic, int maxTotal,
                                        String msgPrivate, String msgPackage,
                                        String msgProtected, String msgPublic, String msgTotal) {
        this.maxPrivate = maxPrivate;
        this.maxPackage = maxPackage;
        this.maxProtected = maxProtected;
        this.maxPublic = maxPublic;
        this.maxTotal = maxTotal;
        this.msgPrivate = msgPrivate;
        this.msgPackage = msgPackage;
        this.msgProtected = msgProtected;
        this.msgPublic = msgPublic;
        this.msgTotal = msgTotal;
    }

    @Override
    public void process(Pipe<AstEvent> in, Pipe<ViolationMessage> out) {
        while (in.hasNext()) {
            final AstEvent event = in.read();
            if (event == null) {
                break;
            }
            final DetailAST ast = event.getNode();
            if (event.getPhase() == AstEvent.Phase.VISIT) {
                if (ast.getType() == TokenTypes.METHOD_DEF) {
                    if (isInLatestScopeDefinition(ast)) {
                        counters.peek().increment(ScopeUtil.getScope(ast));
                    }
                }
                else {
                    counters.push(new Counter(ast));
                }
            }
            else if (event.getPhase() == AstEvent.Phase.LEAVE
                    && ast.getType() != TokenTypes.METHOD_DEF) {
                final Counter counter = counters.pop();
                emitIfExceeded(out, ast, counter.value(Scope.PRIVATE), maxPrivate, msgPrivate);
                emitIfExceeded(out, ast, counter.value(Scope.PACKAGE), maxPackage, msgPackage);
                emitIfExceeded(out, ast, counter.value(Scope.PROTECTED), maxProtected, msgProtected);
                emitIfExceeded(out, ast, counter.value(Scope.PUBLIC), maxPublic, msgPublic);
                emitIfExceeded(out, ast, counter.getTotal(), maxTotal, msgTotal);
            }
        }
    }

    private static void emitIfExceeded(Pipe<ViolationMessage> out, DetailAST ast,
                                       int value, int max, String key) {
        if (max < value) {
            out.write(new ViolationMessage(ast.getLineNo(), ast.getColumnNo(),
                    key, value, max));
        }
    }

    private boolean isInLatestScopeDefinition(DetailAST methodDef) {
        boolean result = false;
        if (!counters.isEmpty()) {
            final DetailAST latest = counters.peek().scopeDefinition;
            result = latest == methodDef.getParent().getParent();
        }
        return result;
    }

    private static final class Counter {
        private final Map<Scope, Integer> counts = new EnumMap<>(Scope.class);
        private final DetailAST scopeDefinition;
        private int total;

        Counter(DetailAST scopeDefinition) {
            this.scopeDefinition = scopeDefinition;
        }

        void increment(Scope scope) {
            total++;
            counts.put(scope, 1 + value(scope));
        }

        int value(Scope scope) {
            final Integer v = counts.get(scope);
            return v == null ? 0 : v;
        }

        int getTotal() {
            return total;
        }
    }
}
