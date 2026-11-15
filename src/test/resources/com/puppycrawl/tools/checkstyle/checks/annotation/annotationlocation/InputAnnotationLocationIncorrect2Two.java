/*
AnnotationLocation
allowSamelineMultipleAnnotations = true
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = true
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF, \
         MODULE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;
public class InputAnnotationLocationIncorrect2Two {

   // violation 3 lines below '.*'MyAnn_22' have incorrect indentation level 0, .* should be 3.'
   @MyAnnotation_12
            (value = "")
@MyAnn_22
   class Foo2 {
        public void method1(@MyAnnotation_32 @MyAnn_22 Object param1) {
            try {
            }
            catch (@MyAnnotation_32 @MyAnn_22 Exception e) {
            }
        }
   }
}
