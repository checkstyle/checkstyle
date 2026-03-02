/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

// Java21
package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentScopeCleanup {

    String a;
    int number;

    // The extra block ensures the pattern variable is registered in a scope that
    // gets popped, so assignments after the block should not be flagged.
    void method(Object obj) {
        {
            if (obj instanceof String a) {
                // violation below, "Assignment of pattern variable 'a' is not allowed."
                a = "x";
            }
        }
        a = "hello"; // ok, scope containing pattern variable 'a' was popped

        {
            if (obj instanceof Integer number) {
                // violation below, "Assignment of pattern variable 'number' is not allowed."
                number = 42;
            }
        }
        number = 100; // ok, scope containing pattern variable 'number' was popped
    }

    void method2(Object obj) {
        {
            if (obj instanceof String a) {
                // violation below, "Assignment of pattern variable 'a' is not allowed."
                a += " suffix";
            }
        }
        a = "world"; // ok, scope containing pattern variable 'a' was popped
    }
}
