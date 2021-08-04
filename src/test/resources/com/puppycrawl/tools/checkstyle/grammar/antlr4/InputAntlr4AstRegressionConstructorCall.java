package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionConstructorCall {
    private int f;

    public InputAntlr4AstRegressionConstructorCall(int i) {
        f = i;
    }

    public InputAntlr4AstRegressionConstructorCall(float f) {
        this((int) f);
    }

}
