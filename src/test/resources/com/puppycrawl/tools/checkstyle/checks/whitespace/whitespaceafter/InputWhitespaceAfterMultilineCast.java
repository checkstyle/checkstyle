package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

public class InputWhitespaceAfterMultilineCast {
    void issue3850() {
        Object obj = new Object();
        obj = (java.lang.
                Object)obj; // violation
        obj = (java.lang.
                Object) obj; // ok
    }
}
