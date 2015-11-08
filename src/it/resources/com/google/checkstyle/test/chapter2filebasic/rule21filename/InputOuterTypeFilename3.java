package com.google.checkstyle.test.chapter2filebasic.rule21filename;

@interface MyAnnotation2 { //warn
    String name();
    int version();
}

@MyAnnotation2(name = "ABC", version = 1)
class InputOuterTypeFilename3 //ok
{

}

enum Enum2 //ok
{
    A, B, C;
    Enum2() {}
    public String toString() {
        return ""; //some custom implementation
    }
}

interface TestRequireThisEnum2 //ok
{
    enum DAY_OF_WEEK
    {
        SUNDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }
}


