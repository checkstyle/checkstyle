/*
StringLiteralEquality


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

public class InputStringLiteralEqualityConcatenatedTextBlocks {

    public void testMethod() {
        String s = "abc";
        String p = "asd";
        if (s == """
                a""" + "bc") { // violation above
        }

        if ("""
                a""" + """
                bc""" == s) { // violation
        }

        if ("a" + ("""
                b""" + """
                c""") != s) { // violation
        }

        if (s == """
                a""" + """
                b""" + """
                c""") { // violation 3 lines above
        }
        if ((s += """
                asd""") != p) { // ok, can't be detected as check in not type aware.
        }

        if ((s += "asd") == s + (p + """
                asd""")) { // violation above
        }

        if ((s += "asd") != s + """
                p""" + p) { // violation above
        }

        if (s != s + """
                p""" + p) { // violation above
        }

        String c = ("""
                ab""" + s) != null ? // violation
                (p + """
                        ab""" == null ? p : s) : p; // violation

    }

}
