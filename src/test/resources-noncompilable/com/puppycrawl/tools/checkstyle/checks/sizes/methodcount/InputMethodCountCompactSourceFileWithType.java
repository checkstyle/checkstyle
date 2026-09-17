/*
MethodCount
maxTotal = 2
maxPrivate = (default)100
maxPackage = (default)100
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, ANNOTATION_DEF, \
         METHOD_DEF, RECORD_DEF, COMPACT_COMPILATION_UNIT

*/

// non-compiled with javac: Compilable with Java25

void m1() {} // violation 'Total number of methods is 3 (max allowed is 2).'

void m2() {}

class Inner { // violation 'Total number of methods is 3 (max allowed is 2).'
    void x() {}

    void y() {}

    void z() {}
}

void main() {}
