package org.checkstyle.suppressionxpathfilter.coding.noclone;

public interface InputXpathNoCloneInEnum {
    class InnerClass {
        public Object clone() throws CloneNotSupportedException { // warn
            return super.clone();
        }
    }
}
