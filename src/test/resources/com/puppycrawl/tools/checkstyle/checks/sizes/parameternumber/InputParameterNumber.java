/*
ParameterNumber
max = (default)7
ignoreOverriddenMethods = true
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

class InputParameterNumberCheckBase
{
    // violation below, 'More than 7 parameters (found 8).'
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h) {

    }

    // violation below, 'More than 7 parameters (found 8).'
    void myMethod2(int a, int b, int c, int d, int e, int f, int g, int h) {

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
