/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = CLASS_DEF, CTOR_DEF, VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

@ClassAnnotation(value = "foo")
  @ClassAnnotation // violation indentation should not be given
@ClassAnnotation("bar") class InputAnnotationLocationClass { // violation 'class' should be on one line

    @ClassAnnotation(value = "foo")
      @ClassAnnotation // violation indentation should not be given
    @ClassAnnotation("bar") Object field; // violation 'Object field' should be on one line

    @ClassAnnotation(value = "foo")
      @ClassAnnotation // violation indentation should not be given
    @ClassAnnotation("bar") InputAnnotationLocationClass() { // violation 'InputAnnotationLocationClass' should be on one line
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
