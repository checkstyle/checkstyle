package com.puppycrawl.tools.checkstyle.checks.naming;

class InputOverrideAnnotationNoNPE
{
    // method with many parameters
    void myMethod(int a, int b) {

    }

    // method with many parameters
    void myMethod2(int a, int b) {

    }
}

class Test extends InputOverrideAnnotationNoNPE
{
    @Override
    void myMethod(int a, int b) {

    }

    @java.lang.Override
    void myMethod2(int a, int b) {

    }
}
