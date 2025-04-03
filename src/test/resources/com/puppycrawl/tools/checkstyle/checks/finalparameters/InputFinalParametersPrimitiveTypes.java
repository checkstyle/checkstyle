/*
FinalParameters
ignorePrimitiveTypes = true
ignoreUnnamedParameters = (default)true
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersPrimitiveTypes
{
    void foo(int i) {} //no warning
    void foo1(int i, String k, float s) {}  // violation, 'k' should be final
    void foo2(String s, Object o, long l) {}
    // 2 violations above:
    //    'Parameter s should be final.'
    //    'Parameter o should be final.'
    void foo3(int[] array) {}  // violation, 'array' should be final
    void foo4(int i, float x, int[] s) {}  // violation, 's' should be final
    void foo5(int x, long[] l, String s) {}
    // 2 violations above:
    //    'Parameter l should be final.'
    //    'Parameter s should be final.'
}
