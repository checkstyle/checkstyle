/*
MultipleStringLiterals
allowedDuplicates = 3
ignoreStringsRegexp = (default)^""$
ignoreOccurrenceContext = (default)ANNOTATION


*/

package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

public class InputMultipleStringLiterals5
{   /*string literals*/
    String m = "StringContents";
    String m1 = "SingleString";
    String m2 = "DoubleString" + "DoubleString";
    String m3 = "" + "";
    String m4 = "" + "";
    String debugStr = ", " + ", " + ", ";

    void method1() {
        String a1 = "StringContents";
        System.identityHashCode("StringContents");
        // The following is not reported, since it is two string literals.
        String a2 = "String" + "Contents";
    }

    @SuppressWarnings("unchecked") // violation, 'The String "unchecked" appears 4 times in the file.'
    void method2(){}

    @SuppressWarnings("unchecked")
    void method3(){}

    @SuppressWarnings("unchecked")
    void method4(){}

    @SuppressWarnings("unchecked")
    void method5(){}
}
