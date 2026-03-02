package org.checkstyle.suppressionxpathfilter.coding.noclone;

public enum InputXpathNoCloneInEnum {
    VALUE;

    public Object clone() throws CloneNotSupportedException { // warn
        return this;
    }
}
