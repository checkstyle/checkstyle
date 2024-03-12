/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, \
         ANNOTATION_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF, PACKAGE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

public class InputAnnotationLocationIncorrect3Two {
   @MyAnnotation_13
            (value = "")
@MyAnn_23 // violation 'Annotation 'MyAnn_23' have incorrect indentation level 0, .* should be 3.'
   class Foo3 {
        public void method1(@MyAnnotation_33 @MyAnn_23 Object param1) {
            try {
            }
            catch (@MyAnnotation_33 @MyAnn_23 Exception e) {
            }
            return;
        }
   }
}
