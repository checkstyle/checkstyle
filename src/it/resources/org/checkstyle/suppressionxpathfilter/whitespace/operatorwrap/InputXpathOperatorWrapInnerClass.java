package org.checkstyle.suppressionxpathfilter.whitespace.operatorwrap;

public class InputXpathOperatorWrapInnerClass {

    class Inner {
        void compute() {
            int y = 5 + // warn
                7;
        }
    }
}
