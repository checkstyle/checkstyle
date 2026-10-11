package org.checkstyle.suppressionxpathfilter.coding.modifiedcontrolvariable;

public class InputXpathModifiedControlVariableNestedWithFor {
    void test(){
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                j *= 2; // warn
            }
        }
    }
}
