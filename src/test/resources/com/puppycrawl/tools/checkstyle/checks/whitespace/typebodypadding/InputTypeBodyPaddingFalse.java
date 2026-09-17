/*
TypeBodyPadding
atStartOfBody = false
atEndOfBody = false
allowEmpty = (default)true
skipLocal = (default)true
skipInner = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

public class InputTypeBodyPaddingFalse {

    private int a;
}

class Allowed1False {

    private int a;

}

class Allowed2False
{

    private int a;

}

class Empty1False {}

class Empty2False {
}

class Empty3False
{}

class Empty4False
{
}

class WithBlankLineOnlyFalse {

}

class MissingStartFalse {
    private int a;

}

class MissingEndFalse {

    private int a;
}

class MissingBothFalse {
    private int a;
}

class WithCommentFalse {
    // This is field a
    private int a;

}

interface AllowedInterfaceFalse {

    void method();

}

interface MissingBothInterfaceFalse {
    void method();
}

enum AllowedEnumFalse {

    VALUE_A,
    VALUE_B;

}

enum MissingBothEnumFalse {
    VALUE_A,
    VALUE_B;
}

record AllowedRecordFalse(int x) {

    void method() {}

}

record MissingBothRecordFalse(int x) {
    void method() {}
}

@interface AllowedAnnotationFalse {

    String value();

}

@interface MissingBothAnnotationFalse {
    String value();
}

class SameLineFalse1 {}

class SameLineFalse2 { int a; }

