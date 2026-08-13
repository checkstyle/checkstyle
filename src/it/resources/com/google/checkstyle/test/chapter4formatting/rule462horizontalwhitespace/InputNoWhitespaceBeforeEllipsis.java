package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

//
/** Some javadoc. */
public class InputNoWhitespaceBeforeEllipsis {

  @Target(ElementType.TYPE_USE)
  @interface NonNull {}

  @Target(ElementType.TYPE_USE)
  @interface B {}

  @Target(ElementType.TYPE_USE)
  @interface C {}

  @Target(ElementType.TYPE_USE)
  @interface Size {
    int max();
  }
  // @NonNull int @NonNull ... field3; // non-compilable
  // @NonNull int @NonNull... field4; // non-compilable

  /** Some javadoc. */
  void test1(String... param) {}

  /** Some javadoc. */
  void test2(String ... param) {} // violation ''...' is preceded with whitespace.'

  /** Some javadoc. */
  void test3(String @NonNull ... param) {}

  /** Some javadoc. */
  void test4(String @NonNull... param) {}
  // violation above ''NonNull' is not followed by whitespace'

  /** Some javadoc. */
  void test5(String[]... param) {}

  /** Some javadoc. */
  void test6(String[] ... param) {}
  // 2 violations above:
  // '']' is followed by whitespace'
  // ''...' is preceded with whitespace.'

  /** Some javadoc. */
  void test7(String @NonNull[]... param) {}
  // 2 violations above:
  // ''NonNull' is not followed by whitespace'
  // ''[' is not preceded with whitespace'

  /** Some javadoc. */
  void test8(String @NonNull[] ... param) {}
  // 4 violations above:
  //   ''NonNull' is not followed by whitespace'
  // ''[' is not preceded with whitespace'
  // '']' is followed by whitespace'
  //   ''...' is preceded with whitespace.'

  void test9(String @Size(max = 10) ... names) {}

  void test10(String @Size(max = 10)... names) {}
  // violation above '')' is not followed by whitespace'

  void test11(@NonNull String @C [] @B ... arg) {}

  void test12(@NonNull String @C []    ... arg) {}
  // 3 violations above:
  // '']' is followed by whitespace'
  // ''...' is preceded with whitespace'
  // 'Use a single space to separate non-whitespace characters'

  void test13(@NonNull String    [] @B ... arg) {}
  // 2 violations above:
  // ''[' is preceded with whitespace'
  // 'Use a single space to separate non-whitespace characters'

  void test14(   String    [] @B ... arg) {}
  // 4 violations above:
  // ''(' is followed by whitespace'
  // 'Use a single space to separate non-whitespace characters'
  // ''[' is preceded with whitespace'
  // 'Use a single space to separate non-whitespace characters'
}
