//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.grammar.java19;

public class InputJava19PatternsInNullSwitch1 {

    class Super {}
    class Sub extends Super {}
    record R(Super s) {}

    private int matchingSwitch14(R r) {
        return switch(r) {
            case R(Super s) -> 1;
            default -> 2;
        };
    }

    private int matchingSwitch15(R r) {
        return switch(r) {
            case R(Sub s) -> 1;
            default -> 2;
        };
    }

    private int matchingSwitch1(Object obj) {
        return switch (obj) {
            case String s -> s.length();
            case null, Integer i -> i == null ? -1 : 100 + i;
            default -> -2;
        };
    }

    private int matchingSwitch2(Object obj) {
        return switch (obj) {
            case String s -> 0;
            case null, default -> 1;
        };
    }

    private int matchingSwitch3(Object obj) {
        return switch (obj) {
            case String s -> s.length();
            case Integer i, null -> i == null ? -1 : 100 + i;
            default -> -2;
        };
    }

    private int matchingSwitch4(Object obj) {
        return switch (obj) {
            case String s -> 0;
            case default, null -> 1;
        };
    }

    private int matchingSwitch5(Object obj) {
        return switch (obj) {
            case String s: yield s.length();
            case null:
            case Integer i: yield i == null ? -1 : 100 + i;
            default: yield -2;
        };
    }

    private int matchingSwitch6(Object obj) {
        return switch (obj) {
            case String s: yield 0;
            case null:
            default: yield 1;
        };
    }

    private int matchingSwitch7(Object obj) {
        return switch (obj) {
            case String s: yield s.length();
            case Integer i:
            case null: yield i == null ? -1 : 100 + i;
            default: yield -2;
        };
    }

    private int matchingSwitch8(Object obj) {
        return switch (obj) {
            case String s: yield 0;
            default:
            case null: yield 1;
        };
    }

    private int matchingSwitch9a(Object obj) {
        return switch (obj) {
            case String s: yield 0;
            case null, Object o: yield 1;
        };
    }

    private int matchingSwitch10a(Object obj) {
        switch (obj) {
            case String s: return 0;
            case null, Object o: return 1;
        }
    }
}
