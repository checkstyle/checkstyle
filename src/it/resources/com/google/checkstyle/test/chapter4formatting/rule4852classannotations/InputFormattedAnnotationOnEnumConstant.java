package com.google.checkstyle.test.chapter4formatting.rule4852classannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Some javadoc. */
public enum InputFormattedAnnotationOnEnumConstant {
  @Deprecated
  FIRST,

  @SomeAnnotation("value")
  SECOND,

  @Deprecated
  @AnotherAnnotation
  THIRD,

  @OtherAnnotation(name = "fifth", priority = 1)
  FOURTH,

  @Deprecated
  @SomeAnnotation("same")
  @AnotherAnnotation
  SIXTH,

  @Deprecated
  @SomeAnnotation("same")
  @AnotherAnnotation
  SEVENTH;

  private final String value;

  InputFormattedAnnotationOnEnumConstant() {
    this.value = null;
  }

  InputFormattedAnnotationOnEnumConstant(String value) {
    this.value = value;
  }

  /** Some javadoc. */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface SomeAnnotation {
    String value() default "";
  }

  /** Some javadoc. */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface AnotherAnnotation {}

  /** Some javadoc. */
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.FIELD)
  @interface OtherAnnotation {
    String name();

    int priority() default 0;
  }
}

