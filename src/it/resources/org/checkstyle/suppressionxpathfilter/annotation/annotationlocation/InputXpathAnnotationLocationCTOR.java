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

public class InputXpathAnnotationLocationCTOR {
    @CTORAnnotation(value = "") public InputXpathAnnotationLocationCTOR()
    { // violation above 'Annotation 'CTORAnnotation' should be alone on line.'
        // comment
    }
}

@interface CTORAnnotation {
    String value();
}
