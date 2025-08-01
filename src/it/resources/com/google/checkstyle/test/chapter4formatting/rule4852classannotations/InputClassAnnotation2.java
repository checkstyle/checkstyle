package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

import javax.annotation.CheckReturnValue;

/** somejavadoc data. */
@Deprecated
@CheckReturnValue
public final class InputClassAnnotation2 {
  void test1() {}

  /** somejavadoc data. */
  @Deprecated @CheckReturnValue
  public class Inner {
    // violation 2 lines above 'Annotation 'CheckReturnValue' should be alone on line'
    void test2() {}
  }
}

// violation 2 lines below 'Top-level class InputClassAnnotation3 has to reside'
/** somejavadoc data. */
@Deprecated @CheckReturnValue
final class InputClassAnnotation3 {
  // violation 2 lines above 'Annotation 'CheckReturnValue' should be alone on line'
  void test2() {}
}
