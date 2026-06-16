/*
StringLiteralEquality


*/

package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

public class InputStringLiteralEqualityNewString {

    public void test() {
        String literal = "hello";

        // violation below 'Literal Strings should be compared using equals(), not '==''
        boolean r1 = (new String("x") == "x");
        // violation below 'Literal Strings should be compared using equals(), not '==''
        boolean r2 = ("x" == new String("x"));
        // violation below 'Literal Strings should be compared using equals(), not '!=''
        boolean r3 = (new String("x") != "x");
        // violation below 'Literal Strings should be compared using equals(), not '==''
        boolean r4 = ("hello" == "world");
        // violation below 'Literal Strings should be compared using equals(), not '==''
        boolean r5 = (new String("a") == new String("b"));

        // no violations expected below
        boolean r6 = (literal.equals("test"));
        boolean r7 = ("hello".equals(literal));
    }
}
