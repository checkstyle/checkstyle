package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

/** Some javadoc. */
public @interface InputFormattedAnnotationOnAnnotationField {

  /** Some javadoc. */
  @SomeAnnotation
  String foo() default "";

  /** Some javadoc. */
  @AnotherAnnotation("value")
  String bar() default "";

  /** Some javadoc. */
  @SomeAnnotation
  @AnotherAnnotation("value")
  @OtherAnnotation
  String foo1() default "";

  /** Some javadoc. */
  @SomeAnnotation
  @AnotherAnnotation("value")
  @OtherAnnotation
  String bar1() default "";

  /** Some javadoc. */
  @SomeAnnotation
  @AnotherAnnotation("value")
  @OtherAnnotation
  String baz() default "";

  /** Some javadoc. */
  @interface SomeAnnotation {
    /** Some javadoc. */
    String value() default "same";
  }

  /** Some javadoc. */
  @interface AnotherAnnotation {
    /** Some javadoc. */
    String value() default "single";
  }

  /** Some javadoc. */
  @interface OtherAnnotation {
    /** Some javadoc. */
    String value() default "alone";
  }
}
