package org.checkstyle.suppressionxpathfilter.coding.illegaltoken;

public enum InputXpathIllegalTokenPostIncrement {
    VALUE;

    void method() {
        int i = 0;
        i++; // warn
    }
}
