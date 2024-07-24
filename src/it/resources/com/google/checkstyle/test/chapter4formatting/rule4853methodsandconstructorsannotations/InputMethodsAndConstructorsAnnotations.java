package com.google.checkstyle.test.chapter4formatting.rule4853methodsandconstructorsannotations;

/** Some javadoc. */
public class InputMethodsAndConstructorsAnnotations {
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
  @SomeAnnotation1 void test2() {}

  /** testing. */
  @SomeAnnotation1 @SomeAnnotation2
  // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
  void test3() {}

  @SomeAnnotation1
  @SomeAnnotation2
  /** testing. */ // violation 'Javadoc comment is placed in the wrong location.'
  void test4() {}

  @SomeAnnotation1 @SomeAnnotation2
  // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
  /** testing. */ // violation 'Javadoc comment is placed in the wrong location.'
  void test5() {}

  @SomeAnnotation1 @SomeAnnotation2 void test6() {}
  // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'

  @SuppressWarnings("blah") void test7() {}
  // violation above 'Annotation 'SuppressWarnings' should be alone on line.'

  // ********testing constructors.*********

  /** testing. */
  @SomeAnnotation1
  @SomeAnnotation2
  InputMethodsAndConstructorsAnnotations() {}

  /** testing. */
  @SomeAnnotation1 InputMethodsAndConstructorsAnnotations(float f) {}

  /** testing. */
  @SomeAnnotation1 @SomeAnnotation2
  // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
  InputMethodsAndConstructorsAnnotations(int x) {}

  @SomeAnnotation1
  @SomeAnnotation2
  /** testing. */ // violation 'Javadoc comment is placed in the wrong location.'
  InputMethodsAndConstructorsAnnotations(String str) {}

  @SomeAnnotation1 @SomeAnnotation2
  // violation above 'Annotation 'SomeAnnotation2' should be alone on line.'
  /** testing. */ // violation 'Javadoc comment is placed in the wrong location.'
  InputMethodsAndConstructorsAnnotations(double d) {}

  // violation below 'Annotation 'SomeAnnotation2' should be alone on line.'
  @SomeAnnotation1 @SomeAnnotation2  InputMethodsAndConstructorsAnnotations(String a,
                                                                            String b) {}

  // violation below 'Annotation 'SuppressWarnings' should be alone on line.'
  @SuppressWarnings("blah") InputMethodsAndConstructorsAnnotations(int x, int y) {}
}
