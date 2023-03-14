/*
InnerTypeLast


*/

package com.puppycrawl.tools.checkstyle.checks.design.innertypelast;

public class InputInnerTypeLastArray {
    static class P {}
    int[] x; // violation
    int y[]; // violation
    P p0 = new P(), p1[] = {p0}, p2[][] = {p1}; // violation
    P []p3 = new P[0], p4 = {p0}, p5 = {p0};  // violation
}
