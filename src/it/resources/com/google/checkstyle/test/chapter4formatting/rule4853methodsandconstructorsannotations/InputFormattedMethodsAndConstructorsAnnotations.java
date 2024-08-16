package com.google.checkstyle.test.chapter4formatting.rule4853methodsandconstructorsannotations;

/** Some javadoc. */
public class InputFormattedMethodsAndConstructorsAnnotations {
  /** Some javadoc. */
  public @interface SomeAnnotation1 {}

  /** Some javadoc. */
  public @interface SomeAnnotation2 {}

  // ********testing methods.***********

  /** testing. */
  @SomeAnnotation1
  @SomeAnnotation2
  void test1() {}

  /** testing. */
  @SomeAnnotation1
  void test2() {}

  /** testing. */
  @SomeAnnotation1
  @SomeAnnotation2
  void test3() {}

  @SomeAnnotation1
  @SomeAnnotation2
  void test6() {}

  @SuppressWarnings("blah")
  void test7() {}

  // ********testing constructors.*********

  /** testing. */
  @SomeAnnotation1
  @SomeAnnotation2
  InputFormattedMethodsAndConstructorsAnnotations() {}

  /** testing. */
  @SomeAnnotation1
  InputFormattedMethodsAndConstructorsAnnotations(float f) {}

  /** testing. */
  @SomeAnnotation1
  @SomeAnnotation2
  InputFormattedMethodsAndConstructorsAnnotations(int x) {}

  @SomeAnnotation1
  @SomeAnnotation2
  InputFormattedMethodsAndConstructorsAnnotations(String a, String b) {}

  @SuppressWarnings("blah")
  InputFormattedMethodsAndConstructorsAnnotations(int x, int y) {}
}
