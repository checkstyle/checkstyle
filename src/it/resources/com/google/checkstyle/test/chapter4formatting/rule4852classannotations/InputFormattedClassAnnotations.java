package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

/** Test class for checking the rule 4852. */
public class InputFormattedClassAnnotations {
  /** Custom annotation 1. */
  public @interface SomeAnnotation1 {}

  /** Custom annotation 2. */
  public @interface SomeAnnotation2 {}

  /** Inner class 2. */
  @SomeAnnotation1
  @SomeAnnotation2
  class Inner1 {}

  /** Inner class 3. */
  @SomeAnnotation1
  @SomeAnnotation2
  class Inner3 {}
}
