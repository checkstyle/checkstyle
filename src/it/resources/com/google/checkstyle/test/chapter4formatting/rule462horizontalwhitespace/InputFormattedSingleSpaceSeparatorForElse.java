package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Test input for SingleSpaceSeparator check. */
public class InputFormattedSingleSpaceSeparatorForElse {
  void testIf(int x) {
    if (x > 0) {
      System.out.println("Positive");
    } else {
      return;
    }
  }

  void testFor() {
    for (int cur = 0; cur < 5; cur++) {
      System.out.println(cur);
    }
  }
}

