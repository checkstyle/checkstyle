package org.checkstyle.suppressionxpathfilter.coding.nofinalizer;

public enum InputXpathNoFinalizerInEnum {
    VALUE;

    protected void finalize() throws Throwable { // warn
    }
}
