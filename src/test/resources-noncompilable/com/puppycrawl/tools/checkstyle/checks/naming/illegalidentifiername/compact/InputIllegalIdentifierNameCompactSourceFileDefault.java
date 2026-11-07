/*
IllegalIdentifierName
format = (default)^(?!var$|\\S*\\$)\\S+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int valid = 1;
    int var = 2; // violation 'Name 'var' must match pattern'
    int te$t = 3; // violation 'Name 'te\$t' must match pattern'
}

int other = 4;
int var = 5; // violation 'Name 'var' must match pattern'
