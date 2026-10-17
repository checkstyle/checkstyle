/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.finalparameters;

public class InputXpathFinalParametersMethod {

    public void method(int argOne, final int argTwo) {
        // violation above 'Parameter argOne should be final.'
    }
}
