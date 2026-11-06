/*
TypeName
format = (default)^[A-Z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = false
applyToPrivate = false
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

class ignoredPackage {
}

private static class ignoredPrivate {
}

public static class badPublic { // violation 'Name 'badPublic' must match pattern'
}

protected static class Bad_Protected { // violation 'Name 'Bad_Protected' must match pattern'
}
