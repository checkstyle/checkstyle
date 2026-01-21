/*
xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheckMultipleAssignments {
    
    public void testBypass(Object obj) {
        if (obj instanceof Integer x) {
            x *= 2; // violation, "Assignment of pattern variable 'x' is not allowed."
            x /= 2; // violation, "Assignment of pattern variable 'x' is not allowed."
            x = 10; // violation, "Assignment of pattern variable 'x' is not allowed."
        }
    }
}
