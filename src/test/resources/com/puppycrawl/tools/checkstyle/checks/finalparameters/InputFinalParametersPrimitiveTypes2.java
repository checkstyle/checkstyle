/*
FinalParameters
ignorePrimitiveTypes = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersPrimitiveTypes2
{
    void foo(int i) {}  // violation
    void foo1(int i, String k, float s) {} // 3 violations
    void foo2(String s, Object o, long l) {} // 3 violations
    void foo3(int[] array) {} // violation
    void foo4(int i, float x, int[] s) {} // 3 violations
    void foo5(int x, long[] l, String s) {} // 3 violations
}
