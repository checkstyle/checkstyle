/*
UnusedPrivateField
ignoreAnnotationCanonicalNames = (default)java.io.Serial
ignoredFieldNames = serialVersionUID,Log

*/

// non-compiled with javac: Compilable with Java25

private static final long serialVersionUID = 1L; // ok, as suppressed by property

private static final long LOG = 1L; // ok, as suppressed by property

void main() { }
