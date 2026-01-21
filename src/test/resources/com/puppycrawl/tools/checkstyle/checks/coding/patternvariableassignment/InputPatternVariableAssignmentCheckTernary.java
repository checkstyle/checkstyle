/*
xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheckTernary {
    
    public void testTernary(Object obj) {
        if (obj instanceof Integer x) {
            int y = (x > 10) ? (x = 0) : (x = 1); // violation, "Assignment of pattern variable 'x' is not allowed."
        }
    }
}
