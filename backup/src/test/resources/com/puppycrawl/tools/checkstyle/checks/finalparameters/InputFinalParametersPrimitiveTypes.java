/*
FinalParameters
ignorePrimitiveTypes = true
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersPrimitiveTypes
{
    void foo(int i) {} //no warning
    void foo1(int i, String k, float s) {}  // violation
    void foo2(String s, Object o, long l) {}  // 2 violations
    void foo3(int[] array) {}  // violation
    void foo4(int i, float x, int[] s) {}  // violation
    void foo5(int x, long[] l, String s) {}  // 2 violations
}
