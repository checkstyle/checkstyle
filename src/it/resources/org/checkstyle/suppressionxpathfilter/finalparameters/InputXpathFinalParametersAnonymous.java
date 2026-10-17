/*
FinalParameters
ignorePrimitiveTypes = true
ignoreUnnamedParameters = (default)true
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.finalparameters;

public class InputXpathFinalParametersAnonymous {

    class AnonymousClass {
        public void method(int argOne, int argTwo) {} // ok, int is primitive
    }

    public void createClass() {
        AnonymousClass obj = new AnonymousClass() {
            public void method(String[] argOne, final String[] argTwo) {}
            // violation above 'Parameter argOne should be final.'
        };
    }
}
