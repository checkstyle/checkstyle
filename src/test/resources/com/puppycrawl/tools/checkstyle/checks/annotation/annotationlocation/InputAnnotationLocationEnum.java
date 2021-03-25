package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;


/* Config :
 *
 * tokens = ["ENUM_DEF", "ENUM_CONSTANT_DEF"]
 * allowSamelineSingleParameterlessAnnotation = true
 * allowSamelineParameterizedAnnotation = false
 * allowSamelineMultipleAnnotations = false
 */

@EnumAnnotation(value = "foo")
  @EnumAnnotation // violation
@EnumAnnotation("bar") enum InputAnnotationLocationEnum { // violation

    @EnumAnnotation(value = "foo")
      @EnumAnnotation // violation
    @EnumAnnotation("bar") ENUM_VALUE(); // violation

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
