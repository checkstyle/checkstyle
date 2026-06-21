/*
MethodCount
maxTotal = (default)100
maxPrivate = 0
maxPackage = 1
maxProtected = 0
maxPublic = 0
tokens = (default)CLASS_DEF, ENUM_CONSTANT_DEF, ENUM_DEF, INTERFACE_DEF, ANNOTATION_DEF, \
         METHOD_DEF, RECORD_DEF, COMPACT_COMPILATION_UNIT

*/

// non-compiled with javac: Compilable with Java25

// violation 4 lines below 'Number of package methods is 2 (max allowed is 1).'
// violation 3 lines below 'Number of private methods is 1 (max allowed is 0).'
// violation 2 lines below 'Number of protected methods is 1 (max allowed is 0).'
// violation below 'Number of public methods is 1 (max allowed is 0).'
public void pub1() {}

private void prv1() {}

protected void pro1() {}

void pkg1() {}

void main() {}
