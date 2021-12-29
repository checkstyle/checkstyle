//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionPatternsInWhile {
    void m1(Object o) {
        while (o instanceof (String s && s.length() > 6)) { // parenthesized pattern, `PATTERN_DEF`

        }
        while (o instanceof String s && s.length() > 4) { // type pattern, no `PATTERN_DEF`

        }
        while (o instanceof String s) { // type pattern, no `PATTERN_DEF`

        }

        do {
            // parenthesized pattern, `PATTERN_DEF`
        } while (o instanceof (String s && s.length() > 6));
        do {
            // type pattern, no `PATTERN_DEF`
        } while (o instanceof String s && s.length() > 4);
        do {
            // type pattern, no `PATTERN_DEF`
        } while (o instanceof String s);
    }
}
