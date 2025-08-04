package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

import javax.annotation.CheckReturnValue;

/** somejavadoc data. */
@Deprecated
@CheckReturnValue
public final class InputFormattedClassAnnotation2 {
  void test1() {}

  /** somejavadoc data. */
  @Deprecated
  @CheckReturnValue
  public class Inner {
    void test2() {}
  }
}

// violation 2 lines below 'Top-level class InputClassAnnotation3 has to reside'
/** somejavadoc data. */
@Deprecated
@CheckReturnValue
final class InputClassAnnotation3 {
  void test2() {}
}
