/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentExtendedScope {

    public String testAssignmentBeforeReturn(Object obj) {
        if (obj instanceof String s) {
            s = "modified"; // violation, "Assignment of pattern variable 's' is not allowed."
            return s;
        }
        return "default";
    }

    public void testMultipleExpressionsInBlock(Object obj) {
        if (obj instanceof String s) {
            s = "first"; // violation, "Assignment of pattern variable 's' is not allowed."
            System.out.println(s);
            s = "second"; // violation, "Assignment of pattern variable 's' is not allowed."
            System.out.println(s);
        }
    }

    public void testAssignmentThenReturn(Object obj) {
        if (obj instanceof Integer i) {
            i = 10; // violation, "Assignment of pattern variable 'i' is not allowed."
            return;
        }
    }

    public void testNestedBlocksWithReturn(Object obj) {
        if (obj instanceof String s) {
            if (s.length() > 10) {
                s = "long"; // violation, "Assignment of pattern variable 's' is not allowed."
                return;
            }
            s = "short"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public void testAssignmentBeforeThrow(Object obj) {
        if (obj instanceof String s) {
            s = "error"; // violation, "Assignment of pattern variable 's' is not allowed."
            throw new RuntimeException(s);
        }
    }

    public void testSemicolonHandling(Object obj) {
        if (obj instanceof Integer i) {
            ;
            i = 5; // violation, "Assignment of pattern variable 'i' is not allowed."
            ;
            ;
            i = 10; // violation, "Assignment of pattern variable 'i' is not allowed."
        }
    }

    public void testCompoundAssignments(Object obj) {
        if (obj instanceof Integer i) {
            i += 10; // violation, "Assignment of pattern variable 'i' is not allowed."
            i *= 2; // violation, "Assignment of pattern variable 'i' is not allowed."
            i -= 5; // violation, "Assignment of pattern variable 'i' is not allowed."
        }
    }

    public void testNestedIfAssignment(Object obj) {
        if (obj instanceof String s) {
            if (s.isEmpty()) {
                s = "empty"; // violation, "Assignment of pattern variable 's' is not allowed."
            }
        }
    }

    public void testAssignmentInWhileLoop(Object obj) {
        if (obj instanceof Integer i) {
            while (i > 0) {
                i = i - 1; // violation, "Assignment of pattern variable 'i' is not allowed."
            }
        }
    }

    public void testAssignmentInForLoop(Object obj) {
        if (obj instanceof Integer i) {
            for (int j = 0; j < 5; j++) {
                i = j; // violation, "Assignment of pattern variable 'i' is not allowed."
            }
        }
    }

    public void testAssignmentInSwitch(Object obj) {
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

}
