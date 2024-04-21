package org.checkstyle.suppressionxpathfilter.nofinalizer;

public class InputXpathNoFinalizerInner {
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
