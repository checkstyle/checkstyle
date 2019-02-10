package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

/**
 * This test-input is intended to be checked using following configuration:
 *
 * tokens = ["ENUM_DEF", "ENUM_CONSTANT_DEF"]
 * allowSamelineSingleParameterlessAnnotation = true
 * allowSamelineParameterizedAnnotation = false
 * allowSamelineMultipleAnnotations = false
 *
 */
@EnumAnnotation(value = "foo")
  @EnumAnnotation //warn
@EnumAnnotation("bar") enum InputAnnotationLocationEnum { //warn

    @EnumAnnotation(value = "foo")
      @EnumAnnotation //warn
    @EnumAnnotation("bar") ENUM_VALUE(); //warn

    InputAnnotationLocationEnum() {
    }

}

@Repeatable(EnumAnnotations.class)
@Target({ElementType.FIELD, ElementType.TYPE})
@interface EnumAnnotation  {

    String value() default "";

}

@Target({ElementType.FIELD, ElementType.TYPE})
@interface EnumAnnotations {

    EnumAnnotation[] value();

}
