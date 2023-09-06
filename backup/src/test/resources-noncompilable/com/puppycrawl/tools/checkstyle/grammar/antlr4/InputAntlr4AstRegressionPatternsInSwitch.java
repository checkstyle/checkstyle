//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionPatternsInSwitch {
    void m1(Object o) {
        switch(o) {
            case String s && s.length() > 4: // guarded pattern, `PATTERN_DEF`
                break;
            case (String s && s.length() > 6): // parenthesized pattern, `PATTERN_DEF`
                break;
            case String s: // type pattern, no `PATTERN_DEF`
                break;
            case null, default:
                throw new UnsupportedOperationException("not supported!");
        }
    }
}
