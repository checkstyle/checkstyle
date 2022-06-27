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
class InputAnnotationLocationIncorrect
{

    @MyAnn_21 @MyAnnotation1(value = "") // violation '.*'MyAnnotation1' should be alone on line.'
    public int a;

    // violation below 'Annotation 'MyAnnotation1' should be alone on line.'
    @MyAnnotation1(value = "") public int b;

    @MyAnn_21 // ok
        // violation below '.* 'MyAnnotation1' have incorrect indentation level 8, .* should be 4.'
        @MyAnnotation1
(value = "")
    public int c;

    @MyAnnotation1(value = "") // ok
    public int d;

    @MyAnn_21 // ok
        // violation below '.* 'MyAnnotation1' have incorrect indentation level 8, .* should be 4.'
        @MyAnnotation1
(value = "")
    public InputAnnotationLocationIncorrect() {}

    @MyAnnotation1("foo") @MyAnn_21 void foo1() {} // 2 violations

    @MyAnnotation1(value = "") // ok
       // violation below 'Annotation 'MyAnn_21' have incorrect indentation level 7, .*should be 4.'
       @MyAnn_21
    void foo2() {}

    @MyAnnotation1(value = "") // ok
        // violation below 'Annotation 'MyAnn_21' have incorrect indentation level 8,.*should be 4.'
        @MyAnn_21
      // violation below '.* 'MyAnnotation3' have incorrect indentation level 6, .* should be 4.'
      @MyAnnotation3
          // violation below '.*'MyAnnotation4' have incorrect indentation level 10, .*should be 4.'
          @MyAnnotation4
    class InnerClass
    {
        @MyAnn_21 @MyAnnotation1 // violation 'Annotation 'MyAnnotation1' should be alone on line.'
(value = "")
        public int a;

        // violation below 'Annotation 'MyAnnotation1' should be alone on line.'
        @MyAnnotation1(value = "") public int b;

        @MyAnn_21 // ok
            // violation below '.*'MyAnnotation1' .* incorrect indentation level 12,.*should be 8.'
            @MyAnnotation1
(value = "")
        public int c;

        @MyAnnotation1(value = "") // ok
        public int d;

        @MyAnn_21 // ok
        // violation below 'Annotation 'MyAnnotation1' should be alone on line.'
        @MyAnnotation1(value = "") public InnerClass()
        {
            // comment
        }
        @MyAnnotation1(value = "")
            // violation below '.*'MyAnn_21' have incorrect indentation level 12, .* should be 8.'
            @MyAnn_21
        void foo1() {}

        @MyAnnotation1(value = "")
            // violation below '.*'MyAnn_21' have incorrect indentation level 12, .* should be 8.'
            @MyAnn_21
        void foo2() {}
    }

    @MyAnnotation1(value = "")
       // violation below 'Annotation 'MyAnn_21' have incorrect indentation level 7,.*should be 4.'
       @MyAnn_21
    InnerClass anon = new InnerClass()
    {
        // violation below 'Annotation 'MyAnnotation1' should be alone on line.'
        @MyAnn_21 @MyAnnotation1(value = "") public int a;

        // violation below 'Annotation 'MyAnnotation1' should be alone on line.'
        @MyAnnotation1(value = "") public int b;

        @MyAnn_21 // ok
        @MyAnnotation1(value = "") // ok
        public int c;

        @MyAnnotation1(value = "") // ok
        public int d;

        @MyAnnotation1(value = "") // ok
           // violation below '.*'MyAnn_21' have incorrect indentation level 11, .* should be 8.'
           @MyAnn_21 void foo1() {}

        @MyAnnotation1(value = "") // ok
          // violation below '.* 'MyAnn_21' have incorrect indentation level 10, .* should be 8.'
          @MyAnn_21
        void foo2() {}
        // violation below 'Annotation 'MyAnnotation1' should be alone on line.'
        @MyAnnotation1(value = "") void foo42() {}
    };

}

   @MyAnnotation1 // ok
(value = "")
// violation below 'Annotation 'MyAnn_21' have incorrect indentation level 0, .* should be 3.'
@MyAnn_21
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
