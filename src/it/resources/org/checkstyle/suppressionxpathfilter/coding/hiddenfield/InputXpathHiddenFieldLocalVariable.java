package org.checkstyle.suppressionxpathfilter.coding.hiddenfield;

public class InputXpathHiddenFieldLocalVariable {
    int count = 10;

    void process() {
        int count = 20; // warn
    }
}
