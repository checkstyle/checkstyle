/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentSingleStmt {

    record Point(Object x, Object y) {}

    public void testSingleStatementInBlock(Object obj) {
        if (obj instanceof String s) {
            s = "test"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public void testNestedIfWithAssignment(Object obj) {
        if (obj instanceof String s) {
            if (s.length() > 0) {
                s = "modified"; // violation, "Assignment of pattern variable 's' is not allowed."
            }
        }
    }

    public void testDoWhileWithAssignment(Object obj) {
        if (obj instanceof Integer i) {
            do {
                i = 10; // violation, "Assignment of pattern variable 'i' is not allowed."
            } while (i < 5);
        }
    }

    public void testForLoopWithAssignment(Object obj) {
        if (obj instanceof Integer i) {
            for (int j = 0; j < 5; j++) {
                i = j; // violation, "Assignment of pattern variable 'i' is not allowed."
            }
        }
    }

    public void testSwitchWithAssignment(Object obj) {
        if (obj instanceof Integer i) {
            switch (i) {
                case 1:
                    i = 10; // violation, "Assignment of pattern variable 'i' is not allowed."
                    break;
                default:
                    i = 0; // violation, "Assignment of pattern variable 'i' is not allowed."
            }
        }
    }

    public void testTryCatchWithAssignment(Object obj) {
        if (obj instanceof String s) {
            try {
                s = "try"; // violation, "Assignment of pattern variable 's' is not allowed."
                System.out.println(s);
            } catch (Exception e) {
                s = "catch"; // violation, "Assignment of pattern variable 's' is not allowed."
            }
        }
    }
}
