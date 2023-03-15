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

    ; // violation

    static {}

    ; // violation

    public InputUnnecessarySemicolonAfterTypeMemberDeclarationRecords {
    }

    ; // violation

    public InputUnnecessarySemicolonAfterTypeMemberDeclarationRecords(Object o) {
        this();
    }

    ; // violation

    void method() {
    }

    ; // violation
    static int field = 10;
    ; // violation

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
