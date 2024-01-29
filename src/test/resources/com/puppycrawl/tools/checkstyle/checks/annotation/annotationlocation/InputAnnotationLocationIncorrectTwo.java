/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

public class InputAnnotationLocationIncorrectTwo {
   @MyAnnotation1
            (value = "")
@MyAnn_21 // violation '.* incorrect .* level 0, .* should be 3.'
   class Foo {
        public void method1(@MyAnnotation3 @MyAnn_21 Object param1) {
            try {
            }
            catch (@MyAnnotation3 @MyAnn_21 Exception e) {
            }
        }
    }

}
