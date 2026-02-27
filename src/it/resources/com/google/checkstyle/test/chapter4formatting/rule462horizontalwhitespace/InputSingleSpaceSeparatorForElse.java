package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/**
 * Test input for SingleSpaceSeparator check.
 */
public class InputSingleSpaceSeparatorForElse {
  void testIf(int x) {
    // violation below 'Use a single space to separate non-whitespace characters.'
    if   (x > 0) {
      System.out.println("Positive");
    }       else       {
      // violation above 'Use a single space to separate non-whitespace characters.'
      // violation 2 lines above 'Use a single space to separate non-whitespace characters.'
      return;
    }
  }

  void testFor() {
    // violation below 'Use a single space to separate non-whitespace characters.'
    for    (int cur = 0; cur < 5; cur++) {
      System.out.println(cur);
    }
  }
}

