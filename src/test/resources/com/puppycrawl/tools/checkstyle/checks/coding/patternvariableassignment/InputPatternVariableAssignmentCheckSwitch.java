/*
xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheckSwitch {
    
    public void testSwitchBodyJ21(Object obj) {
        switch (obj) {
            case String s -> {
                System.out.println(s); 
            }
            case Integer i -> {
                 int x = (i = 50); // violation, "Assignment of pattern variable 'x' is not allowed."
            }
            default -> {}
        }
    }
}
