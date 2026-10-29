/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = true
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

int first = 1;
int second = 2;

int third = 3;
void helper() { // violation 'should be separated from previous line'
    int value = first; // violation 'There is more than 1 empty line after this line.'


    System.out.println(value + second + third);
}


void afterMultiple() { // violation ''METHOD_DEF' has more than 1 empty lines before.'
    System.out.println(first);
}

void main() {}
