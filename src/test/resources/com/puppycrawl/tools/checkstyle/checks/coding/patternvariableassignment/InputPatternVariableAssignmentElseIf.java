/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentElseIf {

    public void testElseIfWithAssignment(Object obj) {
        if (obj instanceof Integer i) {
            i = 10; // violation, "Assignment of pattern variable 'i' is not allowed."
        }
        else if (obj instanceof String s) {
            s = "test"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
        else if (obj instanceof Double d) {
            d = 3.14; // violation, "Assignment of pattern variable 'd' is not allowed."
        }
    }

    public void testNestedElseIf(Object obj) {
        if (obj instanceof Integer i) {
            if (i > 0) {
                i = 5; // violation, "Assignment of pattern variable 'i' is not allowed."
            }
            else if (i < 0) {
                i = -5; // violation, "Assignment of pattern variable 'i' is not allowed."
            }
        }
    }

    public void testElseIfWithoutAssignment(Object obj) {
        if (obj instanceof Integer i) {
            System.out.println(i);
        }
        else if (obj instanceof String s) {
            System.out.println(s);
        }
    }
}
