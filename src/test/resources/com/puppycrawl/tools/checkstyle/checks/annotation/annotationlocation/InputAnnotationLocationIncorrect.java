/*
AnnotationLocation
allowSamelineMultipleAnnotations = (default)false
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = (default)false
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

@MyAnn_21 @com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.MyAnn // violation '@com' should be on one line
(value = "")
class InputAnnotationLocationIncorrect
{

    @MyAnn_21 @MyAnnotation1(value = "") // violation '@MyAnnotaion1..' should be on one line
    public int a;

    @MyAnnotation1(value = "") public int b; // violation 'public ..' should be on one line

    @MyAnn_21 // ok
        @MyAnnotation1 // violation indentation should not be given
(value = "")
    public int c;

    @MyAnnotation1(value = "") // ok
    public int d;

    @MyAnn_21 // ok
        @MyAnnotation1 // violation indentation should not be given
(value = "")
    public InputAnnotationLocationIncorrect() {}

    @MyAnnotation1("foo") @MyAnn_21 void foo1() {} // 2 violations, '@MyAnn.. void..' should be on different lines

    @MyAnnotation1(value = "") // ok
       @MyAnn_21 // violation indentation should not be given
    void foo2() {}

    @MyAnnotation1(value = "") // ok
        @MyAnn_21 // violation indentation should not be given
      @MyAnnotation3 // violation indentation should not be given
          @MyAnnotation4 // violation indentation should not be given
    class InnerClass
    {
        @MyAnn_21 @MyAnnotation1 // violation '@MyAnnotation1' should be on one line
(value = "")
        public int a;

        @MyAnnotation1(value = "") public int b; // violation 'public..' should be on one line

        @MyAnn_21 // ok
            @MyAnnotation1 // violation indentation should not be given
(value = "")
        public int c;

        @MyAnnotation1(value = "") // ok
        public int d;

        @MyAnn_21 // ok
        @MyAnnotation1(value = "") public InnerClass() // violation 'public..' should be one one line
        {
            // comment
        }
        @MyAnnotation1(value = "")
            @MyAnn_21 // violation indentation should not be given
        void foo1() {}

        @MyAnnotation1(value = "")
            @MyAnn_21 // violation indentation should not be given
        void foo2() {}
    }

    @MyAnnotation1(value = "")
       @MyAnn_21 // violation
    InnerClass anon = new InnerClass()
    {
        @MyAnn_21 @MyAnnotation1(value = "") public int a; // violation 'public..' should be on one line

        @MyAnnotation1(value = "") public int b; // violation 'public..' should be on one line

        @MyAnn_21 // ok
        @MyAnnotation1(value = "") // ok
        public int c;

        @MyAnnotation1(value = "") // ok
        public int d;

        @MyAnnotation1(value = "") // ok
           @MyAnn_21 void foo1() {} // violation 'void..' should be on one line

        @MyAnnotation1(value = "") // ok
          @MyAnn_21 // violation indentation should not be given
        void foo2() {}

        @MyAnnotation1(value = "") void foo42() {} // violation 'void..' should be on one line
    };

}

   @MyAnnotation1 // ok
(value = "")
@MyAnn_21 // violation should be before 'value'
class Foo {
    public void method1(@MyAnnotation3 @MyAnn_21 Object param1) {
        try {
        }
        catch (@MyAnnotation3 @MyAnn_21 Exception e) {
        }
        return;
    }
}

@interface MyAnnotation1 {

        String value();}

@interface MyAnn_21 {}

@interface MyAnnotation3 {}

@interface MyAnnotation4 {}

@interface MyAnn {

    String value();}
