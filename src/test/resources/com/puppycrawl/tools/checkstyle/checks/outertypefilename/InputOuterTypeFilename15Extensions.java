// someexamples of 1.5 extensions
package com.puppycrawl.tools.checkstyle.checks.outertypefilename;

@interface MyAnnotation1 {
    String name();
    int version();
}

@MyAnnotation1(name = "ABC", version = 1)
public class InputOuterTypeFilename15Extensions
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
