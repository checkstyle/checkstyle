package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

@interface MyAnnotation2 {
    String name();
    int version();
}

@MyAnnotation2(name = "ABC", version = 1)
public class InputOuterTypeFilenameBeginTree1
{

}

enum Enum2
{
    A, B, C;
    Enum2() {}
    public String toString() {
        return ""; //some custom implementation
    }
}

interface TestRequireThisEnum2
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
