/*
TypeBodyPadding
atStartOfBody = (default)true
atEndOfBody = (default)true
allowEmpty = (default)true
skipLocal = (default)true
skipInner = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

public class InputTypeBodyPaddingDefault {

    private int a;
} // violation 'Blank line required before the closing brace of type definition.'

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

class MissingStart { // violation 'Blank line required after the opening brace of type definition.'
    private int a;

}

class MissingEnd {

    private int a;
} // violation 'Blank line required before the closing brace of type definition.'

class MissingBoth { // violation 'Blank line required after the opening brace of type definition.'
    private int a;
} // violation 'Blank line required before the closing brace of type definition.'

class WithComment { // violation 'Blank line required after the opening brace of type definition.'
    // This is field a
    private int a;

}

class WithCommentAtEnd {

    private int a;
    // Comment line is not treated as blank line
} // violation 'Blank line required before the closing brace of type definition.'

interface AllowedInterface {

    void method();

}

// violation below 'Blank line required after the opening brace of type definition.'
interface MissingBothInterface {
    void method();
} // violation 'Blank line required before the closing brace of type definition.'

enum AllowedEnum {

    VALUE_A,
    VALUE_B;

}

// violation below 'Blank line required after the opening brace of type definition.'
enum MissingBothEnum {
    VALUE_A,
    VALUE_B;
} // violation 'Blank line required before the closing brace of type definition.'

record AllowedRecord(int x) {

    void method() {}

}

// violation below 'Blank line required after the opening brace of type definition.'
record MissingBothRecord(int x) {
    void method() {}
} // violation 'Blank line required before the closing brace of type definition.'

@interface AllowedAnnotation {

    String value();

}

// violation below 'Blank line required after the opening brace of type definition.'
@interface MissingBothAnnotation {
    String value();
} // violation 'Blank line required before the closing brace of type definition.'

