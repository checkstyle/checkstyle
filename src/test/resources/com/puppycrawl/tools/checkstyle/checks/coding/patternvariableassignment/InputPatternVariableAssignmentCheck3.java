/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheck3 {

    void foo(Object object, String[] sessions) {
        if (object instanceof Integer integer1) {
            sessions[integer1] = null; // ok, pattern variable used as array index, not assigned
        }

        if (object instanceof Integer integer2) {
            sessions[integer2] += "x"; // ok, pattern variable used as array index, not assigned
        }

        if (object instanceof Integer integer3) {
            integer3 = 5; // violation, "Assignment of pattern variable 'integer3' is not allowed."
        }

        if (object instanceof String[] arr) {
            arr[0] = "value"; // ok, array element assignment does not re-assign pattern variable
        }

        if (object instanceof String[] arr2) {
            arr2 = new String[5];
            // violation above, "Assignment of pattern variable 'arr2' is not allowed."
        }
    }
}
