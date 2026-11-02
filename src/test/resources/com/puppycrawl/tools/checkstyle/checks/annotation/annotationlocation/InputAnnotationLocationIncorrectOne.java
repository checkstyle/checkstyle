/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF, \
         MODULE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

// violation below 'Annotation 'MyAnn' should be alone on line.'
@MyAnn_21 @com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.MyAnn
(value = "")
class InputAnnotationLocationIncorrectOne
{

    // violation below '.* should be alone on line.'
    @MyAnn_21 @MyAnnotation1(value = "")
    public int a;
    // violation below '.* should be alone on line.'
    @MyAnnotation1(value = "") public int b;
    // violation 2 lines below '.* incorrect .* level 8, .* should be 4.'
    @MyAnn_21
        @MyAnnotation1
(value = "")
    public int c;
    @MyAnnotation1(value = "")
    public int d;
    // violation 2 lines below '.* incorrect .* level 8, .* should be 4.'
    @MyAnn_21
        @MyAnnotation1
(value = "")
    public InputAnnotationLocationIncorrectOne() {}
    @MyAnnotation1("foo") @MyAnn_21 void foo1() {}
    // 2 violations above:
    // 'Annotation 'MyAnnotation1' should be alone on line.'
    // 'Annotation 'MyAnn_21' should be alone on line.'

    // violation 2 lines below '.* incorrect .* level 7, .* should be 4.'
    @MyAnnotation1(value = "")
       @MyAnn_21
    void foo2() {}
    // violation 4 lines below '.* incorrect .* level 8, .* should be 4.'
    // violation 4 lines below '.* incorrect .* level 6, .* should be 4.'
    // violation 4 lines below '.* incorrect .* level 10, .* should be 4.'
    @MyAnnotation1(value = "")
        @MyAnn_21
      @MyAnnotation3
          @MyAnnotation4
    class InnerClass
    {
        // violation below 'Annotation 'MyAnnotation1' should be alone on line.'
        @MyAnn_21 @MyAnnotation1
(value = "")
        public int a;
        // violation below '.* should be alone on line.'
        @MyAnnotation1(value = "") public int b;
        // violation 2 lines below '.* incorrect .* level 12, .* should be 8.'
        @MyAnn_21
            @MyAnnotation1
(value = "")
        public int c;
        @MyAnnotation1(value = "")
        public int d;
        // violation 2 lines below '.* should be alone on line.'
        @MyAnn_21
        @MyAnnotation1(value = "") public InnerClass()
        {
            // comment
        }
        // violation 2 lines below '.* incorrect .* level 12, .* should be 8.'
        @MyAnnotation1(value = "")
            @MyAnn_21
        void foo1() {}
        // violation 2 lines below '.* incorrect .* level 12, .* should be 8.'
        @MyAnnotation1(value = "")
            @MyAnn_21
        void foo2() {}
    }

    // violation 2 lines below '.* incorrect .* level 7, .* should be 4.'
    @MyAnnotation1(value = "")
       @MyAnn_21
    InnerClass anon = new InnerClass()
    {
        // violation below '.*should be alone on line.'
        @MyAnn_21 @MyAnnotation1(value = "") public int a;
        // violation below '.* should be alone on line.'
        @MyAnnotation1(value = "") public int b;
        @MyAnn_21
        @MyAnnotation1(value = "")
        public int c;
        @MyAnnotation1(value = "")
        public int d;
        // violation 2 lines below '.* incorrect .* level 11, .* should be 8.'
        @MyAnnotation1(value = "")
           @MyAnn_21 void foo1() {}
        // violation 2 lines below '.* incorrect .* level 10, .* should be 8.'
        @MyAnnotation1(value = "")
          @MyAnn_21
        void foo2() {}
        // violation below '.* should be alone on line.'
        @MyAnnotation1(value = "") void foo42() {}
    };
}

@interface MyAnnotation1 {

        String value();}
@interface MyAnn_21 {}
@interface MyAnnotation3 {}
@interface MyAnnotation4 {}

@interface MyAnn {

    String value();}
