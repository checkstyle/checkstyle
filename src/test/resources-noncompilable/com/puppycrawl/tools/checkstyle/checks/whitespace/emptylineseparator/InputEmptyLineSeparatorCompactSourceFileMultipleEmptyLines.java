/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

// a comment on the implicit class's first member

int field = 1;
void helper() { // violation 'should be separated from previous line'
}


void main() { // violation ''METHOD_DEF' has more than 1 empty lines before.'
}
