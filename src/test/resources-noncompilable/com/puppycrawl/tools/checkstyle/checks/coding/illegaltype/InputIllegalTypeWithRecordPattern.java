/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = HashMap, HashSet, LinkedHashMap, LinkedHashSet, TreeMap, TreeSet, \
                    java.util.HashMap, java.util.HashSet, java.util.LinkedHashMap, \
                    java.util.LinkedHashSet, java.util.TreeMap, java.util.TreeSet, \
                    Rectangle, ColoredPoint, Point, Box
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class InputIllegalTypeWithRecordPattern {

    record ColoredPoint(Point p, String c) { } // violation, 'Usage of type 'Point' is not allowed.'
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }
    // 2 violations above:
    //                    'Usage of type 'ColoredPoint' is not allowed.'
    //                    'Usage of type 'ColoredPoint' is not allowed.'
    record Point(int x, int y) { }
    record Box<T>(T t) { }


    public void testWithInstanceOf(Object obj) {
        // violation below, 'Usage of type 'Rectangle' is not allowed.'
        if (obj instanceof Rectangle r) { }
        if (obj instanceof Rectangle(ColoredPoint x, ColoredPoint y)) { }
        // 3 violations above:
        //                  'Usage of type 'Rectangle' is not allowed.'
        //                  'Usage of type 'ColoredPoint' is not allowed.'
        //                  'Usage of type 'ColoredPoint' is not allowed.'
        if (obj instanceof Rectangle(ColoredPoint(Point(int _,int _),_),_)) { }
        // 3 violations above:
        //                  'Usage of type 'Rectangle' is not allowed.'
        //                  'Usage of type 'ColoredPoint' is not allowed.'
        //                  'Usage of type 'Point' is not allowed.'
        if (obj instanceof Point(_,_)) { }
        // violation above, 'Usage of type 'Point' is not allowed.'

    }

    public void testWithInstanceOfWithGenerics() {

        // violation below, 'Usage of type 'LinkedHashMap' is not allowed.'
        LinkedHashMap<Integer, Integer> l2
                = new LinkedHashMap<>();

        Box<LinkedHashMap<Integer,Integer>> box = new Box<>(l2);
        // 2 violations above:
        //                  'Usage of type 'Box' is not allowed.'
        //                  'Usage of type 'LinkedHashMap' is not allowed.'

        if (box instanceof Box<LinkedHashMap<Integer,Integer>>(var linkedHashMap)) {}
        // 2 violations above:
        //                  'Usage of type 'Box' is not allowed.'
        //                  'Usage of type 'LinkedHashMap' is not allowed.'

    }

    public void testWithSwitch(Object obj) {
        switch (obj) {
            // violation below,  'Usage of type 'Point' is not allowed.'
            case Point(_,_) -> System.out.println("point");
            case Rectangle(ColoredPoint(Point(_, _),_),_) -> System.out.println("rectangle");
              // 3 violations above:
              //                  'Usage of type 'Rectangle' is not allowed.'
             //                  'Usage of type 'ColoredPoint' is not allowed.'
            //                    'Usage of type 'Point' is not allowed.'
            // violation below,  'Usage of type 'ColoredPoint' is not allowed.'
            case ColoredPoint _ -> System.out.println("coloredPoint");
            default -> throw new IllegalStateException("Unexpected value: " + obj);
        }
    }
}
