/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheck1 {
    public void testAssignment(Object obj) {
        record Rectangle(Object test1, Object test2) {}
        record ColoredPoint(Object test1, Object test2, Object test3) {}

        if (obj instanceof String) {
            System.out.println(obj);
        }
        if (obj instanceof String s) {
            s = "hello"; // violation, "Assignment of pattern variable 's' is not allowed."
            System.out.println(s);
        }
        if (obj instanceof Rectangle(ColoredPoint x, ColoredPoint y)) {
            x = new ColoredPoint(1, 2, "red");
            // violation above, "Assignment of pattern variable 'x' is not allowed."
            y = new ColoredPoint(3, 4, "blue");
            // violation above, "Assignment of pattern variable 'y' is not allowed."
        }
        if (obj instanceof Rectangle(ColoredPoint(Integer x1,Integer x2,String c), Integer x)) {
            c = "red";   // violation, "Assignment of pattern variable 'c' is not allowed."
        }
        if (obj instanceof Rectangle(ColoredPoint(Integer x1, ColoredPoint(Integer y1,Integer y2,
                                                              String d), String c), Integer x)) {
            c = "red";   // violation, "Assignment of pattern variable 'c' is not allowed."
        }
        if (obj instanceof Rectangle(ColoredPoint(Integer x1,Integer x2,String c),
                                     ColoredPoint(Integer y1,Integer y2, String d))) {
            c = "red";   // violation, "Assignment of pattern variable 'c' is not allowed."
        }
        if (obj instanceof String s) {
            String z = "hello";
            z = "bye";
        }
    }
}
