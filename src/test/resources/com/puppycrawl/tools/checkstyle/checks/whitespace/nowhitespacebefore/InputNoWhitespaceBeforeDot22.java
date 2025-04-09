/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = DOT


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

class InputNoWhitespaceBefore22 {

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
    }

    public void testSpaceViolationVarAssignment() {
        equals = " ".equals("");
         equals = "".equals(""); // violation
        equals  = "".equals(""); // violation
        equals =  "".equals(""); // violation
    }

    public void testSpaceViolationVarDeclaration() {
        boolean e = "".equals("");
         boolean e3 = "".equals(""); // violation
        boolean e1  = "".equals(""); // violation
        boolean  e2 = "".equals(""); // violation
         e3 = "".equals(""); // violation
        e3  = "".equals(""); // violation
        e3 =  "".equals(""); // violation
        e3 = "".equals("");
    }

    public void testSpaceViolationTab() {
        "".equals(""); // violation TODO insert tap char
        "".equals(""); // violation
        "".equals(""); // violation
        "".equals(""); // violation
        "".equals(""); // violation
        "".equals(""); // violation
    }

    public void testSpaceViolation0() {
    }

     public void testSpaceViolation1() {
    }  // violation

    public  void testSpaceViolation2() {
    }  // violation

    public void  testSpaceViolation3() {
    }  // violation

    public void testSpaceViolation4 () {
    }  // violation

    public void testSpaceViolation5( ) {
    }  // violation

    public void testSpaceViolation6()  {
    }  // violation

    public void testSpaceViolation7() {
     }  // violation

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
