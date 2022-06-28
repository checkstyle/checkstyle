/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = INTERFACE_DEF, METHOD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

@InterfaceAnnotation(value = "foo")
  @InterfaceAnnotation // violation '.* incorrect .* level 2, .* should be 0.'
// violation below 'Annotation 'InterfaceAnnotation' should be alone on line.'
@InterfaceAnnotation("bar") interface InputAnnotationLocationInterface {

    @InterfaceAnnotation(value = "foo")
      @InterfaceAnnotation // violation '.* incorrect .* level 6, .* should be 4.'
    @InterfaceAnnotation("bar") void method( // violation '.* should be alone on line.'
        int param1,
        @InterfaceAnnotation(value = "foo")
          @InterfaceAnnotation
        @InterfaceAnnotation("bar") int param2,
        int param3);

}

@Repeatable(InterfaceAnnotations.class)
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@interface InterfaceAnnotation  {

    String value() default "";

}

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE})
@interface InterfaceAnnotations {

    InterfaceAnnotation[] value();

}
