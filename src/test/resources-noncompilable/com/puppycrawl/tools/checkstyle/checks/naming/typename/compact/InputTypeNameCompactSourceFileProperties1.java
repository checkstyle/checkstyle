/*
TypeName
format = ^[A-Z][A-Z0-9]*$
applyToPublic = false
applyToProtected = false
applyToPackage = (default)true
applyToPrivate = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

class UPPER {
}

class lower { // violation 'Name 'lower' must match pattern'
}

private static class lowercase { // violation 'Name 'lowercase' must match pattern'
}

public static class ignoredPublic {
}

protected static class ignoredProtected {
}
