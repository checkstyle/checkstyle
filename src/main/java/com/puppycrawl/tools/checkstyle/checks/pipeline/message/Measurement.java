package com.puppycrawl.tools.checkstyle.checks.pipeline.message;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Immutable measurement emitted by a per-check measurement filter. Consumed by
 * {@code ThresholdFilter} which compares {@link #getValue()} against the
 * configured ceiling and converts the message to a {@code ViolationMessage}.
 */
public final class Measurement {

    private final DetailAST subject;
    private final int lineNo;
    private final int colNo;
    private final long value;
    private final String messageKey;
    private final Object[] args;

    public Measurement(DetailAST subject, int lineNo, int colNo,
                       long value, String messageKey, Object... args) {
        this.subject = subject;
        this.lineNo = lineNo;
        this.colNo = colNo;
        this.value = value;
        this.messageKey = messageKey;
        this.args = args == null ? new Object[0] : args.clone();
    }

    public DetailAST getSubject() {
        return subject;
    }

    public int getLineNo() {
        return lineNo;
    }

    public int getColNo() {
        return colNo;
    }

    public long getValue() {
        return value;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public Object[] getArgs() {
        return args.clone();
    }
}
