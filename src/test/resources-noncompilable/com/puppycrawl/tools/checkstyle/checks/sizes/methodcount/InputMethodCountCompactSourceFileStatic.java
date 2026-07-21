/*
MethodCount
maxTotal = (default)100
maxPrivate = (default)100
maxPackage = 1
maxProtected = (default)100
maxPublic = (default)100
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, ANNOTATION_DEF, \
         METHOD_DEF, RECORD_DEF, COMPACT_COMPILATION_UNIT

*/

// non-compiled with javac: Compilable with Java25

static void s1() {} // violation 'Number of package methods is 3 (max allowed is 1).'

static void s2() {}

void main() {}
