package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** some javadoc. */
public class InputFormattedNoWhitespaceBeforeEmptyForLoop {

  /** some javadoc. */
  public static void foo() {
    for (; ; ) { // ok
      break;
    }
    for (int x = 0; ; ) { // ok
      break;
    }
    for (int x = 0; ; ) {
      break;
    }
    for (int x = 0; x < 10; ) { // ok
      break;
    }
    for (int x = 0; x < 10; ) {
      break;
    }
  }
}
