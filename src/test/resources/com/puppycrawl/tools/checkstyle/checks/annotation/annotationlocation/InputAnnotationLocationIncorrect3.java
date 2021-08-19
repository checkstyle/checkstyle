/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, \
         ANNOTATION_DEF, ANNOTATION_FIELD_DEF, ENUM_CONSTANT_DEF, PACKAGE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

@MyAnn_23 @com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.MyAnn3 // violation
(value = "")
class InputAnnotationLocationIncorrect3
{

    @MyAnn_23 @MyAnnotation_13(value = "") // violation
    public int a;

    @MyAnnotation_13(value = "") public int b; // violation

    @MyAnn_23 // ok
        @MyAnnotation_13 // violation
(value = "")
    public int c;

    @MyAnnotation_13(value = "") // ok
    public int d;

    @MyAnn_23
        @MyAnnotation_13 // violation
(value = "")
    public InputAnnotationLocationIncorrect3() {}

    @MyAnnotation_13("foo") @MyAnn_23 void foo1() {} // 2 violations

    @MyAnnotation_13(value = "") // ok
       @MyAnn_23 // violation
    void foo2() {}

    @MyAnnotation_13(value = "") // ok
        @MyAnn_23 // violation
      @MyAnnotation_33 // violation
          @MyAnnotation_43 // violation
    class InnerClass3
    {
        @MyAnn_23 @MyAnnotation_13 // violation
(value = "")
        public int a;

        @MyAnnotation_13(value = "") public int b; // violation

        @MyAnn_23 // ok
            @MyAnnotation_13 // violation
(value = "")
        public int c;

        @MyAnnotation_13(value = "") // ok
        public int d;

        @MyAnn_23 // ok
        @MyAnnotation_13(value = "") public InnerClass3() // violation
        {
            // comment
        }
        @MyAnnotation_13(value = "") // ok
            @MyAnn_23 // violation
        void foo1() {}

        @MyAnnotation_13(value = "") // ok
            @MyAnn_23 // violation
        void foo2() {}
    }

    @MyAnnotation_13(value = "") // ok
       @MyAnn_23 // violation
    InnerClass3 anon = new InnerClass3()
    {
        @MyAnn_23 @MyAnnotation_13(value = "") public int a; // violation

        @MyAnnotation_13(value = "") public int b; // violation

        @MyAnn_23 // ok
        @MyAnnotation_13(value = "") // ok
        public int c;

        @MyAnnotation_13(value = "") // ok
        public int d;

        @MyAnnotation_13(value = "") // ok
           @MyAnn_23 void foo1() {} // violation

        @MyAnnotation_13(value = "") // ok
          @MyAnn_23 // violation
        void foo2() {}

        @MyAnnotation_13(value = "") void foo42() {} // violation
    };

}

   @MyAnnotation_13 // ok
(value = "")
@MyAnn_23 // violation
class Foo3 {
    public void method1(@MyAnnotation_33 @MyAnn_23 Object param1) {
        try {
        }
        catch (@MyAnnotation_33 @MyAnn_23 Exception e) {
        }
        return;
    }
}

@interface MyAnnotation_13 {

        String value();}

@interface MyAnn_23 {}

@interface MyAnnotation_33 {}

@interface MyAnnotation_43 {}

@interface MyAnn3 {

    String value();}
