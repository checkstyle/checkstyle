/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentExtendedScope2 {

    record Point(Object x, Object y) { }

    public void testMisc(Object obj) {
        if (obj instanceof String s) {
            {
                s = "inner"; // violation, "Assignment of pattern variable 's' is not allowed."
            }
            s = "outer"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
        if (obj instanceof String s)
            s = "a"; // violation, "Assignment of pattern variable 's' is not allowed."
    }

    public void testTernary(Object obj) {
        String s2 = (obj instanceof String s)
            ? (s = "a") // violation, "Assignment of pattern variable 's' is not allowed."
            : "b";
    }

    public void testExtendedScope(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        System.out.println(s);
    }

    public String testReturnWithAssignment(Object obj) {
        if (!(obj instanceof String s)) {
            return "";
        }
        return s = "a"; // violation, "Assignment of pattern variable 's' is not allowed."
    }

    public void testMultipleExtendedScopeStatements(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        s = "first"; // violation, "Assignment of pattern variable 's' is not allowed."
        s = "second"; // violation, "Assignment of pattern variable 's' is not allowed."
    }

    public void testUnbracedElseWhile(Object obj) {
        if (obj == null) { }
        else
            while (obj instanceof String s) {
                s = "a"; // violation, "Assignment of pattern variable 's' is not allowed."
            }
    }

    public void testExtendedScopeWithOtherStatements(Object obj) {
        if (!(obj instanceof String s)) {
            return;
        }
        int x = 0;
        s = "a";
        if (true) {
            s = "b";
        }
    }
}
