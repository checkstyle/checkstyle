/*
OneStatementPerLine
treatTryResourcesAsStatement = true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.onestatementperline;

import java.util.function.Consumer;
import java.util.Comparator;
import java.util.List;

public class InputOneStatementPerLineNestedLambda {
    void cases() {
        // violation below 'Only one statement per line allowed.'
        Consumer<String> c = s -> { int a = 1; int b = 2; };

        Runnable r = () -> { Runnable inner = () -> System.out.println(); int d
                = 0; }; // violation 'Only one statement per line allowed.'


        Runnable anon = new Runnable() {
            int x
                    = 2; public void run() { List.of(1).forEach(i -> { int z = i; }); }
        }; // 2 violations above:
           // 'Only one statement per line allowed.'
           // 'Only one statement per line allowed.'

        // violation below 'Only one statement per line allowed.'
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
        }
    }
}
