//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.grammar.java19;

import java.util.Objects;
import java.util.function.Function;

public class InputGenericRecordDeconstructionPattern {

    void m1(Function<Box<String>, Integer> test) {
        Box<String> b = new Box<>(null);
    }

    int m2(Box<String> b) {
        if (b instanceof Box<String>(String s)) return 1;
        if (b instanceof Box<String>(String s)
            && b.v != null
            && b.v() != null
            && "whatever".equals(b.v)) return 1;
        return -1;
    }

    int m3(Box<String> b) {
        switch (b) {
            case Box<String>(String s): return 1;
            default: return -1;
        }
    }

    int m4(Box<String> b) {
        return switch (b) {
            case Box<String>(String s) -> 1;
            default -> -1;
        };
    }

    int m5(Box<Box<String>> b) {
        return switch (b) {
            case Box<Box<String>>(Box<String>(String s)box)
                    when "test".equals(s) && box.x != 7  -> 1;
            case Box<Box<String>>(Box<String>(String s)box)
                    when "test".equals(s) && (int) box.x != 7  -> 1;
            case Box<Box<String>>(Box<String>(String s)box)
                    when "test".equals(s) && Objects.equals(box.v, "box") -> 1;
            case Box<Box<String>>(Box<String>(String s)box)
                    when "test".equals(s) && Objects.equals(box.v, "box")
                        || "something else".equals(s) -> 1;
            case Box<Box<String>>(Box<String>(String s))
                    when "test".equals(s) -> 1;
            case Box<Box<String>>(Box<?> b2)
                    when "test".equals(b2.v) -> 1;
            case Box<Box<String>>(Object o) b2
                    when b2.v != null && "whatever".equals(o.toString())-> 1;
            default -> -1;
        };
    }

    record Box<V>(V v) {
        static int x = 5;
    }

}
