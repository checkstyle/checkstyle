package com.google.checkstyle.test.chapter4formatting.rule4854fieldannotations;

import java.util.ArrayList;
import java.util.List;

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

  /** Testing. */
  @SomeAnnotation1 @SomeAnnotation2 String name = "Zops";

  /** Testing. */
  @SomeAnnotation1 @SomeAnnotation2 int age = 19;

  /** Testing. */
  @SomeAnnotation1 @SomeAnnotation2 String favLanguage = "Java";

  /** Testing. */
  @SomeAnnotation1
  @SomeAnnotation3(x = 90)
  String favPet = "Dog";

  @SomeAnnotation1 @SomeAnnotation2 boolean test = false;

  @SuppressWarnings("bla")
  @SomeAnnotation3(x = 0)
  float pi = 3.14f;

  /** Testing. */
  @SomeAnnotation1
  @SomeAnnotation3(x = 14)
  List<String> list = new ArrayList<>();

  /** Testing. */
  @SuppressWarnings("bla")
  InputFormattedFieldAnnotations obj = new InputFormattedFieldAnnotations();

  /** Testing. */
  @SomeAnnotation1
  @SomeAnnotation2
  void test1() {}

  @SomeAnnotation1
  @SomeAnnotation2
  void test2() {}

  @SomeAnnotation3(x = 78)
  void test3() {}
}
