package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

/** Some javadoc. */
public class InputWhitespaceBeforeLeftCurlyOfEmptyBlock {

  InputWhitespaceBeforeLeftCurlyOfEmptyBlock instance =
      new InputWhitespaceBeforeLeftCurlyOfEmptyBlock(){};
  // violation above ''{' is not preceded with whitespace'

  // violation below ''{' is not preceded with whitespace.'
  InputWhitespaceBeforeLeftCurlyOfEmptyBlock(){}

  void method(){} // violation ''{' is not preceded with whitespace'

  class Class{} // violation ''{' is not preceded with whitespace'

  interface Interface{} // violation ''{' is not preceded with whitespace'

  @interface Annotation{} // violation ''{' is not preceded with whitespace'

  enum Enum{} // violation ''{' is not preceded with whitespace'

  record Record(){} // violation ''{' is not preceded with whitespace'

  /** Some javadoc. */
  public static void main(String... args) {

    boolean b = System.currentTimeMillis() < 0;

    while (b){} // violation ''{' is not preceded with whitespace'

    for (int i = 1; i > 1; i++){} // violation ''{' is not preceded with whitespace'

    do{} while (b);
    // 2 violations above:
    // 'WhitespaceAround: 'do' is not followed by whitespace.'
    // ''{' is not preceded with whitespace.'

    Runnable noop = () ->{};
    // 2 violations above:
    // 'WhitespaceAround: '->' is not followed by whitespace.'
    // ''{' is not preceded with whitespace.'
  }

  static{} // violation ''{' is not preceded with whitespace'

  record Record2(String str) {

    public Record2{} // violation ''{' is not preceded with whitespace.'

  }
}
