/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.util.function.Consumer;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class InputOneStatementPerLineNestedLambda {
    void cases() {
        // violation below 'Only one statement per line allowed.'
        Consumer<String> c = s -> { int a = 1; int b = 2; };

        Runnable r = () -> { Runnable inner = () -> System.out.println(); int d
                = 0; }; // violation 'Only one statement per line allowed.'


        Runnable anon = new Runnable() {
            int x
                    = 2; public void run() { List.of(1).forEach(i -> { int z = i; }); }
        };
        // violation 2 lines above 'Only one statement per line allowed.'

        int y = 3; Comparator<Integer> comp = (o1, o2) -> { int diff = o1 - o2;
            ;
            return diff;
            };
        // violation above 'Only one statement per line allowed.'

        int u = 0; Runnable complex
                = () -> {  int v = 1;  };
        // violation above 'Only one statement per line allowed.'

        // violation below 'Only one statement per line allowed.'
        Runnable empty = () -> {}; Runnable empty2 = () -> {};

        int[] arr = new int[0]; Runnable complex2
                = () -> {  int w = 1;  };
        // violation above 'Only one statement per line allowed.'
    }

    void method3() {
        int a = 0; for (int i = 0; i < 1; i++) {
            group((Function<Integer, Integer>) x -> switch (x) { default: yield x; },
                 (Function<Integer, Integer>) x -> switch (x) { default: yield x; });
        }
    }


    void group(Function<Integer, Integer> f1, Function<Integer, Integer> f2) {
        // Dummy method to test syntax/indentation
    }

}
