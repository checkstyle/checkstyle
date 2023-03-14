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

        if (s != s + "p" + p) { // violation
        }

        String a = (s + "asd") == null ? "asd" : s; // violation

        String b = s + "ab" + p != null ? s : p; // violation

        String c = ("ab" + s) != null ? // violation
                (p + "ab" == null ? p : s) : p; // violation

    }

}
