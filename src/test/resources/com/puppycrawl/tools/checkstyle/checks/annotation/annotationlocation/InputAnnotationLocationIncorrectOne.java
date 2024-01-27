/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

// violation below 'Annotation 'MyAnn' should be alone on line.'
@MyAnn_21 @com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.MyAnn
(value = "")
class InputAnnotationLocationIncorrectOne
{

    @MyAnn_21 @MyAnnotation1(value = "") // violation '.* should be alone on line.'
    public int a;

    @MyAnnotation1(value = "") public int b; // violation '.* should be alone on line.'

    @MyAnn_21
        @MyAnnotation1 // violation '.* incorrect .* level 8, .* should be 4.'
(value = "")
    public int c;

    @MyAnnotation1(value = "")
    public int d;

    @MyAnn_21
        @MyAnnotation1 // violation '.* incorrect .* level 8, .* should be 4.'
(value = "")
    public InputAnnotationLocationIncorrectOne() {}

    @MyAnnotation1("foo") @MyAnn_21 void foo1() {} // 2 violations

    @MyAnnotation1(value = "")
       @MyAnn_21 // violation '.* incorrect .* level 7, .* should be 4.'
    void foo2() {}

    @MyAnnotation1(value = "")
        @MyAnn_21 // violation '.* incorrect .* level 8, .* should be 4.'
      @MyAnnotation3 // violation '.* incorrect .* level 6, .* should be 4.'
          @MyAnnotation4 // violation '.* incorrect .* level 10, .* should be 4.'
    class InnerClass
    {
        @MyAnn_21 @MyAnnotation1 // violation 'Annotation 'MyAnnotation1' should be alone on line.'
(value = "")
        public int a;

        @MyAnnotation1(value = "") public int b; // violation '.* should be alone on line.'

        @MyAnn_21
            @MyAnnotation1 // violation '.* incorrect .* level 12, .* should be 8.'
(value = "")
        public int c;

        @MyAnnotation1(value = "")
        public int d;

        @MyAnn_21
        @MyAnnotation1(value = "") public InnerClass() // violation '.* should be alone on line.'
        {
            // comment
        }
        @MyAnnotation1(value = "")
            @MyAnn_21 // violation '.* incorrect .* level 12, .* should be 8.'
        void foo1() {}

        @MyAnnotation1(value = "")
            @MyAnn_21 // violation '.* incorrect .* level 12, .* should be 8.'
        void foo2() {}
    }

    @MyAnnotation1(value = "")
       @MyAnn_21 // violation '.* incorrect .* level 7, .* should be 4.'
    InnerClass anon = new InnerClass()
    {
        @MyAnn_21 @MyAnnotation1(value = "") public int a; // violation '.*should be alone on line.'

        @MyAnnotation1(value = "") public int b; // violation '.* should be alone on line.'

        @MyAnn_21
        @MyAnnotation1(value = "")
        public int c;

        @MyAnnotation1(value = "")
        public int d;

        @MyAnnotation1(value = "")
           @MyAnn_21 void foo1() {} // violation '.* incorrect .* level 11, .* should be 8.'

        @MyAnnotation1(value = "")
          @MyAnn_21 // violation '.* incorrect .* level 10, .* should be 8.'
        void foo2() {}
        @MyAnnotation1(value = "") void foo42() {} // violation '.* should be alone on line.'
    };

}


@interface MyAnnotation1 {

        String value();}

@interface MyAnn_21 {}

@interface MyAnnotation3 {}

@interface MyAnnotation4 {}

@interface MyAnn {

    String value();}
