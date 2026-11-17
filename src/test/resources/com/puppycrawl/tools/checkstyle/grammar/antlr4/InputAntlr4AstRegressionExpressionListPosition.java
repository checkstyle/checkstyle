package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionExpressionListPosition {

    void wrappedArguments(Exception ex) {
        throw new IllegalStateException("Wrapped"
            + " message " + ex.getMessage(), null
        );
    }

    void methodCalls(String text, int number) {
        text.concat(text
            .trim());
        String.valueOf(number
            + 1);
        for (int index = 0; index < number; index++, number
            --) {
            text.isEmpty();
        }
    }

}
