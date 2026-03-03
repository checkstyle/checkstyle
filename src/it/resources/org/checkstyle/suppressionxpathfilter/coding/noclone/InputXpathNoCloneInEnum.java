package org.checkstyle.suppressionxpathfilter.coding.noclone;

public enum InputXpathNoCloneInEnum {

    INSTANCE;

    class InnerClass {
        public Object clone() { return null; } // warn
    }

}
