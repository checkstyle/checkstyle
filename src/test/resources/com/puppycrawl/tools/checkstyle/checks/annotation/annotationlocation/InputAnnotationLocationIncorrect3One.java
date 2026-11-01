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

    // violation below '.* should be alone on line.'
    @MyAnn_23 @MyAnnotation_13(value = "")
    public int a;
    // violation below '.* should be alone on line.'
    @MyAnnotation_13(value = "") public int b;
    // violation 2 lines below '.* incorrect .* level 8, .* should be 4.'
    @MyAnn_23
        @MyAnnotation_13
(value = "")
    public int c;
    @MyAnnotation_13(value = "")
    public int d;
    // violation 2 lines below '.* incorrect .* level 8, .* should be 4.'
    @MyAnn_23
        @MyAnnotation_13
(value = "")
    public InputAnnotationLocationIncorrect3One() {}
    @MyAnnotation_13("foo") @MyAnn_23 void foo1() {}
    // 2 violations above:
    // 'Annotation 'MyAnnotation_13' should be alone on line.'
    // 'Annotation 'MyAnn_23' should be alone on line.'
    // violation 2 lines below '.* incorrect .* level 7, .* should be 4.'
    @MyAnnotation_13(value = "")
       @MyAnn_23
    void foo2() {}
    // violation 4 lines below '.* incorrect .* level 8, .* should be 4.'
    // violation 4 lines below '.* incorrect .* level 6, .* should be 4.'
    // violation 4 lines below '.* incorrect .* level 10, .* should be 4.'
    @MyAnnotation_13(value = "")
        @MyAnn_23
      @MyAnnotation_33
          @MyAnnotation_43
    class InnerClass3
    {
        // violation below '.*'MyAnnotation_13' should be alone on line.'
        @MyAnn_23 @MyAnnotation_13
(value = "")
        public int a;
        // violation below '.* should be alone on line.'
        @MyAnnotation_13(value = "") public int b;
        // violation 2 lines below '.* incorrect .* level 12, .* should be 8.'
        @MyAnn_23
            @MyAnnotation_13
(value = "")
        public int c;
        @MyAnnotation_13(value = "")
        public int d;
        // violation 2 lines below '.* should be alone on line.'
        @MyAnn_23
        @MyAnnotation_13(value = "") public InnerClass3()
        {
            // comment
        }
        // violation 2 lines below '.* incorrect .* level 12, .* should be 8.'
        @MyAnnotation_13(value = "")
            @MyAnn_23
        void foo1() {}
        // violation 2 lines below '.*'MyAnn_23' have incorrect indentation level 12,.*should be 8.'
        @MyAnnotation_13(value = "")
            @MyAnn_23
        void foo2() {}
    }

    // violation 2 lines below '.*'MyAnn_23' have incorrect indentation level 7,.*should be 4.'
    @MyAnnotation_13(value = "")
       @MyAnn_23
    InnerClass3 anon = new InnerClass3()
    {
        // violation below 'Annotation 'MyAnnotation_13' should be alone on line.'
        @MyAnn_23 @MyAnnotation_13(value = "") public int a;
        // violation below '.* should be alone on line.'
        @MyAnnotation_13(value = "") public int b;
        @MyAnn_23
        @MyAnnotation_13(value = "")
        public int c;
        @MyAnnotation_13(value = "")
        public int d;
        // violation 2 lines below '.* incorrect .* level 11, .* should be 8.'
        @MyAnnotation_13(value = "")
           @MyAnn_23 void foo1() {}
        // violation 2 lines below '.* incorrect .* level 10, .* should be 8.'
        @MyAnnotation_13(value = "")
          @MyAnn_23
        void foo2() {}
        // violation below '.* should be alone on line.'
        @MyAnnotation_13(value = "") void foo42() {}
    };

}

@interface MyAnnotation_13 {

        String value();}

@interface MyAnn_23 {}
@interface MyAnnotation_33 {}
@interface MyAnnotation_43 {}

@interface MyAnn3 {

    String value();}
