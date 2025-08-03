package org.checkstyle.checks.suppressionxpathfilter.finalparameters;

public class InputXpathFinalParametersAnonymous {

    class AnonymousClass {
        public void method(int argOne, int argTwo) {} // ok, int is primitive
    }

    public void createClass() {
        AnonymousClass obj = new AnonymousClass() {
            public void method(String[] argOne, final String[] argTwo) {} // warn
        };
    }
}
