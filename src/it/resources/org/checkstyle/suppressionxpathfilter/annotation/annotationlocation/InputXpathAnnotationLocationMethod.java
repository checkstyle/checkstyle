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

public class InputXpathAnnotationLocationMethod {
     @MethodAnnotation("foo") void foo1() {}
} // violation above 'Annotation '@MethodAnnotation' should be alone on line.'

@interface MethodAnnotation {
     String value();
}
