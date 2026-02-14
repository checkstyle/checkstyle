/*
StringLiteralEquality


*/

package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

public class InputStringLiteralEqualityConcatenatedString {

    public void testMethod() {
        String s = "abc";
        String p = "asd";
        if (s == "a" + "bc") { // violation 'Literal Strings should be compared using equals(), not '=='.'
        }

        if ("a" + "bc" == s) { // violation 'Literal Strings should be compared using equals(), not '=='.'
        }

        if ("a" + ("b" + "c") != s) { // violation 'Literal Strings should be compared using equals(), not '!='.'
        }

        if (s == "a" + "b" + "c") { // violation 'Literal Strings should be compared using equals(), not '=='.'
        }
        if ((s += "asd") != p) { // ok, can't be detected as check in not type aware.
        }

        if ((s += "asd") == s + (p + "asd")) { // violation 'Literal Strings should be compared using equals(), not '=='.'
        }

        if ((s += "asd") != s + "p" + p) { // violation 'Literal Strings should be compared using equals(), not '!='.'
        }

        if (s != s + "p" + p) { // violation 'Literal Strings should be compared using equals(), not '!='.'
        }

        String a = (s + "asd") == null ? "asd" : s; // violation 'Literal Strings should be compared using equals(), not '=='.'

        String b = s + "ab" + p != null ? s : p; // violation 'Literal Strings should be compared using equals(), not '!='.'

        String c = ("ab" + s) != null ? // violation 'Literal Strings should be compared using equals(), not '!='.'
                (p + "ab" == null ? p : s) : p; // violation 'Literal Strings should be compared using equals(), not '=='.'

    }

}
