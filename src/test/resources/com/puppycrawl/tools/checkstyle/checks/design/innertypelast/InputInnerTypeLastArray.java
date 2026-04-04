/*
InnerTypeLast


*/

package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastArray {
    static class P {}
    int[] x; // violation 'Init blocks, constructors, fields and methods should be before inner types.'
    int y[]; // violation 'Init blocks, constructors, fields and methods should be before inner types.'
    P p0 = new P(), p1[] = {p0}, p2[][] = {p1}; // violation 'Init blocks, constructors, fields and methods should be before inner types.'
    P []p3 = new P[0], p4 = {p0}, p5 = {p0};  // violation 'Init blocks, constructors, fields and methods should be before inner types.'
}
