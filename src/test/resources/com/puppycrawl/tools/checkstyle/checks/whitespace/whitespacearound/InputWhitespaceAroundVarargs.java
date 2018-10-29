////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2017
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class InputWhitespaceAroundVarargs
{
    public static void main0(String... args) { }
    public static void main1(String ...args) { }
    public static void main2(String...args) { }
    public static void main3(String ... args) { }
    public void varargs(int...arr) { }
    public void doubleValue(int a, int...b) { }

}
