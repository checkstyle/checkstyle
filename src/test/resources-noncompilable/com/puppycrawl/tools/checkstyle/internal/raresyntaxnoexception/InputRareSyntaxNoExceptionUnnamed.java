// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

import java.util.List;

public class InputRareSyntaxNoExceptionUnnamed {
    public void run() {
        try {
            throw new IllegalStateException("boom");
        }
        catch (IllegalStateException _) {
            System.out.println("caught");
        }
        List<String> values = List.of("a", "b");
        values.forEach(_ -> System.out.println("item"));
        for (String _ : values) {
            System.out.println("loop");
        }
        Object value = "text";
        if (value instanceof String _) {
            System.out.println("pattern");
        }

    }
}
