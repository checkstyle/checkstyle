/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentMultipleStmts {

    public void testMultipleAssignmentsInBlock(Object obj) {
        if (obj instanceof String s) {
            s = "first"; // violation, "Assignment of pattern variable 's' is not allowed."
            System.out.println(s);
            s = "second"; // violation, "Assignment of pattern variable 's' is not allowed."
            System.out.println(s);
            s = "third"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public void testMultipleStatementsBeforeAssignment(Object obj) {
        if (obj instanceof String s) {
            System.out.println("start");
            int x = 5;
            String y = "hello";
            s = "modified"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }

    public void testAssignmentInNestedBlocks(Object obj) {
        if (obj instanceof String s) {
            {
                s = "inner block";
                // violation above, "Assignment of pattern variable 's' is not allowed."
            }
            s = "outer block"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }
}
