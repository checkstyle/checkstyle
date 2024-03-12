/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, \
         ANNOTATION_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF, PACKAGE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

// violation below 'Annotation 'MyAnn3' should be alone on line.'
@MyAnn_23 @com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.MyAnn3
(value = "")
class InputAnnotationLocationIncorrect3One
{

    @MyAnn_23 @MyAnnotation_13(value = "") // violation '.* should be alone on line.'
    public int a;

    @MyAnnotation_13(value = "") public int b; // violation '.* should be alone on line.'

    @MyAnn_23
        @MyAnnotation_13 // violation '.* incorrect .* level 8, .* should be 4.'
(value = "")
    public int c;

    @MyAnnotation_13(value = "")
    public int d;

    @MyAnn_23
        @MyAnnotation_13 // violation '.* incorrect .* level 8, .* should be 4.'
(value = "")
    public InputAnnotationLocationIncorrect3One() {}

    @MyAnnotation_13("foo") @MyAnn_23 void foo1() {} // 2 violations

    @MyAnnotation_13(value = "")
       @MyAnn_23 // violation '.* incorrect .* level 7, .* should be 4.'
    void foo2() {}

    @MyAnnotation_13(value = "")
        @MyAnn_23 // violation '.* incorrect .* level 8, .* should be 4.'
      @MyAnnotation_33 // violation '.* incorrect .* level 6, .* should be 4.'
          @MyAnnotation_43 // violation '.* incorrect .* level 10, .* should be 4.'
    class InnerClass3
    {
        @MyAnn_23 @MyAnnotation_13 // violation '.*'MyAnnotation_13' should be alone on line.'
(value = "")
        public int a;

        @MyAnnotation_13(value = "") public int b; // violation '.* should be alone on line.'

        @MyAnn_23
            @MyAnnotation_13 // violation '.* incorrect .* level 12, .* should be 8.'
(value = "")
        public int c;

        @MyAnnotation_13(value = "")
        public int d;

        @MyAnn_23
        @MyAnnotation_13(value = "") public InnerClass3() // violation '.* should be alone on line.'
        {
            // comment
        }
        @MyAnnotation_13(value = "")
            @MyAnn_23 // violation '.* incorrect .* level 12, .* should be 8.'
        void foo1() {}

        @MyAnnotation_13(value = "")
            @MyAnn_23 // violation '.*'MyAnn_23' have incorrect indentation level 12,.*should be 8.'
        void foo2() {}
    }

    @MyAnnotation_13(value = "")
       @MyAnn_23 // violation '.*'MyAnn_23' have incorrect indentation level 7,.*should be 4.'
    InnerClass3 anon = new InnerClass3()
    {
        // violation below 'Annotation 'MyAnnotation_13' should be alone on line.'
        @MyAnn_23 @MyAnnotation_13(value = "") public int a;

        @MyAnnotation_13(value = "") public int b; // violation '.* should be alone on line.'

        @MyAnn_23
        @MyAnnotation_13(value = "")
        public int c;

        @MyAnnotation_13(value = "")
        public int d;

        @MyAnnotation_13(value = "")
           @MyAnn_23 void foo1() {} // violation '.* incorrect .* level 11, .* should be 8.'

        @MyAnnotation_13(value = "")
          @MyAnn_23 // violation '.* incorrect .* level 10, .* should be 8.'
        void foo2() {}

        @MyAnnotation_13(value = "") void foo42() {} // violation '.* should be alone on line.'
    };

}

@interface MyAnnotation_13 {

        String value();}

@interface MyAnn_23 {}

@interface MyAnnotation_33 {}

@interface MyAnnotation_43 {}

@interface MyAnn3 {

    String value();}
