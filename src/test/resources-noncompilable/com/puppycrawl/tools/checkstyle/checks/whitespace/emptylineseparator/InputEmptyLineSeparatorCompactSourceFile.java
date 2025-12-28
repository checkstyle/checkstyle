/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = false
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;
import java.util.concurrent.Callable;

int a = 1;
int b = 2; // violation ''VARIABLE_DEF' should be separated from previous line.'
void main() { // violation ''METHOD_DEF' should be separated from previous line.'
    int local = 1;
}
/** Javadoc with no blank line after previous method. */
void second() { // violation ''METHOD_DEF' should be separated from previous line.'
    int local = 2; // violation 'There is more than 1 empty line after this line'


    int local2 = 3;
}
interface Nested { // violation ''INTERFACE_DEF' should be separated from previous line.'
    int X = 1;
}
class Inner { // violation ''CLASS_DEF' should be separated from previous line.'
    int inner = 0;
}
record Box(int v) { // violation ''RECORD_DEF' should be separated from previous line.'
}
