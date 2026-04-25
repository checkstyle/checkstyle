package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

/** Somejavadoc data. */
public final class InputFormattedAnnotationOnTypes {

  /** Somejavadoc data. */
  @Deprecated
  static class Foo {}

  /** Somejavadoc data. */
  @Deprecated
  interface Bar {}

  /** Somejavadoc data. */
  @Deprecated
  enum Size {
    S,
    M,
    L
  }

  /** Somejavadoc data. */
  @Deprecated
  record Point(int x) {}
}
