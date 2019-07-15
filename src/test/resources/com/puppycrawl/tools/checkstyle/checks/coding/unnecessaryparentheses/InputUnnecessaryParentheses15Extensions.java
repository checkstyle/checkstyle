// someexamples of 1.5 extensions
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessaryparentheses;

@interface MyAnnotation1 {
    String name();
    int version();
}

@MyAnnotation1(name = "ABC", version = 1)
public class InputUnnecessaryParentheses15Extensions
{

}

@MyAnnotation1(name = ("ABC" + "DEF"), version = (1))
class AnnotationWithUnnecessaryParentheses
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
