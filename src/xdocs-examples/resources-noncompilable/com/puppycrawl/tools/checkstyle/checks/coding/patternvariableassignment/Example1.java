/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

// xdoc section -- start
public class Example1 {
    public void testAssignment(Object obj) {
        if (obj instanceof Integer) {
            Integer z = 5;             // ok, 'z' is not a pattern variable
        }
        if (obj instanceof String s) {
            s = "hello";             // violation, "Assignment of pattern variable 's' is not allowed."
            System.out.println(s);
        }
        if (obj instanceof Rectangle(ColoredPoint x, ColoredPoint y)) {
            x = new ColoredPoint(1, 2, "red");   // violation, "Assignment of pattern variable 'x' is not allowed."
        }
        if (obj instanceof Rectangle(ColoredPoint(int x1,int x2,String c), int _)) {
            c = "red";   // violation, "Assignment of pattern variable 'c' is not allowed."
        }
    }
}
// xdoc section -- end
