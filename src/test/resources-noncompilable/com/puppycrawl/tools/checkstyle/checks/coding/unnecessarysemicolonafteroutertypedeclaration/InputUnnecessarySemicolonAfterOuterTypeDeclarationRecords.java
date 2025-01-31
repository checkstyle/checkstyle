/*
UnnecessarySemicolonAfterOuterTypeDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarysemicolonafteroutertypedeclaration;

public record InputUnnecessarySemicolonAfterOuterTypeDeclarationRecords() {
    public record MyInnerRecord() {

    }

    ; //nested type declarations are ignored in this check
}; // violation 'Unnecessary semicolon'

record OuterRecord() {
    record InnerRecord() {

    };
}; // violation 'Unnecessary semicolon'
