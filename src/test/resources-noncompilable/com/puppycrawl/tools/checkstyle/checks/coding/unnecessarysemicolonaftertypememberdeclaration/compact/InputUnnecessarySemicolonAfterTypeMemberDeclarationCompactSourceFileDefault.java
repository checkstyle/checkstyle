/*
UnnecessarySemicolonAfterTypeMemberDeclaration
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, VARIABLE_DEF, \
         ANNOTATION_FIELD_DEF, STATIC_INIT, INSTANCE_INIT, CTOR_DEF, METHOD_DEF, \
         ENUM_CONSTANT_DEF, COMPACT_CTOR_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

void main() {}

class MyClass {
    MyClass() {}; // violation 'Unnecessary semicolon.'

    void method() {}; // violation 'Unnecessary semicolon.'
}
