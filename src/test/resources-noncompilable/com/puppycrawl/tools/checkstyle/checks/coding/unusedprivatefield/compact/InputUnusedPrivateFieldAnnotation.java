/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = Inject

*/

// non-compiled with javac: Compilable with Java25

@interface Inject {}

@Inject
private Object service;

void main() { }
