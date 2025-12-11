package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

import javax.annotation.CheckReturnValue;

/** Somejavadoc data. */
@Deprecated
@CheckReturnValue
public final class InputClassAnnotation2 {
  void test1() {}

  /** Somejavadoc data. */
  @Deprecated @CheckReturnValue
  public class Inner {
    // violation 2 lines above 'Annotation 'CheckReturnValue' should be alone on line'
    void test2() {}
  }
}

// violation 2 lines below 'Top-level class InputClassAnnotation3 has to reside'
/** Somejavadoc data. */
@Deprecated @CheckReturnValue
final class InputClassAnnotation3 {
  // violation 2 lines above 'Annotation 'CheckReturnValue' should be alone on line'
  void test2() {}
}

// violation 2 lines below 'Top-level class InputClassAnnotation5 has to reside'
/** Some javadoc. */
@Deprecated
// testing
@CheckReturnValue
final class InputClassAnnotation5 {
  void test3() {}
}
