package com.puppycrawl.tools.checkstyle.annotation;




@MyAnnotation6
@MyAnnotation5
class InputCorrectAnnotationLocation
{
    
    @MyAnnotation6
    @MyAnnotation5
    public int a;

    @MyAnnotation5
    public int b;
    
    @MyAnnotation6
    @MyAnnotation5
    public int c;
    
    @MyAnnotation5
    public int d;
    
    @MyAnnotation6
    @MyAnnotation5
    public InputCorrectAnnotationLocation()
    {
        // TODO Auto-generated constructor stub
    }
    @MyAnnotation5
    @MyAnnotation6
    void foo1() {}
    
    @MyAnnotation5
    @MyAnnotation6
    void foo2() {}
    
    @MyAnnotation5
    @MyAnnotation6
    @MyAnnotation3
    @MyAnnotation4
    class InnerClass
    {
        @MyAnnotation6
        @MyAnnotation5
        public int a;

        @MyAnnotation5
        public int b;
        
        @MyAnnotation6
        @MyAnnotation5
        public int c;
        
        @MyAnnotation5
        public int d;
        
        @MyAnnotation6
        public InnerClass()
        {
            // TODO Auto-generated constructor stub
        }
        @MyAnnotation5
        @MyAnnotation6 void foo1(int a) {}
        
        @MyAnnotation5
        @MyAnnotation6
        void foo2() {}
    }

    @MyAnnotation5
    @MyAnnotation6
    InnerClass anon = new InnerClass()
    {
        @MyAnnotation6
        @MyAnnotation5
        public int a;

        @MyAnnotation5 public int b;
        
        @MyAnnotation6
        @MyAnnotation5
        public int c;
        
        @MyAnnotation5
        public int d;
        
        @MyAnnotation5
        @MyAnnotation6 void foo1() {}
        
        @MyAnnotation5
        @MyAnnotation6
        void foo2() {}
    };
    
}

@MyAnnotation5
@MyAnnotation6
class _Foo {}

@interface MyAnnotation5 {}

@interface MyAnnotation6 {}

