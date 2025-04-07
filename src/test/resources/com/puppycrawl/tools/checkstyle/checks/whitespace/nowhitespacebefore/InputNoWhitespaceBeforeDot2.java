/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = DOT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBeforeDot2
{
    Boolean equals;

    public void testSpaceViolation2() {
         "".equals(""); // violation
        "" .equals(""); // violation
        "". equals(""); // violation
        "".equals (""); // violation
        "".equals( ""); // violation
        "".equals("" ); // violation
        "".equals("") ; // violation
         equals = "".equals(""); // violation
        equals  = "".equals(""); // violation
        equals =  "".equals(""); // violation
    }

    public void testSpaceViolation22() {
        " ".equals("");
        "".equals(" ");
        equals = "".equals("");
    }
}
