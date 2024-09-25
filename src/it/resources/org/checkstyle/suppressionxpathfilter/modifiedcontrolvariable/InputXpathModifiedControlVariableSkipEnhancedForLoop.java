package org.checkstyle.suppressionxpathfilter.modifiedcontrolvariable;

public class InputXpathModifiedControlVariableSkipEnhancedForLoop {
    public void test() {
        String[] strings = {"first", "second"};
        for(String s : strings) {
            s += "a"; // ok
        }
        for (int i = 0; i < 1; i++) {
            i++; // warn
        }
    }
}
