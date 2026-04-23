package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputFormattedNoWhitespaceBeforeEmptyForLoop {

  /** Some javadoc. */
  public static void foo() {
    for (; ; ) {
      break;
    }
    for (int x = 0; ; ) {
      break;
    }
    for (int x = 0; ; ) {
      break;
    }
    for (int x = 0; x < 10; ) {
      break;
    }
    for (int x = 0; x < 10; ) {
      break;
    }
  }
}
