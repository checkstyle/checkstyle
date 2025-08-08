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

// violation 2 lines below 'Top-level class InputClassAnnotation4 has to reside'
/** somejavadoc data. */
@Deprecated
@CheckReturnValue
final class InputClassAnnotation4 {
  void test2() {}
}

// violation 2 lines below 'Top-level class InputClassAnnotation6 has to reside'
/** some javadoc. */
@Deprecated
// testing
@CheckReturnValue
final class InputClassAnnotation6 {
  void test3() {}
}
