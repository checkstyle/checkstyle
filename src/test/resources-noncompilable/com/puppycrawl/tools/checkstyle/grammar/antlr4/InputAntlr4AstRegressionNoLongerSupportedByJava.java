// non-compiled with javac: Compilable with Java17
// until https://github.com/checkstyle/checkstyle/issues/18146
package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionNoLongerSupportedByJava {
    void m1(Object o) {
        if (o instanceof (String s && s.length() > 4)) { // parenthesized pattern, `PATTERN_DEF`
            // ^ pattern variable introduced in guarded pattern; this refines the pattern itself
            // 's.length() > 4` is part of the pattern
        }
    }

    void m2(Object o) {
        switch(o) {
            case String s && s.length() > 4: // guarded pattern, `PATTERN_DEF`
                break;
            case (String s && s.length() > 6): // parenthesized pattern, `PATTERN_DEF`
                break;
            case null, default:
                throw new UnsupportedOperationException("not supported!");
        }
    }

    void m3(Object o) {
        switch (o) {
            case default:
                throw new UnsupportedOperationException("not supported!");
        }
    }

    void m4(Object o) {
        switch (o) {
            case default -> throw new UnsupportedOperationException("not supported!");
        }
    }

    void m5(Object o) {
        switch (o) {
            case null -> throw new UnsupportedOperationException("not supported!");
            case default -> throw new UnsupportedOperationException("duplicate branch!");
        }
    }

    void m6(Object o) {
        switch (o) {
            case null: throw new UnsupportedOperationException("not supported!");
            case default: throw new UnsupportedOperationException("duplicate branch!");
        }
    }

    void m7(Object o) {
        for (int i = 0; o instanceof (Integer myInt && myInt > 5);) {
            // parenthesized pattern, `PATTERN_DEF`
        }
    }

   void m8() {
        Object value = 42;
        String result = switch (value) {
            case null -> "It's null...";
            case String s -> "It's a string: " + s;
            case Integer i && i > 50 -> "It's an integer: " + i.toString();
            case Integer i && (i > 2) ->
                throw new IllegalStateException("Invalid Integer argument of value " + i);
            case Object v -> "It's something else: " + v.toString();
        };
        System.out.println(result);
    }

    void m9(Object o) {
        // parenthesized pattern, `PATTERN_DEF`
        int x = o instanceof (String s && s.length() > 6) ? 4 : 5;
    }

    void m10(Object o) {
        while (o instanceof (String s && s.length() > 6)) { // parenthesized pattern, `PATTERN_DEF`

        }
        do {
            // parenthesized pattern, `PATTERN_DEF`
        } while (o instanceof (String s && s.length() > 6));
    }
}
