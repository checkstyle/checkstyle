/*
RightCurlyAloneOrEmpty
*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

class InputRightCurlyAloneOrEmptyNoViolations {
  public @interface TestAnnotation {}

  public @interface TestAnnotation1 { String someValue(); }

  public @interface TestAnnotation2 {
    String someValue(); }  // violation

  public @interface TestAnnotation3 {
    String someValue();
  } //ok

  public @interface TestAnnotation4 { String someValue();
  } //ok

  enum TestEnum2 {
    SOME_VALUE; } // violation
}
