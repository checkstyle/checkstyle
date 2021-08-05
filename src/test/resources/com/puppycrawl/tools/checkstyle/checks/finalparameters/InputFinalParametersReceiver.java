/*
FinalParameters
ignorePrimitiveTypes = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersReceiver { // ok
    public void foo4(InputFinalParametersReceiver this) {}

    private class Inner {
        public Inner(InputFinalParametersReceiver InputFinalParametersReceiver.this) {}
    }
}
