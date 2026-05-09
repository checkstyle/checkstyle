package com.puppycrawl.tools.checkstyle.checks.pipeline.message;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Immutable pipeline message carrying an AST node and the traversal phase in
 * which it was observed.
 */
public final class AstEvent {

    public enum Phase { BEGIN_TREE, VISIT, LEAVE, FINISH_TREE }

    private final DetailAST node;
    private final Phase phase;

    public AstEvent(DetailAST node, Phase phase) {
        this.node = node;
        this.phase = phase;
    }

    public DetailAST getNode() {
        return node;
    }

    public Phase getPhase() {
        return phase;
    }
}
