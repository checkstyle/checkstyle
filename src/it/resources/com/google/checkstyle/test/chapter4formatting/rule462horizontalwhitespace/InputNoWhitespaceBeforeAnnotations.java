package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** Some javadoc. */
public class InputNoWhitespaceBeforeAnnotations {

  @Target(ElementType.TYPE_USE)
  @interface NonNull {}

  @Target(ElementType.TYPE_USE)
  @interface AnnoType {}

  @Target(ElementType.TYPE_USE)
  @interface NonNull2 {}

  @NonNull int @AnnoType[] @NonNull2[] field1;
  // 2 violations above:
  //   ''AnnoType' is not followed by whitespace'
  //   ''NonNull2' is not followed by whitespace'

  @NonNull int @AnnoType [] @NonNull2 [] field2;

  @NonNull int @AnnoType [] @NonNull2[] field3;
  // violation above ''NonNull2' is not followed by whitespace'

  // @NonNull int @NonNull ... field3; // non-compilable
  // @NonNull int @NonNull... field4; // non-compilable

  /** Some javadoc. */
  public void foo(final char @NonNull [] param) {}

  /** Some javadoc. */
  public void foo1(final char[] param) {}

  /** Some javadoc. */
  public void foo2(final char[] param) {}

  /** Some javadoc. */
  public void foo3(final char @NonNull[] param) {}
  // violation above ''NonNull' is not followed by whitespace'

  /** Some javadoc. */
  public void foo4(final char @NonNull [] param) {}

  void test1(String... param) {}

  void test2(String... param) {}

  void test3(String @NonNull ... param) {}

  void test4(String @NonNull ... param) {}
}
