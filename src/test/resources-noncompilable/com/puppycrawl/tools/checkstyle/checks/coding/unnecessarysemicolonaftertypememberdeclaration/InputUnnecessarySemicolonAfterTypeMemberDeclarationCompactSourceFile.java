/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

class MemberClass {

}; // violation 'Unnecessary semicolon.'

interface MemberInterface {

}; // violation 'Unnecessary semicolon.'

enum MemberEnum {

}; // violation 'Unnecessary semicolon.'

@interface MemberAnnotation {

}; // violation 'Unnecessary semicolon.'

record MemberRecord() {

}; // violation 'Unnecessary semicolon.'

class OuterWithNested {

    class Nested {

    }; // violation 'Unnecessary semicolon.'

}; // violation 'Unnecessary semicolon.'

class LeadingSemicolon {
    ; // violation 'Unnecessary semicolon.'
}

enum EnumLeadingSemicolon {
    ;
}

int field;; // violation 'Unnecessary semicolon.'

void method() {

}; // violation 'Unnecessary semicolon.'

class CleanClass {

}

void main() {
}
