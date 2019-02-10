package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

/**
 * This test-input is intended to be checked using following configuration:
 *
 * allowSamelineSingleParameterlessAnnotation = true
 * allowSamelineParameterizedAnnotation = false
 * allowSamelineMultipleAnnotations = false
 * tokens = ["ANNOTATION_DEF", "ANNOTATION_FIELD_DEF"]
 *
 */
@AnnotationAnnotation(value = "foo")
  @AnnotationAnnotation //warn
@AnnotationAnnotation("bar") @interface InputAnnotationLocationAnnotation { //warn

    @AnnotationAnnotation(value = "foo")
      @AnnotationAnnotation //warn
    @AnnotationAnnotation("bar") String value(); //warn

}

@Repeatable(AnnotationAnnotations.class)
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@interface AnnotationAnnotation  {

    String value() default "";

}

@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@interface AnnotationAnnotations {

    AnnotationAnnotation[] value();

}
