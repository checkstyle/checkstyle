/*
UnnecessaryTypeArgumentsWithRecordPattern

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarytypeargumentswithrecordpattern;

public class InputUnnecessaryTypeArgumentsWithRecordPatternMultiple {

    record Pair<T, U>(T t, U u) {}

    void testInstanceof(Pair<String, Integer> pair) {
        // violation below 'Unnecessary type arguments with record pattern.'
        if (pair instanceof Pair<String, Integer>(var a, var b)) {
            System.out.println(a + " " + b);
        }

        if (pair instanceof Pair<?, ?>(var a, var b)) {
            System.out.println(a + " " + b);
        }
        // violation below 'Unnecessary type arguments with record pattern.'
        if (pair instanceof Pair<String, ?>(var a, var b)) {
            System.out.println(a + " " + b);
        }
        // violation below 'Unnecessary type arguments with record pattern.'
        if (pair instanceof Pair<?, Integer>(var a, var b)) {
            System.out.println(a + " " + b);
        }
    }

    void testSwitch(Pair<String, Integer> pair) {
        switch (pair) {
            // violation below 'Unnecessary type arguments with record pattern.'
            case Pair<String, Integer>(var a, var b) ->
                System.out.println(a + " " + b);
            case Pair<?, ?>(var a, var b) ->
                System.out.println(a + " " + b);
            default -> {}
        }
    }

    record Triple<T, U, V>(T t, U u, V v) {}

    void testInstanceof1(Triple<String, Integer, String> triple) {
        // violation below 'Unnecessary type arguments with record pattern.'
        if (pair instanceof Triple<?, ?, String>(var a, var b, var c)) {
            System.out.println(a + " " + b + " " + c);
        }
    }
}
