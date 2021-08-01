/*
WhitespaceAround
allowEmptyConstructors = (default)false
allowEmptyMethods = (default)false
allowEmptyTypes = (default)false
allowEmptyLoops = (default)false
allowEmptyLambdas = (default)false
allowEmptyCatches = (default)false
ignoreEnhancedForColon = (default)true
tokens = ELLIPSIS


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

class InputWhitespaceAroundVarargs
{
    public static void main0(String... args) { } // violation
    public static void main1(String ...args) { }
    public static void main2(String...args) { }
    public static void main3(String ... args) { }
    public void varargs(int...arr) { }
    public void doubleValue(int a, int...b) { }

}
