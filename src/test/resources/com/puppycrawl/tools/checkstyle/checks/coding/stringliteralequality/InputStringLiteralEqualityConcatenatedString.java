/*
StringLiteralEquality


*/

package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

public class InputStringLiteralEqualityConcatenatedString {

    public void testMethod() {
        String s = "abc";
        String p = "asd";
        if (s == "a" + "bc") { // violation
        }

        if ("a" + "bc" == s) { // violation
        }

        if ("a" + ("b" + "c") != s) { // violation
        }

        if (s == "a" + "b" + "c") { // violation
        }
        if ((s += "asd") != p) { // ok, can't be detected as check in not type aware.
        }

        if ((s += "asd") == s + (p + "asd")) { // violation
        }

        if ((s += "asd") != s + "p" + p) { // violation
        }

    }

}
