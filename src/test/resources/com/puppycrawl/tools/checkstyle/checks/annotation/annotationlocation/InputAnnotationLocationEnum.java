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
  // violation below '.* 'EnumAnnotation' have incorrect indentation level 2, .* should be 0.'
  @EnumAnnotation
// violation below 'Annotation 'EnumAnnotation' should be alone on line.'
@EnumAnnotation("bar") enum InputAnnotationLocationEnum {

    @EnumAnnotation(value = "foo")
      // violation below '.* 'EnumAnnotation' have incorrect indentation level 6, .* should be 4.'
      @EnumAnnotation
    // violation below 'Annotation 'EnumAnnotation' should be alone on line.'
    @EnumAnnotation("bar") ENUM_VALUE();

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
