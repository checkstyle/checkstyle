package com.puppycrawl.tools.checkstyle.grammar;

public class InputAstRegressionExpressionListPosition {
    void report(Exception cause) {
        throw new IllegalStateException("Wrapped"
                + " message " + cause.getMessage(), cause);
    }

    void arguments(int value) {
        consume(value
                + 1);
        consume(
                /* first argument */ value
                        + 2);
        consume((value + 3)
                * 4);
        consume(value + 5);
        consume();
    }

    void consume(int... values) { }
}
