package org.checkstyle.suppressionxpathfilter.nofinalizer;

public class InputXpathNoFinalizer2 {
    public static void doStuff() {
        // some code here
    }

    class InnerClass {

        @Override // warn
        @Deprecated
        protected void finalize() throws Throwable {

        }
    }
}
