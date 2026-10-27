package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

/** Somejavadoc data. */
public final class InputAnnotationOnTypes {

  // violation 2 lines below 'Annotation 'Deprecated' should be alone on line.'
  /** Somejavadoc data. */
  @Deprecated static class Foo {}

  // violation 2 lines below 'Annotation 'Deprecated' should be alone on line.'
  /** Somejavadoc data. */
  @Deprecated interface Bar {}

  // violation 2 lines below 'Annotation 'Deprecated' should be alone on line.'
  /** Somejavadoc data. */
  @Deprecated enum Size {
    S,
    M,
    L
  }

  // violation 2 lines below 'Annotation 'Deprecated' should be alone on line.'
  /** Somejavadoc data. */
  @Deprecated record Point(int x) {}

  // violation 2 lines below 'Annotation 'Deprecated' should be alone on line.'
  /** Somejavadoc data. */
  @Deprecated @interface Foo2 {}
}
