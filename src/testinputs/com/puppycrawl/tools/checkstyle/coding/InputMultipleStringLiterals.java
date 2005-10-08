package com.puppycrawl.tools.checkstyle.coding;

public class InputMultipleStringLiterals
{
    String m = "StringContents";
    String m1 = "SingleString";
    String m2 = "DoubleString" + "DoubleString";
    String m3 = "" + "";
    String m4 = "" + "";
    String debugStr = ", " + ", " + ", ";

    void method1() {
        String a1 = "StringContents";
        System.out.println("StringContents");
        // The following is not reported, since it is two string literals.
        String a2 = "String" + "Contents";
    }
}
