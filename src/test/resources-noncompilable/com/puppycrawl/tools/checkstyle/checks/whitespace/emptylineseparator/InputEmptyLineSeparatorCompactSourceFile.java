/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, STATIC_INIT, INSTANCE_INIT, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;
int field1 = 1; // violation 'should be separated from previous line'
int field2 = 2; // violation 'should be separated from previous line'

int field3 = 3;

void m1() {
}
void m2() { // violation 'should be separated from previous line'
}

void main() {
    List<Integer> list = List.of(field1, field2, field3);
}

class Nested {
}
