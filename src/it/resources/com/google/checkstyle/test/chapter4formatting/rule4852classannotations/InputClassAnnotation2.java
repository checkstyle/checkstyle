package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

import javax.annotation.CheckReturnValue;

/** Somejavadoc data. */
@Deprecated
@CheckReturnValue
public final class InputClassAnnotation2 {
  void test1() {}

  // 2 violations 4 lines below:
  //    'Annotation 'Deprecated' should be alone on line.'
  //    'Annotation 'CheckReturnValue' should be alone on line.'
  /** Somejavadoc data. */
  @Deprecated @CheckReturnValue
  public class Inner {
    void test2() {}
  }
}

// violation 5 lines below 'Top-level class InputClassAnnotation3 has to reside'
// 2 violations 4 lines below:
//    'Annotation 'Deprecated' should be alone on line.'
//    'Annotation 'CheckReturnValue' should be alone on line.'
/** Somejavadoc data. */
@Deprecated @CheckReturnValue
final class InputClassAnnotation3 {
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
