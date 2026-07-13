/*
AnnotatedDeclarationVisibility
annotations = java.lang.Deprecated
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

// non-compiled with javac: Compilable with Java25

@Deprecated
protected void allowedMethod() {}

// violation below 'Annotated element has disallowed visibility 'public'.'
@Deprecated
public void violationPublicMethod() {}

@Deprecated
protected record AllowedRecord(int x) {}

// violation below 'Annotated element has disallowed visibility 'private'.'
@Deprecated
private record ViolationRecord(int x) {}

void main() {}
