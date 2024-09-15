package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

/**
 * Test class for checking the rule 4852.
 */
public class InputClassAnnotations {
  /**
   * Custom annotation 1.
   */
  public @interface SomeAnnotation1 { }
  // violation above 'Empty blocks should have no spaces. .* may only be represented as {}'

  /**
   * Custom annotation 2.
   */
  public @interface SomeAnnotation2 { }
  // violation above 'Empty blocks should have no spaces. .* may only be represented as {}'

  /**
   * Inner class 2.
   */
  @SomeAnnotation1
  @SomeAnnotation2
  class Inner1 {}

  @SomeAnnotation1
  @SomeAnnotation2
  /** // violation 'Javadoc comment is placed in the wrong location.'
   * Inner class 2.
   */
  class Inner2 {}

  /**
   * Inner class 3.
   */
  @SomeAnnotation1 @SomeAnnotation2
  // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
  class Inner3 {}

  @SomeAnnotation1 @SomeAnnotation2
  // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
  /** // violation 'Javadoc comment is placed in the wrong location.'
   * Inner class 4.
   */
  class Inner4 {}
}
