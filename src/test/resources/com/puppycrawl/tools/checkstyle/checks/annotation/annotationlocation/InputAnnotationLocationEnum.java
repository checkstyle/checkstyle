/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = ENUM_DEF, ENUM_CONSTANT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

@EnumAnnotation(value = "foo")
  @EnumAnnotation // violation indentation should not be given
@EnumAnnotation("bar") enum InputAnnotationLocationEnum { // violation 'enum' should be on one line

    @EnumAnnotation(value = "foo")
      @EnumAnnotation // violation indentation should not be given
    @EnumAnnotation("bar") ENUM_VALUE(); // violation 'ENUM_VALUE' should be on one line

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
