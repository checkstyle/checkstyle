/*
InnerTypeLast


*/

package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastArray {
    static class P {}
    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    int[] x;
    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    int y[];
    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    P p0 = new P(), p1[] = {p0}, p2[][] = {p1};
    // violation below 'Init blocks, constructors, fields and methods should be before inner types.'
    P []p3 = new P[0], p4 = {p0}, p5 = {p0};
}
