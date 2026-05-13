/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;
public class InputPatternVariableAssignmentCheck3 {
    void foo(Object object, String[] sessions) {
        if (object instanceof Integer integer1) {
            sessions[integer1] = null;
        }
        if (object instanceof Integer integer2) {
            sessions[integer2] += "x";
        }
        if (object instanceof Integer integer3) {
            integer3 = 5; // violation, "Assignment of pattern variable 'integer3' is not allowed."
        }
        if (object instanceof String[] arr) {
            arr[0] = "value";
        }
        if (object instanceof String[] arr2) {
            arr2 = new String[5];
            // violation above, "Assignment of pattern variable 'arr2' is not allowed."
        }
        if (object instanceof int[] arr3) {
            arr3[0] = 1;
        }
        if (object instanceof String s) {
            System.out.println(s);
            System.out.println("test");
            s = "hello"; // violation, "Assignment of pattern variable 's' is not allowed."
        }
    }
}
