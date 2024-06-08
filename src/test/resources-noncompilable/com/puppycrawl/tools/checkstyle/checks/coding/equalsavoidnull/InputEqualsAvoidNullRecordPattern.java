/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullRecordPattern {

    record ColoredPoint(Point p, String c) { }

    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }

    record Point(int x, int y) { }


    void test(Object obj) {
        if (obj instanceof ColoredPoint(Point p, String c)) {
            System.out.println(c.equals("yellow")); // violation 'left .* of .* equals'
            if (p.toString().equals("point")) {
                System.out.println("point");
            }
        }
        if (obj instanceof Rectangle(ColoredPoint(_,String x),
                                     ColoredPoint(Point(int a, int b), String y))) {
            boolean bb = x.equals("yellow")   // violation 'left .* of .* equals'
                    || y.equals("blue");     // violation 'left .* of .* equals'
            boolean c = x.equals("yellow") && y.equals("blue"); // 2 violations
        }

    }

}
