/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/


package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonaftertypememberdeclaration;

public record InputUnnecessarySemicolonAfterTypeMemberDeclarationRecords() {
    ; // violation 'Unnecessary semicolon.'

    static {}

    ; // violation 'Unnecessary semicolon.'

    static {}

    ; // violation 'Unnecessary semicolon.'

    public InputUnnecessarySemicolonAfterTypeMemberDeclarationRecords {
    }

    ; // violation 'Unnecessary semicolon.'

    public InputUnnecessarySemicolonAfterTypeMemberDeclarationRecords(Object o) {
        this();
    }

    ; // violation 'Unnecessary semicolon.'

    void method() {
    }

    ; // violation 'Unnecessary semicolon.'
    static int field = 10;
    ; // violation 'Unnecessary semicolon.'

    static {
        ; // ok, it is empty statement inside init block
    }

    static {
        ; // ok, it is empty statement inside static init block
    }

    void anotherMethod() {
        ; // ok, it is empty statement
        if (true) ; // ok, it is empty statement
    }
}; // ok, this check does not apply to outer types
