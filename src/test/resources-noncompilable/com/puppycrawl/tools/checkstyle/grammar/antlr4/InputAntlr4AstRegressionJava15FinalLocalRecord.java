//non-compiled with javac: Compilable with Java15
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionJava15FinalLocalRecord {
    void method() {
        final record MyFinalRecord (int x) { }
    }
}
