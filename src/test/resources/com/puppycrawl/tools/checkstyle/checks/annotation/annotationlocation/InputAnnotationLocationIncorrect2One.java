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

@MyAnn_22 @com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.MyAnn_2
(value = "")
class InputAnnotationLocationIncorrect2One
{

    @MyAnn_22 @MyAnnotation_12(value = "")
    public int a;

    @MyAnnotation_12(value = "") public int b;

    @MyAnn_22
        @MyAnnotation_12 // violation '.* incorrect .* level 8, .* should be 4.'
(value = "")
    public int c;

    @MyAnnotation_12(value = "")
    public int d;

    @MyAnn_22
        @MyAnnotation_12 // violation '.* incorrect .* level 8, .* should be 4.'
(value = "")
    public InputAnnotationLocationIncorrect2One() {}

    @MyAnnotation_12("foo") @MyAnn_22 void foo1() {}

    // violation 2 lines below '.*'MyAnn_22' have incorrect indentation level 7, .* should be 4.'
    @MyAnnotation_12(value = "")
       @MyAnn_22
    void foo2() {}

    // violation 4 lines below '.* incorrect .* level 8, .* should be 4.'
    // violation 4 lines below '.* incorrect .* level 6, .* should be 4.'
    // violation 4 lines below '.* incorrect .* level 10, .* should be 4.'
    @MyAnnotation_12(value = "")
        @MyAnn_22
      @MyAnnotation_32
          @MyAnnotation_42
    class InnerClass
    {
        @MyAnn_22 @MyAnnotation_12
(value = "")
        public int a;

        @MyAnnotation_12(value = "") public int b;

        @MyAnn_22
            @MyAnnotation_12 // violation '.* incorrect .* level 12, .* should be 8.'
(value = "")
        public int c;

        @MyAnnotation_12(value = "")
        public int d;

        @MyAnn_22
        @MyAnnotation_12(value = "") public InnerClass()
        {
            // comment
        }
        // violation 2 lines below '.* incorrect .* level 12, .* should be 8.'
        @MyAnnotation_12(value = "")
            @MyAnn_22
        void foo1() {}

        // violation 2 lines below '.* incorrect .* level 12, .* should be 8.'
        @MyAnnotation_12(value = "")
            @MyAnn_22
        void foo2() {}
    }

    @MyAnnotation_12(value = "")
       @MyAnn_22 // violation '.* incorrect .* level 7, .* should be 4.'
    InnerClass anon = new InnerClass()
    {
        @MyAnn_22 @MyAnnotation_12(value = "") public int a;

        @MyAnnotation_12(value = "") public int b;

        @MyAnn_22
        @MyAnnotation_12(value = "")
        public int c;

        @MyAnnotation_12(value = "")
        public int d;

        @MyAnnotation_12(value = "")
           @MyAnn_22 void foo1() {} // violation '.* incorrect .* level 11, .* should be 8.'

        // violation 2 lines below '.* incorrect .* level 10, .* should be 8.'
        @MyAnnotation_12(value = "")
          @MyAnn_22
        void foo2() {}

        @MyAnnotation_12(value = "") void foo42() {}
    };

}

@interface MyAnnotation_12 {
        String value();}

@interface MyAnn_22 {}
@interface MyAnnotation_32 {}
@interface MyAnnotation_42 {}

@interface MyAnn_2 {
    String value();}
