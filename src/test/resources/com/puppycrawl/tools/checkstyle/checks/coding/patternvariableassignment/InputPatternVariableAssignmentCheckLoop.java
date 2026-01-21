/*
xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheckLoop {
    
    public void testLoop(Object obj) {
        while (obj instanceof Integer x) {
            x = 5; // violation, "Assignment of pattern variable 'x' is not allowed."
            break;
        }
    }
}
