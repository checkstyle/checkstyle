/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentExtendedScope {

    public String testAssignmentBeforeReturn(Object obj) {
        if (obj instanceof String s) {
            s = "modified"; // violation "Assignment of pattern variable 's' is not allowed."
            return s;
        }
        return "default";
    }

    public void testAssignments(Object obj) {
        if (obj instanceof String s) {
            s = "first"; // violation "Assignment of pattern variable 's' is not allowed."
            s = "second"; // violation "Assignment of pattern variable 's' is not allowed."
        }
        if (obj instanceof String s) {
            s = "error"; // violation "Assignment of pattern variable 's' is not allowed."
            throw new RuntimeException(s);
        }
        if (obj instanceof Integer i) {
            while (i > 0) {
                i = i - 1; // violation "Assignment of pattern variable 'i' is not allowed."
            }
        }
        if (obj instanceof Integer i) {
            i += 10; // violation "Assignment of pattern variable 'i' is not allowed."
        }
    }

    public void testNestedAndSwitch(Object obj) {
        if (obj instanceof String s) {
            if (s.length() > 10) {
                s = "long"; // violation "Assignment of pattern variable 's' is not allowed."
                return;
            }
            s = "short"; // violation "Assignment of pattern variable 's' is not allowed."
        }
        if (obj instanceof Integer i) {
            switch (i) {
                case 1:
                    i = 10; // violation "Assignment of pattern variable 'i' is not allowed."
                    break;
                default:
                    i = 0; // violation "Assignment of pattern variable 'i' is not allowed."
            }
        }
    }

    public void testTryCatchAndRecord(Object obj) {
        if (obj instanceof String s) {
            try {
                s = "try"; // violation "Assignment of pattern variable 's' is not allowed."
            } catch (Exception e) {
                s = "catch"; // violation "Assignment of pattern variable 's' is not allowed."
            }
        }
        if (obj instanceof Point(String x,String y)) {
            x = "new x"; // violation "Assignment of pattern variable 'x' is not allowed."
            y = "new y"; // violation "Assignment of pattern variable 'y' is not allowed."
        }
    }

    record Point(Object x, Object y) { }

}
