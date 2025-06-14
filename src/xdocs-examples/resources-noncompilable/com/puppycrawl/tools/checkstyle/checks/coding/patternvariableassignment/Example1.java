/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

// xdoc section -- start
public class Example1 {
  public void testAssignment(Object obj) {
    record Rectangle(Object test1, Object test2) {}
    record ColoredPoint(Object test1, Object test2, Object test3) {}

    if (obj instanceof Integer) {
      Integer z = 5; // ok, 'z' is not a pattern variable
    }
    if (obj instanceof String s) {
      s = "hello"; // violation, "Assignment of pattern variable 's' is not allowed."
      System.out.println(s);
    }
    if (obj instanceof Rectangle(ColoredPoint x, ColoredPoint y)) {
      x = new ColoredPoint(1, 2, "red");
      // violation above, "Assignment of pattern variable 'x' is not allowed."
    }
    if (obj instanceof Rectangle(ColoredPoint(Integer x1,Integer x2,String c),
                                 Integer _)) {
      c = "red"; // violation, "Assignment of pattern variable 'c' is not allowed."
    }
  }
}
// xdoc section -- end
