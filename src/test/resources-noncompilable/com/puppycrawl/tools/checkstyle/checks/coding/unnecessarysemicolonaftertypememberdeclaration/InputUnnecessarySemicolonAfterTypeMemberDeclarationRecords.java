/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonaftertypememberdeclaration;

public record InputUnnecessarySemicolonAfterTypeMemberDeclarationRecords() {
    ; // violation

    static {}

    ; // violation, extra semicolon after init block

    static {}

    ; // violation, extra semicolon after static init block

    public InputUnnecessarySemicolonAfterTypeMemberDeclarationRecords {
    }

    ; // violation, extra semicolon after compact constructor definition

    public InputUnnecessarySemicolonAfterTypeMemberDeclarationRecords(Object o) {
        this();
    }

    ; // violation, extra semicolon after constructor definition

    void method() {
    }

    ; // violation, extra semicolon after method definition
    static int field = 10;
    ; // violation, extra semicolon after field declaration

    static {
        ; // no violation, it is empty statement inside init block
    }

    static {
        ; // no violation, it is empty statement inside static init block
    }

    void anotherMethod() {
        ; // no violation, it is empty statement
        if (true) ; // no violation, it is empty statement
    }
}; // ok, this check does not apply to outer types
