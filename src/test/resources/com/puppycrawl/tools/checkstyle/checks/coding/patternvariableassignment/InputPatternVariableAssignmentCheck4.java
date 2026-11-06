/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="PatternVariableAssignment"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.patternvariableassignment;

public class InputPatternVariableAssignmentCheck4 {

    record Point(Integer x, Integer y) {}

    void testIncrementDecrement(Object obj, int[] arr) {
        if (obj instanceof Integer i) {
            i++; // violation "Assignment of pattern variable 'i' is not allowed."
        }
        if (obj instanceof Integer i) {
            ++i; // violation "Assignment of pattern variable 'i' is not allowed."
        }
        if (obj instanceof Integer i) {
            i--; // violation "Assignment of pattern variable 'i' is not allowed."
        }
        if (obj instanceof Integer i) {
            --i; // violation "Assignment of pattern variable 'i' is not allowed."
        }
        if (obj instanceof Integer i) {
            i = 10; // violation "Assignment of pattern variable 'i' is not allowed."
        }
        if (obj instanceof Integer i) {
            i += 1; // violation "Assignment of pattern variable 'i' is not allowed."
        }
        if (obj instanceof Integer i) {
            int local = 0;
            local++;
            ++local;
            local--;
            --local;
            local = 10;
            local += 1;
            arr[i]++;
            ++arr[i];
            arr[i]--;
            --arr[i];
            int read = i + 1;
        }
        if (obj instanceof Point(Integer x, Integer y)) {
            x++; // violation "Assignment of pattern variable 'x' is not allowed."
            --y; // violation "Assignment of pattern variable 'y' is not allowed."
        }
    }
}
