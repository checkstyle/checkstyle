package org.checkstyle.suppressionxpathfilter.finalparameters;

public class InputXpathFinalParameters3 {

    class AnonymousClass {
        public void method(int argOne, int argTwo) {} // ok, int is primitive
    }

    public void createClass() {
        AnonymousClass obj = new AnonymousClass() {
            public void method(String[] argOne, final String[] argTwo) {} // warn
        };
    }
}
