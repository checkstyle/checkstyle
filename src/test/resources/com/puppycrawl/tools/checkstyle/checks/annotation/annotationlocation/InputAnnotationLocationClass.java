package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

/**
 * This test-input is intended to be checked using following configuration:
 *
 * tokens = ["CLASS_DEF", "CTOR_DEF", "VARIABLE_DEF"]
 * allowSamelineSingleParameterlessAnnotation = true
 * allowSamelineParameterizedAnnotation = false
 * allowSamelineMultipleAnnotations = false
 *
 */
@ClassAnnotation(value = "foo")
  @ClassAnnotation //warn
@ClassAnnotation("bar") class InputAnnotationLocationClass { //warn

    @ClassAnnotation(value = "foo")
      @ClassAnnotation //warn
    @ClassAnnotation("bar") Object field; //warn

    @ClassAnnotation(value = "foo")
      @ClassAnnotation //warn
    @ClassAnnotation("bar") InputAnnotationLocationClass() { //warn
    }

}

@Repeatable(ClassAnnotations.class)
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
@interface ClassAnnotation {

    String value() default "";

}

@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
@interface ClassAnnotations {

    ClassAnnotation[] value();

}
