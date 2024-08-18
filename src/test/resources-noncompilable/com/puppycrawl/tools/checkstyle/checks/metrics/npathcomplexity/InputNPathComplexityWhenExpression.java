/*
NPathComplexity
max = 1


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityWhenExpression {

    // violation below, 'NPath Complexity is 3 (max allowed is 1)'
    void m(Object o) {

        if (o instanceof String s && !s.isEmpty()) { }
    }

    // violation below, 'NPath Complexity is 3 (max allowed is 1)'
    void m2(Object o) {
        switch (o) {
            case String s when !s.isEmpty() -> { }
            default -> { }
        }
    }

    // violation below, 'NPath Complexity is 3 (max allowed is 1)'
    void m3(Object o) {
        switch (o) {
            case String s when (!s.isEmpty()) -> { }
            default -> { }
        }
    }

    // violation below, 'NPath Complexity is 4 (max allowed is 1)'
    void m4(Object o, boolean b) {
        switch (o) {
            case String s when !s.isEmpty() && b -> { }
            default -> { }
        }
    }

    // violation below, 'NPath Complexity is 4 (max allowed is 1)'
    void m5(Object o, boolean b) {
        switch (o) {
            case String s when (!s.isEmpty() && b) -> { }
            default -> { }
        }
    }

    // violation below, 'NPath Complexity is 5 (max allowed is 1)'
    void m6(Object o, boolean b, boolean c) {
        switch (o) {
            case String s when (!s.isEmpty() && b) && c -> { }
            default -> { }
        }
    }

    // violation below, 'NPath Complexity is 7 (max allowed is 1)'
    void m7(Object o, boolean b, boolean c) {
        switch (o) {
            case String s when !s.isEmpty() -> { }
            case Integer i when (i == 0) && c || b -> { }
            default -> { }
        }
    }

    // violation below, 'NPath Complexity is 5 (max allowed is 1)'
    void m8(Object o, boolean b, boolean c) {
        switch (o) {
            case String s when (b ? true : c) -> { }
            default -> { }
        }
    }

    // violation below, 'NPath Complexity is 5 (max allowed is 1)'
    void m9(Object o, boolean b, boolean c) {
        switch (o) {
            case String s when b ? true : c -> { }
            default -> { }
        }
    }

    // violation below, 'NPath Complexity is 6 (max allowed is 1)'
    void m10(Object o, boolean b, boolean c) {
        switch (o) {
            case String s when (b ? true : c) && c == true -> { }
            default -> { }
        }
    }
}
