/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = Inject
ignoredFieldNames = (default)serialVersionUID

*/

// non-compiled with javac: Compilable with Java25

@interface Inject {}

@Inject
private Object service; // ok, as suppressed by property

void main() { }
