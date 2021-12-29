//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionPatternsInIfStatement {
    void m1(Object o) {
        if (o instanceof String s) { // type pattern, no `PATTERN_DEF`
        }

        if (o instanceof String s && s.length() > 6) { // type pattern, no `PATTERN_DEF`
            // ^ pattern variable introduced (simple type pattern),
            // then evaluated in boolean expression
            // 's.length() > 6' is usual boolean expression, not part of pattern
        }

        if (o instanceof (String s && s.length() > 4)) { // parenthesized pattern, `PATTERN_DEF`
            // ^ pattern variable introduced in guarded pattern; this refines the pattern itself
            // 's.length() > 4` is part of the pattern
        }
    }
}
