package org.checkstyle.suppressionxpathfilter.nofinalizer;

public class InputXpathNoFinalizer1 {
    public static void main(String[] args) {
        // some code here
    }

    protected void finalize() throws Throwable { // warn

    }
}
