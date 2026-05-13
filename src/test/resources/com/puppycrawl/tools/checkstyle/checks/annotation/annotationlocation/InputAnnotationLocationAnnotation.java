/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = ANNOTATION_DEF, ANNOTATION_FIELD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

// violation 3 lines below '.* incorrect .* level 2, .* should be 0.'
// violation 3 lines below 'Annotation 'AnnotationAnnotation' should be alone on line.'
@AnnotationAnnotation(value = "foo")
  @AnnotationAnnotation 
@AnnotationAnnotation("bar") @interface InputAnnotationLocationAnnotation {

    // violation 3 lines below '.* incorrect .* level 6, .* should be 4.'
    // violation 3 lines below '.* should be alone on line.'
    @AnnotationAnnotation(value = "foo")
      @AnnotationAnnotation 
    @AnnotationAnnotation("bar") String value(); 

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
