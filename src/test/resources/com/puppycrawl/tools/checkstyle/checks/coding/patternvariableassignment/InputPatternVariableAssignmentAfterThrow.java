/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentAfterThrow {

    public void testAssignmentBeforeThrow(Object obj) {
        if (obj instanceof String s) {
            s = "modified"; // violation, "Assignment of pattern variable 's' is not allowed."
            throw new RuntimeException(s);
        }
    }

    public void testThrowInNestedBlock(Object obj) {
        if (obj instanceof String s) {
            if (s.isEmpty()) {
                s = "empty"; // violation, "Assignment of pattern variable 's' is not allowed."
                throw new IllegalArgumentException(s);
            }
            s = "not empty"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public void testConditionalThrow(Object obj) {
        if (obj instanceof String s) {
            s = "check"; // violation, "Assignment of pattern variable 's' is not allowed."
            if (s.isEmpty()) {
                throw new IllegalArgumentException("empty");
            }
        }
    }
}
