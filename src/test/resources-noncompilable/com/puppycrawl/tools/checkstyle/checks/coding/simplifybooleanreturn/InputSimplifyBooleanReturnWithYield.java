/*
SimplifyBooleanReturn

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.simplifybooleanreturn;

public class InputSimplifyBooleanReturnWithYield {

    boolean test(int x, boolean c) {
        return switch (x) {
            case 1 -> {
                if (c) { // violation
                    yield true;
                } else {
                    yield false;
                }
            }
            case 2 -> false;
            default -> false;
        };
    }

    boolean test2(int x, boolean c) {
        return switch (x) {
            case 1 -> {
                if (c) yield true; // violation
                else yield false;
            }
            case 2 -> false;
            default -> false;
        };
    }

    boolean test3(int x, boolean c) {
        return switch (x) {
            case 1: {
                if (c) yield true; // violation
                else yield false;
            }
            case 2: {
                yield false;
            }
            default: {
                yield false;
            }
        };
    }

    boolean test4(int x, boolean c) {
        switch (x) {
            case 1: {
                if (c) { // violation
                    return true;
                } else {
                    return false;
                }
            }
            case 2: {
                return false;
            }
            default: {
                return false;
            }
        }
    }

    boolean correct(int x, boolean c) {
        boolean y = switch (x) {
            case 1: {
                yield c;
            }
            case 2: {
                yield false;
            }
            default: {
                yield false;
            }
        };
        return switch (x) {
            case 1 -> {
                yield c;
            }
            case 2 -> false;
            default -> false;
        };
    }
}
