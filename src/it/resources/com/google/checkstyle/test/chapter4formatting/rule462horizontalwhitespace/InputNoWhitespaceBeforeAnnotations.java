package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** some javadoc. */
public class InputNoWhitespaceBeforeAnnotations {

  @Target(ElementType.TYPE_USE)
  @interface NonNull {}

  @NonNull int @NonNull[] @NonNull[] field1;
  // 2 violations above:
  //   ''NonNull' is not followed by whitespace'
  //   ''NonNull' is not followed by whitespace'

  @NonNull int @NonNull [] @NonNull [] field2; // ok

  /** some javadoc. */
  public void foo(final char @NonNull [] param) {} // ok

  /** some javadoc. */
  public void foo1(final char[] param) {} // ok

  /** some javadoc. */
  public void foo2(final char[] param) {} // ok

  /** some javadoc. */
  public void foo3(final char @NonNull[] param) {}
  // violation above ''NonNull' is not followed by whitespace'

  /** some javadoc. */
  public void foo4(final char @NonNull [] param) {} // ok

  void test1(String... param) {}

  void test2(String... param) {}

  void test3(String @NonNull ... param) {}

  void test4(String @NonNull ... param) {}
}
