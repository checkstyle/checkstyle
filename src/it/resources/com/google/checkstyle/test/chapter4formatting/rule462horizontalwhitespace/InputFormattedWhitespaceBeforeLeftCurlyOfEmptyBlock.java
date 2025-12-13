package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputFormattedWhitespaceBeforeLeftCurlyOfEmptyBlock {

  InputFormattedWhitespaceBeforeLeftCurlyOfEmptyBlock instance =
      new InputFormattedWhitespaceBeforeLeftCurlyOfEmptyBlock() {};

  InputFormattedWhitespaceBeforeLeftCurlyOfEmptyBlock() {}

  void method() {}

  class Class {}

  interface Interface {}

  @interface Annotation {}

  enum Enum {}

  record Record() {}

  /** Some javadoc. */
  public static void main(String... args) {

    boolean b = System.currentTimeMillis() < 0;

    while (b) {}

    for (int i = 1; i > 1; i++) {}

    do {} while (b);

    Runnable noop = () -> {};
  }

  static {
  }

  record Record2(String str) {

    public Record2 {}
  }
}
