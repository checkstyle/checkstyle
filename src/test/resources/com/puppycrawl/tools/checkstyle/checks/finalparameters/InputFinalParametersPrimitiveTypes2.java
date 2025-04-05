/*
FinalParameters
ignorePrimitiveTypes = (default)false
ignoreUnnamedParameters = (default)true
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersPrimitiveTypes2
{
    void foo(int i) {}  // violation, 'i' should be final
    void foo1(int i, String k, float s) {}
    // 3 violations above:
    //    'Parameter i should be final.'
    //    'Parameter k should be final.'
    //    'Parameter s should be final.'
    void foo2(String s, Object o, long l) {}
    // 3 violations above:
    //    'Parameter s should be final.'
    //    'Parameter o should be final.'
    //    'Parameter l should be final.'
    void foo3(int[] array) {} // violation, 'array' should be final
    void foo4(int i, float x, int[] s) {}
    // 3 violations above:
    //    'Parameter i should be final.'
    //    'Parameter x should be final.'
    //    'Parameter s should be final.'
    void foo5(int x, long[] l, String s) {}
    // 3 violations above:
    //    'Parameter x should be final.'
    //    'Parameter l should be final.'
    //    'Parameter s should be final.'
}
