package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputWhitespaceBeforeLeftCurlyOfEmptyBlock {

  InputWhitespaceBeforeLeftCurlyOfEmptyBlock instance =
      new InputWhitespaceBeforeLeftCurlyOfEmptyBlock(){}; // ok until #17715

  // violation below 'WhitespaceAround: '{' is not preceded with whitespace.'
  InputWhitespaceBeforeLeftCurlyOfEmptyBlock(){}

  void method(){} // ok until #17715

  class Class{} // ok until #17715

  interface Interface{} // ok until #17715

  @interface Annotation{} // ok until #17715

  enum Enum{} // ok until #17715

  record Record(){} // ok until #17715

  /** Some javadoc. */
  public static void main(String... args) {

    boolean b = System.currentTimeMillis() < 0;

    while (b){} // ok until #17715

    for (int i = 1; i > 1; i++){} // ok until #17715

    do{} while (b); // violation 'WhitespaceAround: 'do' is not followed by whitespace. *'

    Runnable noop = () ->{}; // violation 'WhitespaceAround: '->' is not followed by whitespace. *'
  }

  static{} // ok until #17715

  record Record2(String str) {

    public Record2{} // violation 'WhitespaceAround: '{' is not preceded with whitespace.'

  }
}
