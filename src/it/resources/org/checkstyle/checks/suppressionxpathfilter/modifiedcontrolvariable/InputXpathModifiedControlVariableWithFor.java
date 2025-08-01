package org.checkstyle.checks.suppressionxpathfilter.modifiedcontrolvariable;

public class InputXpathModifiedControlVariableWithFor {
    public void test() {
        for (int i = 0; i < 1; i++) {
            i++; // warn
        }
    }
}
