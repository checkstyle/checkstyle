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
class InputAnnotationLocationIncorrect3
{

    // violation below 'Annotation 'MyAnnotation_13' should be alone on line.'
    @MyAnn_23 @MyAnnotation_13(value = "")
    public int a;

    // violation below 'Annotation 'MyAnnotation_13' should be alone on line.'
    @MyAnnotation_13(value = "") public int b;

    @MyAnn_23 // ok
        // violation below '.*'MyAnnotation_13' have incorrect indentation level 8,.*should be 4.'
        @MyAnnotation_13
(value = "")
    public int c;

    @MyAnnotation_13(value = "") // ok
    public int d;

    @MyAnn_23
        // violation below '.*'MyAnnotation_13' have incorrect indentation level 8,.*should be 4.'
        @MyAnnotation_13
(value = "")
    public InputAnnotationLocationIncorrect3() {}

    @MyAnnotation_13("foo") @MyAnn_23 void foo1() {} // 2 violations

    @MyAnnotation_13(value = "") // ok
       // violation below 'Annotation 'MyAnn_23' have incorrect indentation level 7,.*should be 4.'
       @MyAnn_23
    void foo2() {}

    @MyAnnotation_13(value = "") // ok
        // violation below 'Annotation 'MyAnn_23' have incorrect indentation level 8,.*should be 4.'
        @MyAnn_23
      // violation below '.*'MyAnnotation_33' have incorrect indentation level 6,.*should be 4.'
      @MyAnnotation_33
          // violation below '.*'MyAnnotation_43' .* incorrect indentation level 10,.*should be 4.'
          @MyAnnotation_43
    class InnerClass3
    {
        @MyAnn_23 @MyAnnotation_13 // violation '.*'MyAnnotation_13' should be alone on line.'
(value = "")
        public int a;

        // violation below 'Annotation 'MyAnnotation_13' should be alone on line.'
        @MyAnnotation_13(value = "") public int b;

        @MyAnn_23 // ok
            // violation below '.*'MyAnnotation_13'.*incorrect indentation level 12,.*should be 8.'
            @MyAnnotation_13
(value = "")
        public int c;

        @MyAnnotation_13(value = "") // ok
        public int d;

        @MyAnn_23 // ok
        // violation below 'Annotation 'MyAnnotation_13' should be alone on line.'
        @MyAnnotation_13(value = "") public InnerClass3()
        {
            // comment
        }
        @MyAnnotation_13(value = "") // ok
            // violation below '.*'MyAnn_23' have incorrect indentation level 12,.*should be 8.'
            @MyAnn_23
        void foo1() {}

        @MyAnnotation_13(value = "") // ok
            @MyAnn_23 // violation '.*'MyAnn_23' have incorrect indentation level 12,.*should be 8.'
        void foo2() {}
    }

    @MyAnnotation_13(value = "") // ok
       @MyAnn_23 // violation '.*'MyAnn_23' have incorrect indentation level 7,.*should be 4.'
    InnerClass3 anon = new InnerClass3()
    {
        // violation below 'Annotation 'MyAnnotation_13' should be alone on line.'
        @MyAnn_23 @MyAnnotation_13(value = "") public int a;

        // violation below 'Annotation 'MyAnnotation_13' should be alone on line.'
        @MyAnnotation_13(value = "") public int b;

        @MyAnn_23 // ok
        @MyAnnotation_13(value = "") // ok
        public int c;

        @MyAnnotation_13(value = "") // ok
        public int d;

        @MyAnnotation_13(value = "") // ok
           // violation below '.*'MyAnn_23' have incorrect indentation level 11,.*should be 8.'
           @MyAnn_23 void foo1() {}

        @MyAnnotation_13(value = "") // ok
          // violation below '.*'MyAnn_23' have incorrect indentation level 10, .* should be 8.'
          @MyAnn_23
        void foo2() {}

        // violation below 'Annotation 'MyAnnotation_13' should be alone on line.'
        @MyAnnotation_13(value = "") void foo42() {}
    };

}

   @MyAnnotation_13 // ok
(value = "")
@MyAnn_23 // violation 'Annotation 'MyAnn_23' have incorrect indentation level 0, .* should be 3.'
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
