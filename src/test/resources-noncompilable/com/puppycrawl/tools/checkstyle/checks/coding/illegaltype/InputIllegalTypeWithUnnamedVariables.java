/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet, \
                    ColoredPoint, Point
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = (default)
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF, RECORD_PATTERN_DEF


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.util.LinkedHashMap;
import java.util.List;

public class InputIllegalTypeWithUnnamedVariables {

    void testLocalVariables() {
        LinkedHashMap<Integer, Integer> _ = new LinkedHashMap<>(); // ok, can't be used
        // violation below, 'Usage of type 'LinkedHashMap' is not allowed.'
        LinkedHashMap<Integer, Integer> l1 = new LinkedHashMap<>();
    }

    void testPattern(Object obj) {
        // violation below, 'Usage of type 'ColoredPoint' is not allowed.'
        if (obj instanceof ColoredPoint r) { }
        if (obj instanceof ColoredPoint)
        if (obj instanceof ColoredPoint _) { } // ok, can't be used

        switch (obj) {
            // violation below, 'Usage of type 'ColoredPoint' is not allowed.'
            case ColoredPoint r -> {}
            default -> {}
        }

        switch (obj) {
            case ColoredPoint _ -> {}
            default -> {}
        }
    }

    void testRecordPattern(Object obj) {
        // violation below, 'Usage of type 'ColoredPoint' is not allowed.'
        if (obj instanceof Rectangle(ColoredPoint r, _)) { }
        if (obj instanceof Rectangle(ColoredPoint _, _)) {}
        if (obj instanceof ColoredPoint(_, _)) { }
        if (obj instanceof ColoredPoint(Point(int _,int _), _)) { } // ok, all nested are unnamed
        if (obj instanceof ColoredPoint(Point(int _,int y), _)) { }
        // 2 violations above:
        //                  'Usage of type 'ColoredPoint' is not allowed.'
        //                  'Usage of type 'Point' is not allowed.'
        if (obj instanceof ColoredPoint(Point(int _,int _), String c)) { }
        // violation above, 'Usage of type 'ColoredPoint' is not allowed.'

        switch (obj) {
            // violation below, 'Usage of type 'ColoredPoint' is not allowed.'
            case Rectangle(ColoredPoint r, _) -> {}
            default -> {}
        }

        switch (obj) {
            case Rectangle(ColoredPoint _, _) -> {}
            default -> {}
        }

        switch (obj) {
            case ColoredPoint(_, _) -> {}
            default -> {}
        }
    }

    void testEnhancedForLoop() {
        // violation below, 'Usage of type 'Point' is not allowed.'
        List<Point> points = List.of(new Point(1, 2), new Point(3, 4), new Point(5, 6));
        for (Point point: points) {} // violation, 'Usage of type 'Point' is not allowed.'
        for (Point _ : points) {}
    }

    record ColoredPoint(Point p, String c) { }// violation, 'Usage of type 'Point' is not allowed.'
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }
    // 2 violations above:
    //                    'Usage of type 'ColoredPoint' is not allowed.'
    //                    'Usage of type 'ColoredPoint' is not allowed.'
    record Point(int x, int y) { }
}
