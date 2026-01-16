/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentAfterReturn {

    public String testAssignmentBeforeReturn(Object obj) {
        if (obj instanceof String s) {
            s = "modified"; // violation, "Assignment of pattern variable 's' is not allowed."
            return s;
        }
        return "default";
    }

    public void testReturnInNestedBlock(Object obj) {
        if (obj instanceof String s) {
            if (s.isEmpty()) {
                s = "empty"; // violation, "Assignment of pattern variable 's' is not allowed."
                return;
            }
            s = "not empty"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public void testMultipleReturns(Object obj) {
        if (obj instanceof String s) {
            if (s.length() > 10) {
                s = "long"; // violation, "Assignment of pattern variable 's' is not allowed."
                return;
            }
            s = "short"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }
}
