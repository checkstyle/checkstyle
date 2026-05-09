package com.puppycrawl.tools.checkstyle.checks.pipeline.message;

/**
 * Immutable terminal pipeline message. Produced by {@code ThresholdFilter},
 * collected by {@code ViolationSink}, drained by the driver and forwarded to
 * {@code AbstractCheck.log(..)}.
 */
public final class ViolationMessage {

    private final int line;
    private final int col;
    private final String messageKey;
    private final Object[] args;

    public ViolationMessage(int line, int col, String messageKey, Object... args) {
        this.line = line;
        this.col = col;
        this.messageKey = messageKey;
        this.args = args == null ? new Object[0] : args.clone();
    }

    public int getLine() {
        return line;
    }

    public int getCol() {
        return col;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getArgs() {
        return args.clone();
    }
}
