/*
StringLiteralEquality


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

public class InputStringLiteralEqualityConcatenatedTextBlocks {

    public void testMethod() {
        String s = "abc";
        String p = "asd";
        if (s == """
                a""" + "bc") {
        } // violation 2 lines above 'Literal Strings should be compared using equals(), not '=='.'

        if ("""
                a""" + """
                bc""" == s) {
        } // violation above 'Literal Strings should be compared using equals(), not '=='.'

        if ("a" + ("""
                b""" + """
                c""") != s) {
        } // violation above 'Literal Strings should be compared using equals(), not '!='.'

        if (s == """
                a""" + """
                b""" + """
                c""") {
        } // violation 4 lines above 'Literal Strings should be compared using equals(), not '=='.'
        if ((s += """
                asd""") != p) { // ok, can't be detected as check in not type aware.
        }

        if ((s += "asd") == s + (p + """
                asd""")) {
        } // violation 2 lines above 'Literal Strings should be compared using equals(), not '=='.'

        if ((s += "asd") != s + """
                p""" + p) {
        } // violation 2 lines above 'Literal Strings should be compared using equals(), not '!='.'

        if (s != s + """
                p""" + p) {
        } // violation 2 lines above 'Literal Strings should be compared using equals(), not '!='.'

        String c = ("""
                ab""" + s) != null ?
                (p + """
                        ab""" == null ? p : s) : p;
        // violation 3 lines above 'Literal Strings should be compared using equals(), not '!='.'
        // violation 2 lines above 'Literal Strings should be compared using equals(), not '=='.'
    }

}
