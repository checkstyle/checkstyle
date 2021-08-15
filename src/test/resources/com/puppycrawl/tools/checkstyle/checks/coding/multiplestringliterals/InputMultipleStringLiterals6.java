/*
MultipleStringLiterals
allowedDuplicates = (default)1
ignoreStringsRegexp = (default)^""$
ignoreOccurrenceContext = (default)ANNOTATION


*/

package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

public class InputMultipleStringLiterals6
{   /*string literals*/
    String m = "StringContents"; // violation
    String m1 = "SingleString";
    String m2 = "DoubleString" + "DoubleString"; // violation
    String m3 = "" + "";
    String m4 = "" + "";
    String debugStr = ", " + ", " + ", "; // violation

    void method1() {
        String a1 = "StringContents";
        System.identityHashCode("StringContents");
        // The following is not reported, since it is two string literals.
        String a2 = "String" + "Contents";
    }

    @SuppressWarnings("unchecked")
    void method2(){}

    @SuppressWarnings("unchecked")
    void method3(){}

    @SuppressWarnings("unchecked")
    void method4(){}

    @SuppressWarnings("unchecked")
    void method5(){}
}
