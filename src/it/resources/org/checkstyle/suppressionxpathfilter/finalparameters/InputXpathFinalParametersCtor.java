/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = CTOR_DEF


*/

package org.checkstyle.suppressionxpathfilter.finalparameters;

public class InputXpathFinalParametersCtor {

    public InputXpathFinalParametersCtor(int argOne, final int argTwo) { // violation 'Parameter argOne should be final.'
    }
}
