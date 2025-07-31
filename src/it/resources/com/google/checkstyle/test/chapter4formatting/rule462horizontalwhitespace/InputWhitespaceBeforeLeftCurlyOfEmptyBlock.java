package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** some javadoc. */
public class InputWhitespaceBeforeLeftCurlyOfEmptyBlock {

  InputWhitespaceBeforeLeftCurlyOfEmptyBlock instance =
      new InputWhitespaceBeforeLeftCurlyOfEmptyBlock(){}; // ok until #10834

  // violation below 'WhitespaceAround: '{' is not preceded with whitespace.'
  InputWhitespaceBeforeLeftCurlyOfEmptyBlock(){}

  void method(){} // ok until #10834

  class Class{} // ok until #10834

  interface Interface{} // ok until #10834

  @interface Annotation{} // ok until #10834

  enum Enum{} // ok until #10834

  record Record(){} // ok until #10834

  /** some javadoc. */
  public static void main(String... args) {

    boolean b = System.currentTimeMillis() < 0;

    while (b){} // ok until #10834

    for (int i = 1; i > 1; i++){} // ok until #10834

    do{} while (b);
    // 2 violations above:
    //   ''do' is not followed by whitespace.'
    //   'WhitespaceAround: 'do' is not followed by whitespace. *'

    Runnable noop = () ->{};
    // 2 violations above:
    //   ''->' is not followed by whitespace.'
    //   'WhitespaceAround: '->' is not followed by whitespace. *'
  }

  static{} // ok until #10834

  record Record2(String str) {

    public Record2{} // violation 'WhitespaceAround: '{' is not preceded with whitespace.'

  }
}
