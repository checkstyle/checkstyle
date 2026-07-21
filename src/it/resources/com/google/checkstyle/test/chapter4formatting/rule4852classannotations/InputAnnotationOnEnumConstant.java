package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Some javadoc. */
public enum InputAnnotationOnEnumConstant {

  // violation below 'Annotation 'Deprecated' should be alone on line.'
  @Deprecated FIRST,

  // violation below 'Annotation 'SomeAnnotation' should be alone on line.'
  @SomeAnnotation("value") SECOND,

  // 2 violations 3 lines below:
  // 'Annotation 'Deprecated' should be alone on line.'
  // 'Annotation 'AnotherAnnotation' should be alone on line.'
  @Deprecated @AnotherAnnotation THIRD,

  // violation below 'Annotation 'OtherAnnotation' should be alone on line.'
  @OtherAnnotation(name = "fifth", priority = 1) FOURTH,

  // 3 violations 4 lines below:
  // 'Annotation 'Deprecated' should be alone on line.'
  // 'Annotation 'SomeAnnotation' should be alone on line.'
  // 'Annotation 'AnotherAnnotation' should be alone on line.'
  @Deprecated @SomeAnnotation("same") @AnotherAnnotation SIXTH,

  // 2 violations 3 lines below:
  // 'Annotation 'Deprecated' should be alone on line.'
  // 'Annotation 'SomeAnnotation' should be alone on line.'
  @Deprecated @SomeAnnotation("same")
  @AnotherAnnotation SEVENTH;
  // violation above 'Annotation 'AnotherAnnotation' should be alone on line.'

  private final String value;

  InputAnnotationOnEnumConstant() {
    this.value = null;
  }

  InputAnnotationOnEnumConstant(String value) {
    this.value = value;
  }
}

// violation 2 lines below 'Top-level class SomeAnnotation has to reside in its own source file.'
/** Some javadoc. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface SomeAnnotation {
  String value() default "";
}

// violation 2 lines below 'Top-level class AnotherAnnotation has to reside in its own source file.'
/** Some javadoc. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface AnotherAnnotation {
}

// violation 2 lines below 'Top-level class OtherAnnotation has to reside in its own source file.'
/** Some javadoc. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface OtherAnnotation {
  String name();
  int priority() default 0;
}
