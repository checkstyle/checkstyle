//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionPatternsInTernary {
    void m1(Object o) {
        // parenthesized pattern, `PATTERN_DEF`
        int x = o instanceof (String s && s.length() > 6) ? 4 : 5;
        // type pattern, no `PATTERN_DEF`
        int y = o instanceof String s && s.length() > 4 ? 2 : 3;
        // type pattern, no `PATTERN_DEF`
        int z = o instanceof String s ? 1 : 2;
    }
}
