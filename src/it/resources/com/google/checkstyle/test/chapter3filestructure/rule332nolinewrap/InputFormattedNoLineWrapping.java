package com.google.checkstyle.test.chapter3filestructure.rule332nolinewrap;

// ok, long imports are allowed
import com.google.checkstyle.test.chapter3filestructure.toolongpackagetotestcoveragegooglesjavastylerule.PackageStatementTest;

/** Some javadoc. */
public class InputFormattedNoLineWrapping {

  public void fooMethod() {
    //
  }

  // Long line
  // --------------------------------------------------------------------------------------------------
  // violation above 'Line is longer than 100 characters (found 103).'

  private int[] testing =
      new int[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 12,
        13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24
      };

  PackageStatementTest packageStatementTest = new PackageStatementTest();
}
