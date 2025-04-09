/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = DOT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBefore22 {

    Boolean equals;

    public void testSpaceViolationMethodCall() {
        " ".equals("");
        "".equals(" ");
         "".equals(""); // violation is preceded with whitespace.
        "" .equals(""); // violation is preceded with whitespace.
        "". equals(""); // violation is preceded with whitespace.
        "".equals (""); // violation is preceded with whitespace.
        "".equals( ""); // violation is preceded with whitespace.
        "".equals("" ); // violation is preceded with whitespace.
        "".equals("") ; // violation is preceded with whitespace.
    }

    public void testSpaceViolationVarAssignment() {
        equals = " ".equals("");
         equals = "".equals(""); // violation is preceded with whitespace.
        equals  = "".equals(""); // violation is preceded with whitespace.
        equals =  "".equals(""); // violation is preceded with whitespace.
    }

    public void testSpaceViolationVarDeclaration() {
        boolean e = "".equals("");
         boolean e3 = "".equals(""); // violation is preceded with whitespace.
        boolean e1  = "".equals(""); // violation is preceded with whitespace.
        boolean  e2 = "".equals(""); // violation is preceded with whitespace.
         e3 = "".equals(""); // violation is preceded with whitespace.
        e3  = "".equals(""); // violation is preceded with whitespace.
        e3 =  "".equals(""); // violation is preceded with whitespace.
        e3 = "".equals("");
    }

    public void testSpaceViolationTab() {
        "".equals(""); // violation is preceded with whitespace. TODO insert tap char
        "".equals(""); // violation is preceded with whitespace.
        "".equals(""); // violation is preceded with whitespace.
        "".equals(""); // violation is preceded with whitespace.
        "".equals(""); // violation is preceded with whitespace.
        "".equals(""); // violation is preceded with whitespace.
    }

    public void testSpaceViolation0() {
    }

     public void testSpaceViolation1() {
    }  // violation is preceded with whitespace.

    public  void testSpaceViolation2() {
    }  // violation is preceded with whitespace.

    public void  testSpaceViolation3() {
    }  // violation is preceded with whitespace.

    public void testSpaceViolation4 () {
    }  // violation is preceded with whitespace.

    public void testSpaceViolation5( ) {
    }  // violation is preceded with whitespace.

    public void testSpaceViolation6()  {
    }  // violation is preceded with whitespace.

    public void testSpaceViolation7() {
     }  // violation is preceded with whitespace.

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
