package com.puppycrawl.tools.checkstyle.grammar;

public class InputPatternsAnnotationsOnBinding {
        static void m1(Object o) {
        switch (o) {
            case @A Integer i when i >= 0 -> System.out.println(i);
            case @A @B Integer i -> System.out.println(i << 1);
            default -> throw new IllegalStateException(
                    "Unexpected value: " + o);
        }
    }
}

@interface A {}
@interface B {}
