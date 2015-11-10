package com.google.checkstyle.test.chapter4formatting.rule485annotations;

@MyAnnotation2 @MyAnnotation1 //warn
class InputCorrectAnnotationIndentation
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
        @MyAnnotation1 //warn
    public InputCorrectAnnotationIndentation() {}

    @MyAnnotationWithParam("foo")
    @MyAnnotation2 void foo1() {} //warn

    @MyAnnotation1
       @MyAnnotation2 //warn
    void foo2() {}

    @MyAnnotation1
        @MyAnnotation2 //warn
      @MyAnnotation3 //warn
          @MyAnnotation4 //warn
    class InnerClass
    {
        @MyAnnotation2 @MyAnnotation1 //warn
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
            // TODO Auto-generated constructor stub
        }
        @MyAnnotation1
            @MyAnnotation2 //warn
        void foo1() {}
        
        @MyAnnotation1
            @MyAnnotation2 //warn
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
           @MyAnnotation2 void foo1() {} //warn
        
        @MyAnnotation1
          @MyAnnotation2 //warn
        void foo2() {}
        
        @MyAnnotation1 void foo42() {}
    };
    
}

@MyAnnotation1
 @MyAnnotation2 //warn
class Foo {}

@interface MyAnnotation1 {}

@interface MyAnnotation2 {}

@interface MyAnnotation3 {}

@interface MyAnnotation4 {}

@interface MyAnnotationWithParam {

	String value();}
