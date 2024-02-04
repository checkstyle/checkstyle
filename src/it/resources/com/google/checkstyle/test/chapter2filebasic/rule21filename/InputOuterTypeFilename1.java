package com.google.checkstyle.test.chapter2filebasic.rule21filename;

@interface MyAnnotation1 {
    String name();
    int version();
}

@MyAnnotation1(name = "ABC", version = 1)
public class InputOuterTypeFilename1
{

}

enum Enum1
{
    A, B, C;
    Enum1() {}
    public String toString() {
        return ""; //some custom implementation
    }
}

interface TestRequireThisEnum
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


