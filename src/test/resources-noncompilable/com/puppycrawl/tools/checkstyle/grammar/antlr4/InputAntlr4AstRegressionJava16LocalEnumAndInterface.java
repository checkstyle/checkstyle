//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionJava16LocalEnumAndInterface {
    void m1() {
        strictfp enum E {a, b, c};
        enum X {a, b, c};
    }

    void m2() {
        strictfp interface I1{}
        interface I2{}
    }
}
