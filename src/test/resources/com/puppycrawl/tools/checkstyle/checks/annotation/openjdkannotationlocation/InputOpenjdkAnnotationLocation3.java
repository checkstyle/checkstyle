/*
OpenjdkAnnotationLocation
tokens = ENUM_DEF, ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

// violation 3 lines below """Annotations must be on a separate line
// from 'InputOpenjdkAnnotationLocation3'."""
@EnumAnnotation(value = "foo") @EnumAnnotation
@EnumAnnotation("bar") enum InputOpenjdkAnnotationLocation3 {
// violation above """Annotations on 'InputOpenjdkAnnotationLocation3' must be all on one line
// or all on separate lines."""

    @EnumAnnotation(value = "foo")
    @EnumAnnotation
    @EnumAnnotation("bar") ENUM_VALUE_BAD();
    // violation above 'Annotations must be on a separate line from 'ENUM_VALUE_BAD'.'

    InputOpenjdkAnnotationLocation3() {
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

