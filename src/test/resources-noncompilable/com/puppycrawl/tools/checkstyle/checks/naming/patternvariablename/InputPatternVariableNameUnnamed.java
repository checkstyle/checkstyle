/*
PatternVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

public class InputPatternVariableNameUnnamed {

    void test(Object o, Object obj) {
        if (o instanceof String _) { }
        if (o instanceof String) { }
        // violation below, 'Name '__' must match pattern*.'
        if (o instanceof String __ && __.length() == 0) { }
        // violation below, 'Name '_s' must match pattern*.'
        if (o instanceof String _s) { }

        // violation below, 'Name '__' must match pattern*.'
        if (o instanceof String __ &&
                        obj instanceof Integer _) { }
    }

    void test2(Object o) {
        switch (o) {
            // violation below, 'Name '__' must match pattern*.'
            case String __ when __.length() == 0 -> {}
            case Integer _ -> {}
            // violation below, 'Name '_s' must match pattern*.'
            case Double _s -> {}
            default -> {}
        }
    }

    void test3(Object o, Object obj) {
        if (o instanceof Point(int _, int _)) { }
          // violation below, 'Name '_Color' must match pattern*.'
        if (o instanceof ColoredPoint(Point(int _, int x), String _Color)) { }

        switch (o) {
            case Point(int _, int _) -> {}
              // violation below, 'Name '_Color' must match pattern*.'
            case ColoredPoint(Point(int _, int x), String _Color) -> {}
            default -> {}
        }

        boolean b = o instanceof Point(int _, int y)
                  // violation below, 'Name '__' must match pattern*.'
                && obj instanceof ColoredPoint(Point(int _, int x), String __);
    }

    record Point(int x, int y) { }
    record ColoredPoint(Point point, String color) { }
}
