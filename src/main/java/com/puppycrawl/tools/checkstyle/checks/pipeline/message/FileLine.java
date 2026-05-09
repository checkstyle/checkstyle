package com.puppycrawl.tools.checkstyle.checks.pipeline.message;

/** Immutable per-line message used by file-level pipelines. */
public final class FileLine {

    private final int lineNo;
    private final String text;

    public FileLine(int lineNo, String text) {
        this.lineNo = lineNo;
        this.text = text;
    }

    public int getLineNo() {
        return lineNo;
    }

    public String getText() {
        return text;
    }
}
