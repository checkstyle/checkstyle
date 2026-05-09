package com.puppycrawl.tools.checkstyle.checks.pipeline.filter;

import java.util.Arrays;
import java.util.BitSet;

import com.puppycrawl.tools.checkstyle.checks.pipeline.Filter;
import com.puppycrawl.tools.checkstyle.checks.pipeline.message.AstEvent;
import com.puppycrawl.tools.checkstyle.checks.pipeline.pipe.Pipe;

/**
 * Forwards {@link AstEvent} messages whose AST node type is in the configured
 * set. {@code BEGIN_TREE} and {@code FINISH_TREE} phases pass through unchanged
 * so downstream measurement filters can perform per-file initialisation and
 * flushing.
 */
public final class TokenFilter implements Filter<AstEvent, AstEvent> {

    private final BitSet allowed;

    public TokenFilter(int... tokenTypes) {
        this.allowed = new BitSet();
        Arrays.stream(tokenTypes).forEach(allowed::set);
    }

    @Override
    public void process(Pipe<AstEvent> in, Pipe<AstEvent> out) {
        while (in.hasNext()) {
            final AstEvent event = in.read();
            if (event == null) {
                break;
            }
            final AstEvent.Phase phase = event.getPhase();
            final boolean keep = phase == AstEvent.Phase.BEGIN_TREE
                    || phase == AstEvent.Phase.FINISH_TREE
                    || (event.getNode() != null
                        && allowed.get(event.getNode().getType()));
            if (keep) {
                out.write(event);
            }
        }
    }
}
