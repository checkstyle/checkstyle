package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

class InputParameterNameOverrideAnnotationNoNPE
{
    // method with many parameters
    void InputParameterNameOverrideAnnotationNoNPEMethod(int a, int b) {

    }

    // method with many parameters
    void InputParameterNameOverrideAnnotationNoNPEMethod2(int a, int b) {

    }
}

class Test extends InputParameterNameOverrideAnnotationNoNPE
{
    @Override
    void InputParameterNameOverrideAnnotationNoNPEMethod(int a, int b) {

    }

    @java.lang.Override
    void InputParameterNameOverrideAnnotationNoNPEMethod2(int a, int b) {

    }
}
