package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** some javadoc. */
public class InputFormattedNoWhitespaceBeforeAnnotations {

  @Target(ElementType.TYPE_USE)
  @interface NonNull {}

  @NonNull int @NonNull [] @NonNull [] fiel1; //until #8205
  @NonNull int @NonNull [] @NonNull [] field2;

  /** some javadoc. */
  public void foo(final char @NonNull [] param) {}

  // @NonNull int @NonNull ... field3; // non-compilable
  // @NonNull int @NonNull... field4; // non-compilable

  /** some javadoc. */
  public void foo1(final char[] param) {}

  /** some javadoc. */
  public void foo2(final char[] param) {}

  /** some javadoc. */
  public void foo3(final char @NonNull [] param) {} //until #8205

  /** some javadoc. */
  public void foo4(final char @NonNull [] param) {}

  void test1(String... param) {} //until #8205

  void test2(String... param) {} //until #8205

  void test3(String @NonNull ... param) {} //until #8205

  void test4(String @NonNull ... param) {}
}
