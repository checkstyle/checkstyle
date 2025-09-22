package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/** some javadoc. */
public class InputFormattedNoWhitespaceBeforeAnnotations {

  @Target(ElementType.TYPE_USE)
  @interface NonNull {}

  @Target(ElementType.TYPE_USE)
  @interface AnnoType {}

  @Target(ElementType.TYPE_USE)
  @interface NonNull2 {}

  @NonNull int @AnnoType [] @NonNull2 [] fiel1;
  @NonNull int @AnnoType [] @NonNull2 [] field2;

  /** some javadoc. */
  public void foo(final char @NonNull [] param) {}

  // @NonNull int @NonNull ... field3; // non-compilable
  // @NonNull int @NonNull... field4; // non-compilable

  /** some javadoc. */
  public void foo1(final char[] param) {}

  /** some javadoc. */
  public void foo2(final char[] param) {}

  /** some javadoc. */
  public void foo3(final char @NonNull [] param) {}

  /** some javadoc. */
  public void foo4(final char @NonNull [] param) {}

  void test1(String... param) {}

  void test2(String... param) {}

  void test3(String @NonNull ... param) {}

  void test4(String @NonNull ... param) {}
}
