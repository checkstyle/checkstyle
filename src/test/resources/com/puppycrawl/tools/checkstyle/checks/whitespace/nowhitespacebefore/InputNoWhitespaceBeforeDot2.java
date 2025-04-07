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
        "".equals("") ; // violation
        " ".equals("");
        "".equals(" ");
         "".equals(""); // violation
        "" .equals(""); // violation
        "". equals(""); // violation
        "".equals (""); // violation
        "".equals( ""); // violation
        "".equals("" ); // violation
        "".equals(""    ); // violation
        ""  .equals(""); // violation
        "".   equals(""); // violation
        "".equals    (""); // violation
        "".equals(  ""); // violation
        "".equals(""    ); // violation
        equals = " ".equals("");
         equals = "".equals(""); // violation
        equals  = "".equals(""); // violation
        equals =  "".equals(""); // violation
    }

    public void testSpaceViolationTab() {
        "".equals(""    ); // violation
        ""  .equals(""); // violation
        "".   equals(""); // violation
        "".equals    (""); // violation
        "".equals(  ""); // violation
        "".equals(""    ); // violation
    }

    public  void testSpaceViolation1() {}  // violation
    public void  testSpaceViolation2() {}  // violation
    public void testSpaceViolation3  () {}  // violation
    public void testSpaceViolation4 ( ) {}  // violation
    public void testSpaceViolation5 ()  {}  // violation
    public void testSpaceViolation6 () { }  // violation
    public void testSpaceViolation7 () {}

    /**
     * as we check with string contains we must ignore comments
     */
    public void testSpaceViolationComments() {
//        " ".equals("");
//        "".equals(" ");
//        "".equals("");
//        "" .equals("");
//        "". equals("");
//        "".equals ("");
//        "".equals( "");
//        "".equals("" );
//        "".equals("") ;
//        equals = " ".equals("");
//        equals = "".equals("");
//        equals  = "".equals("");
//        equals =  "".equals("");
//    public  void testSpaceViolation1() {}
//    public void  testSpaceViolation2() {}
//    public void testSpaceViolation3  () {}
//    public void testSpaceViolation4 ( ) {}
//    public void testSpaceViolation5 ()  {}
//    public void testSpaceViolation6 () { }
//    public void testSpaceViolation7 () {}
    }


//    public  void testSpaceViolation1() {}
//    public void  testSpaceViolation2() {}
//    public void testSpaceViolation3  () {}
//    public void testSpaceViolation4 ( ) {}
//    public void testSpaceViolation5 ()  {}
//    public void testSpaceViolation6 () { }
//    public void testSpaceViolation7 () {}
}
