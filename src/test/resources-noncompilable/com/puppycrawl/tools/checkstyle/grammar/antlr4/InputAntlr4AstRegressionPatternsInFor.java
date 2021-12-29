//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionPatternsInFor {
    void m1(Object o) {
        for (int i = 0; o instanceof Integer myInt && myInt > 5;) {
            // type pattern, no `PATTERN_DEF`
        }
        for (int i = 0; o instanceof (Integer myInt && myInt > 5);) {
            // parenthesized pattern, `PATTERN_DEF`
        }
        for (int i = 0; o instanceof Integer myInt; ) {
            // type pattern, no `PATTERN_DEF`
        }
    }
}
