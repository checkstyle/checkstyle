/*
UnnecessarySemicolonAfterOuterTypeDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonafteroutertypedeclaration;

public record InputUnnecessarySemicolonAfterOuterTypeDeclarationRecords() {
    public record MyInnerRecord() {

    }

    ; // ok, nested type declarations are ignored in this check
}; // violation

record OuterRecord() {
    record InnerRecord() {

    }; // ok
}; // violation
