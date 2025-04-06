/*
ParameterNumber
max = (default)7
ignoreOverriddenMethods = true
ignoreAnnotatedBy = (default)
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

class InputParameterNumberCheckBase
{
    // method with many parameters
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h) { // violation

    }

    // method with many parameters
    void myMethod2(int a, int b, int c, int d, int e, int f, int g, int h) { // violation

    }
}

public class InputParameterNumber extends InputParameterNumberCheckBase
{
    @Override
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h) {

    }

    @java.lang.Override
    void myMethod2(int a, int b, int c, int d, int e, int f, int g, int h) {

    }
}
