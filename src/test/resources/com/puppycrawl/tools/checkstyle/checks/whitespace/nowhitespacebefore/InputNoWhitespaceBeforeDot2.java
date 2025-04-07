/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = DOT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBeforeDot2
{
    Boolean equals;

    public void testSpaceViolation() {
        " ".equals("");
        "".equals(" ");
         "".equals(""); // violation
        "" .equals(""); // violation
        "". equals(""); // violation
        "".equals (""); // violation
        "".equals( ""); // violation
        "".equals("" ); // violation
        "".equals("") ; // violation
        equals = " ".equals("");
         equals = "".equals(""); // violation
        equals  = "".equals(""); // violation
        equals =  "".equals(""); // violation
    }
}
