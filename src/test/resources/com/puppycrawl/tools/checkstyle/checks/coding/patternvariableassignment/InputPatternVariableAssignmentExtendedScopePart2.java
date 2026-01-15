/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentExtendedScopePart2 {

    public void testAssignmentInTryCatch(Object obj) {
        if (obj instanceof String s) {
            try {
                s = "try"; // violation, "Assignment of pattern variable 's' is not allowed."
                System.out.println(s);
            } catch (Exception e) {
                s = "catch"; // violation, "Assignment of pattern variable 's' is not allowed."
            }
        }
    }

    record Point(Object x, Object y) {}

    public void testRecordPatternAssignments(Object obj) {
        if (obj instanceof Point(String x, String y)) {
            x = "new x"; // violation, "Assignment of pattern variable 'x' is not allowed."
            y = "new y"; // violation, "Assignment of pattern variable 'y' is not allowed."
            return;
        }
    }

    public void testExpressionSequence(Object obj) {
        if (obj instanceof String s) {
            System.out.println("start");
            s = "modified"; // violation, "Assignment of pattern variable 's' is not allowed."
            System.out.println(s);
            s = "again"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public void testAssignmentInInnerBlock(Object obj) {
        if (obj instanceof String s) {
            {
                s = "inner"; // violation, "Assignment of pattern variable 's' is not allowed."
            }
            s = "outer"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public int testReturnAsSibling(Object obj) {
        if (obj instanceof Integer i) {
            i = 100; // violation, "Assignment of pattern variable 'i' is not allowed."
            return i;
        }
        return 0;
    }

    public String testMultipleReturns(Object obj) {
        if (obj instanceof String s) {
            if (s.isEmpty()) {
                s = "empty"; // violation, "Assignment of pattern variable 's' is not allowed."
                return s;
            }
            s = "nonempty"; // violation, "Assignment of pattern variable 's' is not allowed."
            return s;
        }
        return null;
    }

}
