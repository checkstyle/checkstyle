package org.checkstyle.suppressionxpathfilter.coding.nofinalizer;

public interface InputXpathNoFinalizerInEnum {
    class InnerClass {
        protected void finalize() throws Throwable { // warn
        }
    }
}
