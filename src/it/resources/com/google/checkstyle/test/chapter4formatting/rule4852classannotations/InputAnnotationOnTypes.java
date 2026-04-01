package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

/** Somejavadoc data. */
public final class InputAnnotationOnTypes {

  /** Somejavadoc data. */
  @Deprecated static class Foo {}
  // violation above 'Annotation 'Deprecated' should be alone on line.'

  /** Somejavadoc data. */
  @Deprecated interface Bar {}
  // violation above 'Annotation 'Deprecated' should be alone on line.'

  /** Somejavadoc data. */
  // violation below 'Annotation 'Deprecated' should be alone on line.'
  @Deprecated enum Size {
    S,
    M,
    L
  }

  /** Somejavadoc data. */
  @Deprecated record Point(int x) {}
  // violation above 'Annotation 'Deprecated' should be alone on line.'
}
