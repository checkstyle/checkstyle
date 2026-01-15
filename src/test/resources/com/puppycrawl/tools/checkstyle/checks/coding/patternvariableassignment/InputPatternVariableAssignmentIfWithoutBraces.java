/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentIfWithoutBraces {

    public void testIfWithBracesContainingLoop(Object obj) {
        if (obj instanceof Integer i) {
            for (int j = 0; j < 5; j++)
                i = j; // violation, "Assignment of pattern variable 'i' is not allowed."
        }
    }

    public void testIfWithBracesContainingWhile(Object obj) {
        if (obj instanceof Integer i) {
            while (i > 0)
                i = i - 1; // violation, "Assignment of pattern variable 'i' is not allowed."
        }
    }

    public void testNestedIfWithBraces(Object obj) {
        if (obj instanceof String s) {
            if (s.isEmpty())
                s = "empty"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public void testElseIfWithBraces(Object obj) {
        if (obj == null) {
            System.out.println("null");
        }
        else if (obj instanceof Integer i) {
            i = 5; // violation, "Assignment of pattern variable 'i' is not allowed."
        }
    }

    record Point(Object x, Object y) {}

    public void testRecordPatternWithBraces(Object obj) {
        if (obj instanceof Point(String x, String y)) {
            x = "new"; // violation, "Assignment of pattern variable 'x' is not allowed."
        }
    }

    public void testMultipleStatementsWithBraces(Object obj) {
        if (obj instanceof String s) {
            s = "first"; // violation, "Assignment of pattern variable 's' is not allowed."
            System.out.println(s);
            s = "second"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public void testDoWhileWithBraces(Object obj) {
        if (obj instanceof Integer i) {
            do {
                i = 10; // violation, "Assignment of pattern variable 'i' is not allowed."
            } while (i < 5);
        }
    }

    public void testSwitchWithBraces(Object obj) {
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

    public void testTryCatchWithBraces(Object obj) {
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
