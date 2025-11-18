package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionPatternsInTernary {
    void m1(Object o) {
        // type pattern, no `PATTERN_DEF`
        int y = o instanceof String s && s.length() > 4 ? 2 : 3;
        // type pattern, no `PATTERN_DEF`
        int z = o instanceof String s ? 1 : 2;
    }
}
