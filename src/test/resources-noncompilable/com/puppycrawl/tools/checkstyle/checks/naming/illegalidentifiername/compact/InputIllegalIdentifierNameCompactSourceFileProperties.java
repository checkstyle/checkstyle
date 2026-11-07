/*
IllegalIdentifierName
format = ^(?!foo$).+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int valid = 1;
    int foo = 2; // violation 'Name 'foo' must match pattern'
}

int other = 3;
int foo = 4; // violation 'Name 'foo' must match pattern'
