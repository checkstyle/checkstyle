package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

/**
 * This test-input is intended to be checked using following configuration:
 *
 * tokens = ["INTERFACE_DEF", "METHOD_DEF", "PARAMETER_DEF"]
 * allowSamelineSingleParameterlessAnnotation = true
 * allowSamelineParameterizedAnnotation = false
 * allowSamelineMultipleAnnotations = false
 *
 */
@InterfaceAnnotation(value = "foo")
  @InterfaceAnnotation //warn
@InterfaceAnnotation("bar") interface InputAnnotationLocationInterface { //warn

    @InterfaceAnnotation(value = "foo")
      @InterfaceAnnotation //warn
    @InterfaceAnnotation("bar") void method( //warn
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
