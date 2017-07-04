package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;




@MyAnnotation2 @com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation.MyAnnotation1 //warn
(value = "")
class InputAnnotationLocationIncorrect
{

    @MyAnnotation2 @MyAnnotation1(value = "")
    public int a;

    @MyAnnotation1(value = "") public int b;

    @MyAnnotation2
        @MyAnnotation1 //warn
(value = "")
    public int c;

    @MyAnnotation1(value = "")
    public int d;

    @MyAnnotation2
        @MyAnnotation1 //warn
(value = "")
    public InputAnnotationLocationIncorrect() {}

    @MyAnnotation1("foo") @MyAnnotation2 void foo1() {} //warn

    @MyAnnotation1(value = "")
       @MyAnnotation2 //warn
    void foo2() {}

    @MyAnnotation1(value = "")
        @MyAnnotation2 //warn
      @MyAnnotation3 //warn
          @MyAnnotation4 //warn
    class InnerClass
    {
        @MyAnnotation2 @MyAnnotation1 //warn
(value = "")
        public int a;

        @MyAnnotation1(value = "") public int b;
        
        @MyAnnotation2
            @MyAnnotation1 //warn
(value = "")
        public int c;
        
        @MyAnnotation1(value = "")
        public int d;
        
        @MyAnnotation2
        @MyAnnotation1(value = "") public InnerClass()
        {
            // comment
        }
        @MyAnnotation1(value = "")
            @MyAnnotation2 //warn
        void foo1() {}
        
        @MyAnnotation1(value = "")
            @MyAnnotation2 //warn
        void foo2() {}
    }

    @MyAnnotation1(value = "")
       @MyAnnotation2 //warn
    InnerClass anon = new InnerClass()
    {
        @MyAnnotation2 @MyAnnotation1(value = "") public int a;

        @MyAnnotation1(value = "") public int b;
        
        @MyAnnotation2
        @MyAnnotation1(value = "")
        public int c;
        
        @MyAnnotation1(value = "")
        public int d;
        
        @MyAnnotation1(value = "")
           @MyAnnotation2 void foo1() {} //warn
        
        @MyAnnotation1(value = "")
          @MyAnnotation2 //warn
        void foo2() {}
        
        @MyAnnotation1(value = "") void foo42() {}
    };
    
}

   @MyAnnotation1 //warn
(value = "")
@MyAnnotation2
class Foo {}

@interface MyAnnotation1 {

	String value();}

@interface MyAnnotation2 {}

@interface MyAnnotation3 {}

@interface MyAnnotation4 {}
