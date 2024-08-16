package com.google.checkstyle.test.chapter4formatting.rule4854fieldannotations;

/** Some javadoc. */
public class InputFormattedFieldAnnotations {
  /** Some javadoc. */
  public @interface SomeAnnotation1 {}

  /** Some javadoc. */
  public @interface SomeAnnotation2 {}

  /** Some javadoc. */
  public @interface SomeAnnotation3 {
    /** Some javadoc. */
    int x();
  }

  /** testing. */
  @SomeAnnotation1 @SomeAnnotation2 String name = "Zops";

  /** testing. */
  @SomeAnnotation1 @SomeAnnotation2 int age = 19;

  /** testing. */
  @SomeAnnotation1 @SomeAnnotation2 String favLanguage = "Java";

  /** testing. */
  @SomeAnnotation1
  @SomeAnnotation3(x = 90)
  String favPet = "Dog";

  @SomeAnnotation1 @SomeAnnotation2 boolean test = false;

  @SuppressWarnings("bla")
  @SomeAnnotation3(x = 0)
  float pi = 3.14f;

  /** testing. */
  @SomeAnnotation1
  @SomeAnnotation2
  void test1() {}

  @SomeAnnotation1
  @SomeAnnotation2
  void test2() {}

  @SomeAnnotation3(x = 78)
  void test3() {}
}
