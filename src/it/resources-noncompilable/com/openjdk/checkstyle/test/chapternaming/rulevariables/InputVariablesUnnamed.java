// non-compiled with javac: Compilable with Java25

package com.openjdk.checkstyle.test.chapternaming.rulevariables;

// violation first line 'Header mismatch'

import java.util.function.Function;

public class InputVariablesUnnamed {

    public void method1() {
        int value = 10;
        final int finalValue = 20;
        System.out.println(value);
        System.out.println(finalValue);
        int _ = 10;
        // violation below 'Redundant 'final' modifier.'
        final int _ = 20;
        Function<Integer, Integer> func = _ -> 42;
        System.out.println(func.apply(value));
        try {
            System.out.println("hello");
        } catch (Exception _) {
            System.out.println("ignored");
        }
    }

    public void method2(Object obj) {
        if (obj instanceof String _) {
            System.out.println("string");
        }
    }

}
