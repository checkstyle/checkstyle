package com.google.checkstyle.test. // violation 'package statement should not be line-wrapped.'
    chapter3filestructure.rule32packagestatement;

/** Some javadoc. */
public class InputPackageStatement {
  // Long line
  // -----------------------------------------------------------------------------------------------------
  // violation above 'Line is longer than 100 characters (found 106).'

  private int[] testing =
      new int[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 12,
        13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24
      };
}
