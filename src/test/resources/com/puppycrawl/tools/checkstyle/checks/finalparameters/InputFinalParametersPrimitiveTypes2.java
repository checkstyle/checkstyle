/*
FinalParameters
ignorePrimitiveTypes = (default)false
tokens = (default)METHOD_DEF, CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.finalparameters;

public class InputFinalParametersPrimitiveTypes2
{
    void foo(int i) {}  // violation
    void foo1(int i, String k, float s) {} //no warning on 'i' and 's'
    void foo2(String s, Object o, long l) {} //no warning on 'l'
    void foo3(int[] array) {} //warning
    void foo4(int i, float x, int[] s) {} //warning on 's'
    void foo5(int x, long[] l, String s) {} //warning on 'l' and 's'
}
