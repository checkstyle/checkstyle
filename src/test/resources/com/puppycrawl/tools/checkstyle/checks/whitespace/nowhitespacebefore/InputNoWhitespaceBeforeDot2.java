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

    public  void testSpaceViolation1() {}  // violation
    public void  testSpaceViolation2() {}  // violation
    public void testSpaceViolation3 () {}  // violation
    public void testSpaceViolation4 ( ) {}  // violation
    public void testSpaceViolation5 ()  {}  // violation
    public void testSpaceViolation6 () { }  // violation
    public void testSpaceViolation7 () {}
}
