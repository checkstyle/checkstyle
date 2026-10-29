/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;
int first = 1; // violation 'should be separated from previous line'
int second = 2; // violation 'should be separated from previous line'

int third = 3;

void helper() {
    int value = first;


    System.out.println(value);
}
void next() { // violation 'should be separated from previous line'
    System.out.println(second);
}


void separated() {
    System.out.println(List.of(first, second, third));
}

void main() {}
