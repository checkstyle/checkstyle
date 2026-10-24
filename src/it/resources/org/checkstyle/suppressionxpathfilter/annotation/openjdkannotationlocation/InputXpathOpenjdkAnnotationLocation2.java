/*
OpenjdkAnnotationLocation
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         RECORD_DEF, COMPACT_CTOR_DEF, MODULE_DEF


*/

package org.checkstyle.suppressionxpathfilter.annotation.openjdkannotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@EnumAnnotation("bar") enum InputXpathOpenjdkAnnotationLocation2 {
// violation above """Annotations must be on a separate line
// from 'InputXpathOpenjdkAnnotationLocation2'."""

}

@Target({ElementType.FIELD, ElementType.TYPE})
@interface EnumAnnotation  {

    String value() default "";

}
