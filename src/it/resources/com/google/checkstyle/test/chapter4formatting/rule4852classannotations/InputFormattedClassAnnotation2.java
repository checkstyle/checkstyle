package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

import javax.annotation.CheckReturnValue;

/** Somejavadoc data. */
@Deprecated
@CheckReturnValue
public final class InputFormattedClassAnnotation2 {
  void test1() {}

  /** Somejavadoc data. */
  @Deprecated
  @CheckReturnValue
  public class Inner {
    void test2() {}
  }
}

// violation 2 lines below 'Top-level class InputClassAnnotation4 has to reside'
/** Somejavadoc data. */
@Deprecated
@CheckReturnValue
final class InputClassAnnotation4 {
  void test2() {}
}

// violation 2 lines below 'Top-level class InputClassAnnotation6 has to reside'
/** Some javadoc. */
@Deprecated
// testing
@CheckReturnValue
final class InputClassAnnotation6 {
  void test3() {}
}
