//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.grammar.java21;

import java.util.function.Supplier;

public class InputStringTemplateNested {
    int x = 2;
    final String s =
            STR."x\{ sp(() -> {
                return STR."x\{ sp(() -> {
                    return STR."x\{ x }x" + "{" + "}}}";
                }) }x";
            })}x";

    private static String sp(Supplier<String> y) {
        return y.get();
    }
}
