package com.google.checkstyle.test.chapter3filestructure.rule332nolinewrap;

import static java.math. // violation 'import statement should not be line-wrapped.'
        BigInteger.ONE;
import static java.math.BigInteger.ZERO; // ok

// ok, long imports are allowed
import com.google.checkstyle.test.chapter3filestructure.toolongpackagetotestcoveragegooglesjavastylerule.PackageStatementTest;
import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck; // ok
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater; // ok
import java.util.concurrent.atomic // violation 'import statement should not be line-wrapped.'
        .AtomicMarkableReference;
import javax.accessibility.AccessibleAttributeSequence; // ok
import javax.accessibility. // violation 'import statement should not be line-wrapped.'
        AccessibleAttributeSequence;

/** Some javadoc. */
public class InputNoLineWrapping {

  /** Some javadoc. */
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
