package com.puppycrawl.tools.checkstyle.checks.javadoc.missingleadingasterisk;

public class InputMissingLeadingAsterisk {
  /**
   ** OK
   *
       violation
   violation

   **/
  public void test(int a) {}

  /** This is an inline comment */
  public void test2() {}
}
