/*
ParameterNumber
max = (default)7
ignoreAnnotatedBy = (default)
ignoreOverriddenMethods = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

class InputParameterNumberCheckBase2
{
    // violation below, 'More than 7 parameters (found 8).'
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h) {

    }

    // violation below, 'More than 7 parameters (found 8).'
    void myMethod2(int a, int b, int c, int d, int e, int f, int g, int h) {

    }
}

public class InputParameterNumber2 extends InputParameterNumberCheckBase
{
    @Override
    void myMethod(int a, int b, int c, int d, int e, int f, int g, int h) {

    } // violation 2 lines above 'More than 7 parameters (found 8).'

    @java.lang.Override
    void myMethod2(int a, int b, int c, int d, int e, int f, int g, int h) {

    } // violation 2 lines above 'More than 7 parameters (found 8).'
}
