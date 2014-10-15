package com.puppycrawl.tools.checkstyle.javadoc;

import test.MyAnnotation1;
import test.MyAnnotation2;

@MyAnnotation2
@MyAnnotation1
class InputCorrectAnnotationIndentation
{
    
    @MyAnnotation2
    @MyAnnotation1
    public int a;

    @MyAnnotation1
    public int b;
    
    @MyAnnotation2("foo")
    @MyAnnotation1
    public int c;
    
    @MyAnnotation1
    public int d;
    
    @MyAnnotation2
    @MyAnnotation1
    public InputCorrectAnnotationIndentation()
    {
        // TODO Auto-generated constructor stub
    }
    @MyAnnotation1
    @MyAnnotation2
    void foo1() {}
    
    @MyAnnotation1
    @MyAnnotation2
    void foo2() {}
    
    @MyAnnotation1
    @MyAnnotation2
    @MyAnnotation3
    @MyAnnotation4
    class InnerClass
    {
        @MyAnnotation2
        @MyAnnotation1
        public int a;

        @MyAnnotation1
        public int b;
        
        @MyAnnotation2
        @MyAnnotation1
        public int c;
        
        @MyAnnotation1
        public int d;
        
        @MyAnnotation2
        public InnerClass()
        {
            // TODO Auto-generated constructor stub
        }
        @MyAnnotation1
        @MyAnnotation2 void foo1(int a) {}
        
        @MyAnnotation1
        @MyAnnotation2
        void foo2() {}
    }

    @MyAnnotation1
    @MyAnnotation2
    InnerClass anon = new InnerClass()
    {
        @MyAnnotation2
        @MyAnnotation1
        public int a;

        @MyAnnotation1 public int b;
        
        @MyAnnotation2
        @MyAnnotation1
        public int c;
        
        @MyAnnotation1
        public int d;
        
        @MyAnnotation1
        @MyAnnotation2 void foo1() {}
        
        @MyAnnotation1
        @MyAnnotation2
        void foo2() {}
    };
    
}

@MyAnnotation1
@MyAnnotation2
class Foo {}

@interface MyAnnotation1 {}

@interface MyAnnotation2 {}

@interface MyAnnotation3 {}

@interface MyAnnotation4 {}
