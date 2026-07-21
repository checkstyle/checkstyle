/*
AnnotatedDeclarationVisibility
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF


*/

// non-compiled with javac: Compilable with Java25

protected void method() {}

@Deprecated
public void publicMethod() {}

@Deprecated
protected record record1(int x) {}

private record record2(int x) {}

void main() {}
