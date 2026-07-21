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

  /** Some javadoc . */
  @SomeAnnotation
  @AnotherAnnotation("value")
  @OtherAnnotation
  String baz() default "";
}

// violation 2 lines below 'Top-level class SomeAnnotation has to reside in its own source file.'
/** Some javadoc. */
@interface SomeAnnotation {
  String value() default "same";
}

// violation 2 lines below 'Top-level class AnotherAnnotation has to reside in its own source file.'
/** Some javadoc. */
@interface AnotherAnnotation {
  String value() default "single";
}

// violation 2 lines below 'Top-level class OtherAnnotation has to reside in its own source file.'
/** Some javadoc. */
@interface OtherAnnotation {
  String value() default "alone";
}
