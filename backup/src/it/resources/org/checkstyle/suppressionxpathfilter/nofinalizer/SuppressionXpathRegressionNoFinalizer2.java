package org.checkstyle.suppressionxpathfilter.nofinalizer;

public class SuppressionXpathRegressionNoFinalizer2 {
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
