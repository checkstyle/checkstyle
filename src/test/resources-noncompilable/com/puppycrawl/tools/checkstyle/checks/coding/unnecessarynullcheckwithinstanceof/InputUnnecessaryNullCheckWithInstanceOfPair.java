/*
UnnecessaryNullCheckWithInstanceOf

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

record Pair<T, U>(T first, U second) {}

public class InputUnnecessaryNullCheckWithInstanceOfPair {

    public void basicIfStatements(Object obj) {
        // violation below, 'Unnecessary nullity check'
        if (obj instanceof Pair(String s, String t) && obj != null) {
            Pair<String, String> pairObj = (Pair<String, String>) obj;
            String firstValue = s;
            String secondValue = t;
        }
    }

    void method(Object o) {
        switch (o) {
            case String s when o != null -> {
                System.out.println(s + "is a string");
            }
            default -> {}
        }
    }
}
