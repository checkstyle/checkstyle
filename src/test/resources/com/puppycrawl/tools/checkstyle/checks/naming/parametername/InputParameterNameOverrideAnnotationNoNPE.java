package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

/* Config:
 *
 * format = "^[a-z][a-zA-Z0-9]*$"
 * ignoreOverridden = true
 */

class InputParameterNameOverrideAnnotationNoNPE
{
    /* method with many parameters*/
    void InputParameterNameOverrideAnnotationNoNPEMethod(int a, int b) { // ok

    }

    /* method with many parameters */
    void InputParameterNameOverrideAnnotationNoNPEMethod2(int a, int b) { // ok

    }
}

class Test extends InputParameterNameOverrideAnnotationNoNPE
{
    @Override
    void InputParameterNameOverrideAnnotationNoNPEMethod(int a, int b) { // ok

    }

    @java.lang.Override
    void InputParameterNameOverrideAnnotationNoNPEMethod2(int a, int b) { // ok

    }
}
