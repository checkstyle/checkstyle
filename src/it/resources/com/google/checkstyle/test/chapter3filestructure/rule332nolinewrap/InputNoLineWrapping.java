package com.google.checkstyle.test.chapter3filestructure.rule332nolinewrap;

import static java.math.BigInteger.ZERO; // ok
import com.google.checkstyle.test.chapter3filestructure.toolongpackagetotestcoveragegooglesjavastylerule.*; // ok, long imports are allowed

import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck; // ok
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater; // ok
import javax.accessibility.AccessibleAttributeSequence; // ok

import javax.accessibility. // violation 'import statement should not be line-wrapped.'
        AccessibleAttributeSequence;
import static java.math. // violation 'import statement should not be line-wrapped.'
        BigInteger.ONE;
import java.util.concurrent.atomic // violation 'import statement should not be line-wrapped.'
        .AtomicMarkableReference;

public class InputNoLineWrapping {

  public void fooMethod() {
    //
  }

  // Long line
  // --------------------------------------------------------------------------------------------------
  // violation above 'Line is longer than 100 characters (found 103).'

  private int[] mInts =
      new int[] {
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 12,
        13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24
      };
  // violation above 'Line is longer than 100 characters (found 104).'
}
