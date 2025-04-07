/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = DOT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBeforeDot2
{
    public void testSpaceViolation2() {
         "".equals(""); // violation
        "" .equals(""); // violation
        "". equals(""); // violation
        "".equals (""); // violation
        "".equals( ""); // violation
        "".equals("" ); // violation
        "".equals("") ; // violation
        Object b;
         b = "".equals(""); // violation
        b  = "".equals(""); // violation
        b =  "".equals(""); // violation
        " ".equals("");
        "".equals(" ");
    }
}
