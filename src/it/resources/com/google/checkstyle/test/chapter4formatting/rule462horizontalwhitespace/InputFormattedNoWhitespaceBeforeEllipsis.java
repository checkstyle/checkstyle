package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** Some javadoc. */
public class InputFormattedNoWhitespaceBeforeEllipsis {

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
  void test2(String... param) {}

  /** Some javadoc. */
  void test3(String @NonNull ... param) {}

  /** Some javadoc. */
  void test4(String @NonNull ... param) {}

  /** Some javadoc. */
  void test5(String[]... param) {}

  /** Some javadoc. */
  void test6(String[]... param) {}

  /** Some javadoc. */
  void test7(String @NonNull []... param) {}

  /** Some javadoc. */
  void test8(String @NonNull []... param) {}

  void test9(String @Size(max = 10) ... names) {}

  void test10(String @Size(max = 10) ... names) {}

  void test11(@NonNull String @C [] @B ... arg) {}

  void test12(@NonNull String @C []... arg) {}

  void test13(@NonNull String[] @B ... arg) {}

  void test14(String[] @B ... arg) {}
}
