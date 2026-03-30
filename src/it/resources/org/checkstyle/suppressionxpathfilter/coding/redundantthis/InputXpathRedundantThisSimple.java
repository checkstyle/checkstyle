package org.checkstyle.suppressionxpathfilter.coding.redundantthis;

public class InputXpathRedundantThisSimple {
    private String name;

    public void setName(String value) {
        this.name = value; // warn
    }
}
