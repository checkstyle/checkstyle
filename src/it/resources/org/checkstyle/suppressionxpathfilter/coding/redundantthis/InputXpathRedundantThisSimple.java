package org.checkstyle.suppressionxpathfilter.coding.redundantthis;

public class InputXpathRedundantThisSimple {
    private int name;

    public void setName(String value) {
        this.name = value; // warn
    }
}
