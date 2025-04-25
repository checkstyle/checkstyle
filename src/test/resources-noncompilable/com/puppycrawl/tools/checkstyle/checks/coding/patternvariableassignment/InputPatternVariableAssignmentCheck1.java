/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"></module>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheck1 {
    public void testAssignment(Object obj) {
        if (obj instanceof String s) {
            s = "hello";             // violation
            System.out.println(s);
        }
        if (obj instanceof Rectangle(ColoredPoint x, ColoredPoint y)) {
            x = new ColoredPoint(1, 2, "red");   // violation
        }
        if (obj instanceof Rectangle(ColoredPoint(int x1,int x2,String c), _)) {
            c = "red";   // violation
        }
    }
}
