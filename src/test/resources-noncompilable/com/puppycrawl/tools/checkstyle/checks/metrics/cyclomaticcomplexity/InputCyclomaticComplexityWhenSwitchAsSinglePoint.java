/*
CyclomaticComplexity
max = 0
switchBlockAsSingleDecisionPoint = true

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

public class InputCyclomaticComplexityWhenSwitchAsSinglePoint {

    // violation below, 'Cyclomatic Complexity is 5 (max allowed is 0)'
    void testIf(Object o) {   // 1, function declaration
        if (o instanceof Integer i && i == 9) {}  // 2, if - 3, &&
        else if (o instanceof String s && s.length() == 9) {} // 4, if - 5, &&
    }

    // violation below, 'Cyclomatic Complexity is 4 (max allowed is 0)'
    void testSwitch(Object o) {   // 1, function declaration
        switch (o) {  // 2, switch
            case Integer i when i == 9 -> {} // 3, when
            case String s when s.length() == 9 -> {} // 4, when
            default -> {}
        }
    }

    // violation below, 'Cyclomatic Complexity is 5 (max allowed is 0)'
    void testSwitch2(Object o) {  // 1, function declaration
        switch (o) {    // 2 switch
            case Integer i -> {}
            case Point(int x, int y) when (x == 0 && y == 0) -> {} // 3, when - 4, &&
            case Point(int x, int y) when x == 0 -> {} // 5, when
            default -> {}
        }
    }

    // violation below, 'Cyclomatic Complexity is 5 (max allowed is 0)'
    void testSwitch3(Object o) {  // 1, function declaration
        switch (o) {    // 2 switch
            case Integer i -> {}
            case Point(int x, int y) -> {
                if (x == 0 && y == 0) { // 3, if - 4, &&

                }
            }
            case Point2(int x) -> {
                if (x == 0) { // 5 if

                }
            }
            default -> {}
        }
    }

    record Point(int x, int y) { }

    record Point2(int x) { }
}
