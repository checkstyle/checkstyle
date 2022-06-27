/*
AnnotationLocation
allowSamelineMultipleAnnotations = true
allowSamelineSingleParameterlessAnnotation = (default)true
allowSamelineParameterizedAnnotation = true
tokens = (default)CLASS_DEF, INTERFACE_DEF, PACKAGE_DEF, ENUM_CONSTANT_DEF, \
         ENUM_DEF, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

@MyAnn_22 @com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.MyAnn_2 // ok
(value = "")
class InputAnnotationLocationIncorrect2
{

    @MyAnn_22 @MyAnnotation_12(value = "") // ok
    public int a;

    @MyAnnotation_12(value = "") public int b; // ok

    @MyAnn_22 // ok
        // violation below '.*'MyAnnotation_12' have incorrect indentation level 8, .* should be 4.'
        @MyAnnotation_12
(value = "")
    public int c;

    @MyAnnotation_12(value = "") // ok
    public int d;

    @MyAnn_22 // ok
        // violation below '.*'MyAnnotation_12' have incorrect indentation level 8, .* should be 4.'
        @MyAnnotation_12
(value = "")
    public InputAnnotationLocationIncorrect2() {}

    @MyAnnotation_12("foo") @MyAnn_22 void foo1() {} // ok

    @MyAnnotation_12(value = "") // ok
       @MyAnn_22 // violation '.*'MyAnn_22' have incorrect indentation level 7, .* should be 4.'
    void foo2() {}

    @MyAnnotation_12(value = "") // ok
        @MyAnn_22 // violation '.*'MyAnn_22' have incorrect indentation level 8, .* should be 4.'
      // violation below '.*'MyAnnotation_32' have incorrect indentation level 6, .* should be 4.'
      @MyAnnotation_32
          // violation below '.*'MyAnnotation_42' .* incorrect indentation level 10,.*should be 4.'
          @MyAnnotation_42
    class InnerClass
    {
        @MyAnn_22 @MyAnnotation_12 // ok
(value = "")
        public int a;

        @MyAnnotation_12(value = "") public int b; // ok

        @MyAnn_22 // ok
            // violation below '.*'MyAnnotation_12'.*incorrect indentation level 12,.*should be 8.'
            @MyAnnotation_12
(value = "")
        public int c;

        @MyAnnotation_12(value = "") // ok
        public int d;

        @MyAnn_22 // ok
        @MyAnnotation_12(value = "") public InnerClass() // ok
        {
            // comment
        }
        @MyAnnotation_12(value = "") // ok
            // violation below '.*'MyAnn_22' have incorrect indentation level 12, .* should be 8.'
            @MyAnn_22
        void foo1() {}

        @MyAnnotation_12(value = "") // ok
            // violation below '.*'MyAnn_22' have incorrect indentation level 12, .* should be 8.'
            @MyAnn_22
        void foo2() {}
    }

    @MyAnnotation_12(value = "") // ok
       // violation below 'Annotation 'MyAnn_22' have incorrect indentation level 7,.*should be 4.'
       @MyAnn_22
    InnerClass anon = new InnerClass()
    {
        @MyAnn_22 @MyAnnotation_12(value = "") public int a; // ok

        @MyAnnotation_12(value = "") public int b; // ok

        @MyAnn_22 // ok
        @MyAnnotation_12(value = "") // ok
        public int c;

        @MyAnnotation_12(value = "") // ok
        public int d;

        @MyAnnotation_12(value = "") // ok
           // violation below '.*'MyAnn_22' have incorrect indentation level 11, .* should be 8.'
           @MyAnn_22 void foo1() {}

        @MyAnnotation_12(value = "") // ok
          // violation below '.*'MyAnn_22' have incorrect indentation level 10, .* should be 8.'
          @MyAnn_22
        void foo2() {}

        @MyAnnotation_12(value = "") void foo42() {} // ok
    };

}

   @MyAnnotation_12 // ok
(value = "")
@MyAnn_22 // violation '.*'MyAnn_22' have incorrect indentation level 0, .* should be 3.'
class Foo2 {
    public void method1(@MyAnnotation_32 @MyAnn_22 Object param1) { // ok
        try {
        }
        catch (@MyAnnotation_32 @MyAnn_22 Exception e) { // ok
        }
        return;
    }
}

@interface MyAnnotation_12 {

        String value();}

@interface MyAnn_22 {}

@interface MyAnnotation_32 {}

@interface MyAnnotation_42 {}

@interface MyAnn_2 {

    String value();}
