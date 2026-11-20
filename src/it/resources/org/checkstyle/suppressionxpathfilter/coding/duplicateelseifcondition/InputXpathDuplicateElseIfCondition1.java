/*
DuplicateElseIfCondition


*/

package org.checkstyle.suppressionxpathfilter.coding.duplicateelseifcondition;

public class InputXpathDuplicateElseIfCondition1 {
    void test(int x) {
        if (x > 0) {
            // do something
        } else if (x > 0) {
            // violation above "Duplicate condition, identical to the condition at line '11'."
        }
    }
}
