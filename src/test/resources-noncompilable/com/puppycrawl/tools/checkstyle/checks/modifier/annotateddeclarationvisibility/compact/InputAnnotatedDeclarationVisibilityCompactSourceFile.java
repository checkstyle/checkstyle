/*
AnnotatedDeclarationVisibility
annotations = (default)com.google.common.annotations.VisibleForTesting
visibility = (default)protected,package
tokens = (default)PACKAGE_DEF, IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, RECORD_DEF, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, ANNOTATION_DEF


*/

// non-compiled with javac: Compilable with Java25

import com.google.common.annotations.VisibleForTesting;

@VisibleForTesting
protected void allowedMethod() {}

// violation below 'Annotated element has disallowed visibility 'public'.'
@VisibleForTesting
public void violationPublicMethod() {}

@VisibleForTesting
protected record AllowedRecord(int x) {}

// violation below 'Annotated element has disallowed visibility 'private'.'
@VisibleForTesting
private record ViolationRecord(int x) {}

void main() {}
