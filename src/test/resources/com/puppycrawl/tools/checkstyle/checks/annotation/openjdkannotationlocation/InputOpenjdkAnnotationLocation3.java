/*
OpenjdkAnnotationLocation
tokens = ENUM_DEF, ENUM_CONSTANT_DEF

*/

package com.puppycrawl.tools.checkstyle.checks.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;

@EnumAnnotation(value = "foo") @EnumAnnotation
// violation below, 'Annotation 'EnumAnnotation' must be alone on a line, or all on one line.'
@EnumAnnotation("bar") enum InputOpenjdkAnnotationLocation3 {
// violation above, 'Annotation 'EnumAnnotation' must be on a separate line from target.'

    @EnumAnnotation(value = "foo")
    @EnumAnnotation
    @EnumAnnotation("bar") ENUM_VALUE();
    // violation above, 'Annotation 'EnumAnnotation' must be on a separate line from target.'

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

