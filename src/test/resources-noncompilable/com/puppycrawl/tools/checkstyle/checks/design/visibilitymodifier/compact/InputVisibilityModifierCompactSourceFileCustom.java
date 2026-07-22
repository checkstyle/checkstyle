/*
VisibilityModifier
packageAllowed = true
protectedAllowed = true
publicMemberPattern = ^f[A-Z][a-zA-Z0-9]*$
allowPublicFinalFields = true
allowPublicImmutableFields = true
immutableClassCanonicalNames = java.lang.String
ignoreAnnotationCanonicalNames = java.lang.Deprecated


*/

// non-compiled with javac: Compilable with Java25

public int publicField = 1;
protected int protectedField = 2;
int packageField = 3;
private int privateField = 4;

void main() {
    int localVariable = privateField;
}
