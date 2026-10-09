/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF, \
         MODULE_DEF


*/

package org.checkstyle.suppressionxpathfilter.annotation.annotationlocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

// violation below 'Annotation 'InterfaceAnnotation' should be alone on line.'
@ClassAnnotation("bar") class InputXpathAnnotationLocationClass {

}

@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
@interface ClassAnnotation {

    String value() default "";

}
