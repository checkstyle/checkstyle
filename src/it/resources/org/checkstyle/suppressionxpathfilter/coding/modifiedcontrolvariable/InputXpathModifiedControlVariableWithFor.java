package org.checkstyle.suppressionxpathfilter.coding.modifiedcontrolvariable;

public class InputXpathModifiedControlVariableWithFor {
    public void test() {
        for (int i = 0; i < 1; i++) {
            i++; // warn
        }
    }
}
