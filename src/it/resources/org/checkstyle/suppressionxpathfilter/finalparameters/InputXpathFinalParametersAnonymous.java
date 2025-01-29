package org.checkstyle.suppressionxpathfilter.finalparameters;

public class InputXpathFinalParametersAnonymous {

    class AnonymousClass {
        public void method(int argOne, int argTwo) {} //int is primitive
    }

    public void createClass() {
        AnonymousClass obj = new AnonymousClass() {
            public void method(String[] argOne, final String[] argTwo) {} // warn
        };
    }
}
