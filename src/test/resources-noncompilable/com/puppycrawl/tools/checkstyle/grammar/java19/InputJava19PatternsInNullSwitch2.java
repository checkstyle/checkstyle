//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.grammar.java19;

public class InputJava19PatternsInNullSwitch2 {

    class Super {}
    class Sub extends Super {}
    record R(Super s) {}

    private int matchingSwitch9b(Object obj) {
        try {
            return switch (obj) {
                case String s: yield 0;
                case Object o: yield 1;
            };
        } catch (NullPointerException ex) {
            return 2;
        }
    }

    private int matchingSwitch10b(Object obj) {
        try {
            switch (obj) {
                case String s: return 0;
                case Object o: return 1;
            }
        } catch (NullPointerException ex) {
            return 2;
        }
    }

    private int matchingSwitch11(Object obj) {
        try {
            return switch (obj) {
                case String s: yield 0;
                default: yield 1;
            };
        } catch (NullPointerException ex) {
            return 2;
        }
    }

    private int matchingSwitch12(Object obj) {
        try {
            switch (obj) {
                case String s: return 0;
                default: return 1;
            }
        } catch (NullPointerException ex) {
            return 2;
        }
    }

    private int matchingSwitch13(Object obj) {
        try {
            switch (obj) {
                default: return 1;
                case String s: return 0;
            }
        } catch (NullPointerException ex) {
            return 2;
        }
    }
}
