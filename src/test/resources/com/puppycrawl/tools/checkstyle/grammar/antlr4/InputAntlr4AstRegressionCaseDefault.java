package com.puppycrawl.tools.checkstyle.grammar.antlr4;


public class InputAntlr4AstRegressionCaseDefault {
    void m1(Object o) {
        switch (o) {
            case String s when s.length() > 4: // guarded pattern, `PATTERN_DEF`
                break;
            case String s: // type pattern, no `PATTERN_DEF`
                break;
            default:
                throw new UnsupportedOperationException("not supported!");
        }
    }

    void m2(Object o) {
        switch (o) {
            case String s when s.length() > 4 ->
                    System.out.println("guarded pattern, `PATTERN_DEF`");
            case String s -> System.out.println("type pattern, no `PATTERN_DEF`");
            default -> throw new UnsupportedOperationException("not supported!");
        }
    }

    void m3(Object o) {
        switch (o) {
            case String s when s.length() > 4 ->
                    System.out.println("guarded pattern, `PATTERN_DEF`");
            case String s -> System.out.println("type pattern, no `PATTERN_DEF`");
            case null -> throw new UnsupportedOperationException("not supported!");
            default -> throw new UnsupportedOperationException("duplicate branch!");
        }
    }

    void m4(Object o) {
        switch (o) {
            case String s when s.length() > 4:
                    System.out.println("guarded pattern, `PATTERN_DEF`");
                    break;
            case String s: System.out.println("type pattern, no `PATTERN_DEF`");
                break;
            case null: throw new UnsupportedOperationException("not supported!");
            default: throw new UnsupportedOperationException("duplicate branch!");
        }
    }
}
