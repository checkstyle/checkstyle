package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

/** Some javadoc. */
public @interface InputAnnotationOnAnnotationField {

  /** Some javadoc. */
  @SomeAnnotation String foo() default "";
  // violation above 'Annotation 'SomeAnnotation' should be alone on line.'

  /** Some javadoc. */
  @AnotherAnnotation("value") String bar() default "";
  // violation above 'Annotation 'AnotherAnnotation' should be alone on line.'

  /** Some javadoc. */
  @SomeAnnotation
  @AnotherAnnotation("value") @OtherAnnotation
  String foo1() default "";
  // 2 violations 2 lines above:
  // 'Annotation 'AnotherAnnotation' should be alone on line.'
  // 'Annotation 'OtherAnnotation' should be alone on line.'

  /** Some javadoc. */
  @SomeAnnotation @AnotherAnnotation("value")
  @OtherAnnotation String bar1() default "";
  // 2 violations 2 lines above:
  // 'Annotation 'SomeAnnotation' should be alone on line.'
  // 'Annotation 'AnotherAnnotation' should be alone on line.'
  // violation 4 lines above 'OtherAnnotation' should be alone on line'

  /** Some javadoc .*/
  @SomeAnnotation @AnotherAnnotation("value") @OtherAnnotation String baz () default "";
  // 3 violations above:
  // 'Annotation 'SomeAnnotation' should be alone on line.'
  // 'Annotation 'AnotherAnnotation' should be alone on line.'
  // 'Annotation 'OtherAnnotation' should be alone on line.'
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
