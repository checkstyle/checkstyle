package com.google.checkstyle.test.chapter4formatting.rule485annotations;

@MyAnnotation2 @MyAnnotation1 // in variables config
class InputAnnotationLocationVariables
{

    @MyAnnotation2 @MyAnnotation1
    public int a;

    @MyAnnotation1 public int b;

    @MyAnnotation2
    @MyAnnotation1
    public int c;

    @MyAnnotation1
    public int d;

    @MyAnnotation2
        @MyAnnotation1 // in variables config
    public InputAnnotationLocationVariables() {}

    @MyAnnotationWithParam("foo")
    @MyAnnotation2 void foo1() {}

    @MyAnnotation1
       @MyAnnotation2 // in variables config
    void foo2() {}

    @MyAnnotation1
        @MyAnnotation2 //  in variables config
      @MyAnnotation3 //  in variables configwarn
          @MyAnnotation4 // in variables config
    class InnerClass
    {
        @MyAnnotation2 @MyAnnotation1
        public int a;

        @MyAnnotation1 public int b;

        @MyAnnotation2
        @MyAnnotation1
        public int c;

        @MyAnnotation1
        public int d;

        @MyAnnotation2
        @MyAnnotation1 public InnerClass()
        {
            // OOOO Auto-generated constructor stub
        }
        @MyAnnotation1
            @MyAnnotation2 // in variables config
        void foo1() {}

        @MyAnnotation1
            @MyAnnotation2 // in variables config
        void foo2() {}
    }

    @MyAnnotation1
       @MyAnnotation2 //warn
    InnerClass anon = new InnerClass()
    {
        @MyAnnotation2 @MyAnnotation1 public int a;

        @MyAnnotation1 public int b;

        @MyAnnotation2
        @MyAnnotation1
        public int c;

        @MyAnnotation1
        public int d;

        @MyAnnotation1
           @MyAnnotation2 void foo1() {} // in variables config

        @MyAnnotation1
          @MyAnnotation2 //  in variables config
        void foo2() {}

        @MyAnnotation1 void foo42() {}
    };

}

@MyAnnotation1
 @MyAnnotation2 // in variables config
class FooVariables {}

@interface MyAnnotationVariables1 {}

@interface MyAnnotationVariables2 {}

@interface MyAnnotationVariables3 {}

@interface MyAnnotationVariables4 {}

@interface MyAnnotationWithParamVariables {

    String value();}
