/*
AnnotatedDeclarationVisibility
annotations = java.lang.Deprecated
visibility = protected
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF,


*/

// non-compiled with javac: Compilable with Java25

@Deprecated
protected void allowedMethod() {}

// violation below 'Annotated element has disallowed visibility 'private'.'
@Deprecated
@SuppressWarnings("unused")
private void violationPrivateMethod() {}

// violation below 'Annotated element has disallowed visibility 'public'.'
@Deprecated
public void violationPublicMethod() {}

// violation below 'Annotated element has disallowed visibility 'package-private'.'
@Deprecated
void violationPackagePrivate() {}

void main() {}
