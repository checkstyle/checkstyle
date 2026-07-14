/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = false
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;
// 3 violations 4 lines below:
// 'Annotation 'MyAnnotation11' should be alone on line.'
// 'Annotation 'MyAnnotation12' should be alone on line.'
// 'Annotation 'MyAnnotation13' should be alone on line.'
@MyAnnotation11 @MyAnnotation12 @MyAnnotation13
public class InputAnnotationLocationCustomAnnotationsDeclared {

    @MyAnnotation13
    void method() {

    }

    @MyAnnotation13
    @MyAnnotation12
    void method2() {

    }

}

@interface MyAnnotation11 {
}

@interface MyAnnotation12 {
}

@interface MyAnnotation13 {
}
