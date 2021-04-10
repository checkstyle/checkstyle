package com.puppycrawl.tools.checkstyle.checks.annotation.annotationlocation;

/* Config: default */


@MyAnnotation6 // ok
@MyAnnotation5 // ok
class InputAnnotationLocationCorrect
{

    @MyAnnotation6 // ok
    @MyAnnotation5 // ok
    public int a;

    @MyAnnotation5 // ok
    public int b;

    @MyAnnotation6 // ok
    @MyAnnotation5 // ok
    public int c;

    @MyAnnotation5 // ok
    public int d;

    @MyAnnotation6 // ok
    @MyAnnotation5 // ok
    public InputAnnotationLocationCorrect()
    {
        //comment
    }
    @MyAnnotation5 // ok
    @MyAnnotation6 // ok
    void foo1() {}

    @MyAnnotation5 // ok
    @MyAnnotation6 // ok
    void foo2() {}

    @MyAnnotation5 // ok
    @MyAnnotation6 // ok
    @MyAnnotation3 // ok
    @MyAnnotation4 // ok
    class InnerClass
    {
        @MyAnnotation6 // ok
        @MyAnnotation5 // ok
        public int a;

        @MyAnnotation5 // ok
        public int b;

        @MyAnnotation6 // ok
        @MyAnnotation5 // ok
        public int c;

        @MyAnnotation5 // ok
        public int d;

        @MyAnnotation6 // ok
        public InnerClass()
        {
            // comment
        }
        @MyAnnotation5 // ok
        @MyAnnotation6 void foo1(int a) {} // ok

        @MyAnnotation5 // ok
        @MyAnnotation6 // ok
        void foo2() {}
    }

    @MyAnnotation5 // ok
    @MyAnnotation6 // ok
    InnerClass anon = new InnerClass()
    {
        @MyAnnotation6 // ok
        @MyAnnotation5 // ok
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
