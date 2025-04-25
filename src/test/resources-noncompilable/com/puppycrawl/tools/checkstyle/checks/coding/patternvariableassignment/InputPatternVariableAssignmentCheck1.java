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

    boolean theMatch;

    private static final Object o = "";
    private static final boolean B1s = o instanceof String s;

    record Rectangle(Object test1, Object test2) {}
    record ColoredPoint(Object test1, Object test2, Object test3) {}

    public void testAssignment(Object obj) {
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
        if (obj instanceof Integer d) {
            for (int i = 0; i < 1; i++) {
                if (d > 5) {
                    d -= 3; // violation, "Assignment of pattern variable 'd' is not allowed."
                }
            }
        }
        if (obj instanceof Boolean b) {
            boolean boo = false;
            boo = true;
        }

        if (obj instanceof Integer t);

        if (obj instanceof String s) {
            String z = "hello";
            z = "bye";
        }

        Rectangle antiFigure = obj instanceof Rectangle f
           ? f = null // violation, "Assignment of pattern variable 'f' is not allowed."
           : new Rectangle(40, 40);

        if (obj instanceof String rectName) {
          this.theMatch = testBooleans(obj);
        }

      record ColoredRectangle() {};

      if (obj instanceof ColoredRectangle()) {}

      if (obj instanceof String[] sa) {
          for (int i = 0; i < sa.length; i++) {
              if (sa[i] == null) {
                  sa[i] = sa[i-1];
              }
          }
      }

    }

    public boolean testBooleans(Object obj) {
        boolean typeMatch = obj instanceof ColoredPoint cp;

        if (obj instanceof Boolean bool) {
            return bool;
        }

        return obj instanceof String s;
    }

}
