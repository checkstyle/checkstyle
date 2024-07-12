/*
PatternVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

public class InputPatternVariableNameRecordPattern {

    void test(Object o) {
        if (o instanceof Point(int x, int _)) {}
        if (o instanceof Point(int XX, int __)) {}
        // 2 violations above:
        //                'Name 'XX' must match pattern*.'
        //                'Name '__' must match pattern*.'
        if (o instanceof ColoredPoint(Point(int x, int yy), String color)) {}
        if (o instanceof ColoredPoint(_, String S)) {}
        //violation above, 'Name 'S' must match pattern*.'
    }
    void test2(Object o) {
        switch (o) {
            case Point(int XX, int __) when XX > 0-> {}
            // 2 violations above:
            //                'Name 'XX' must match pattern*.'
            //                'Name '__' must match pattern*.'
            case Point(int x, int _) -> {}
            case ColoredPoint(Point(int x, int yy), String color) -> {}
            case ColoredPoint(_, String S) -> {}
            // violation above, 'Name 'S' must match pattern*.'
            default -> {}
        }
    }

    record Point(int x, int y) { }
    record ColoredPoint(Point point, String color) { }
}
