/*
FinalParameters
ignorePrimitiveTypes = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersPrimitiveTypes2
{
    void foo(int i) {}  // violation, 'i' should be final
    void foo1(int i, String k, float s) {}
    // 3 violations above
    //'i' should be final
    //'k' should be final
    //'s' should be final
    void foo2(String s, Object o, long l) {}
    // 3 violations above
    //'s' should be final
    //'o' should be final
    //'l' should be final
    void foo3(int[] array) {} // violation, 'array' should be final
    void foo4(int i, float x, int[] s) {}
    // 3 violations above
    //'i' should be final
    //'x' should be final
    //'s' should be final
    void foo5(int x, long[] l, String s) {}
    // 3 violations above
    //'x' should be final
    //'l' should be final
    //'s' should be final
}
