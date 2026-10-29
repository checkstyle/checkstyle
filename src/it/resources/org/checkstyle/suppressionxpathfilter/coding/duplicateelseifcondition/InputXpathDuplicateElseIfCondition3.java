/*
DuplicateElseIfCondition


*/

package org.checkstyle.suppressionxpathfilter.coding.duplicateelseifcondition;

public class InputXpathDuplicateElseIfCondition3 {
    void test(boolean a, boolean b) {
        if (a && b) {
            // do something
        } else if (a && b) {
            // violation above "Duplicate condition, identical to the condition at line '11'."
        }
    }
}
