/*
DuplicateElseIfCondition


*/

package org.checkstyle.suppressionxpathfilter.coding.duplicateelseifcondition;

public class InputXpathDuplicateElseIfCondition2 {
    void test(int x) {
        if (x == 1) {
            // do something
        } else if (x == 2) {
            // do something
        } else if (x == 1) {
            // violation above "Duplicate condition, identical to the condition at line '11'."
        }
    }
}
