/*
TypeBodyPadding
starting = false
ending = false
allowEmpty = (default)true
skipLocal = (default)true
skipInner = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

public class InputTypeBodyPaddingFalse {

    private int a;
}

class Allowed1 {

    private int a;

}

class Allowed2
{

    private int a;

}

class Empty1 {}

class Empty2 {
}

class Empty3
{}

class Empty4
{
}

class WithBlankLineOnly {

}

class MissingStart {
    private int a;

}

class MissingEnd {

    private int a;
}

class MissingBoth {
    private int a;
}

class WithComment {
    // This is field a
    private int a;

}

interface AllowedInterface {

    void method();

}

interface MissingBothInterface {
    void method();
}

enum AllowedEnum {

    VALUE_A,
    VALUE_B;

}

enum MissingBothEnum {
    VALUE_A,
    VALUE_B;
}

record AllowedRecord(int x) {

    void method() {}

}

record MissingBothRecord(int x) {
    void method() {}
}

@interface AllowedAnnotation {

    String value();

}

@interface MissingBothAnnotation {
    String value();
}

class SameLineFalse {}

class SameLineFalse { int a; }

