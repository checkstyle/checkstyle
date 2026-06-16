/*
 * InputStringLiteralEqualityNewString - tests for false negative
 * when new String(...) is compared with == or != against a string literal.
 * Issue: https://github.com/checkstyle/checkstyle/issues/19475
 */
package com.puppycrawl.tools.checkstyle.checks.coding.stringliteralequality;

public class InputStringLiteralEqualityNewString {

    public void test() {
        String literal = "hello";
        String newStr  = new String("hello");

        boolean r1 = (newStr == literal);            // violation
        boolean r2 = (literal == newStr);            // violation
        boolean r3 = (new String("x") == "x");      // violation
        boolean r4 = (new String("x") != "x");      // violation
        boolean r5 = ("hello" == "world");           // violation (pre-existing)

        // OK - no violations expected:
        boolean r6 = (newStr.equals(literal));
        boolean r7 = ("hello".equals(newStr));
    }
}
EOF