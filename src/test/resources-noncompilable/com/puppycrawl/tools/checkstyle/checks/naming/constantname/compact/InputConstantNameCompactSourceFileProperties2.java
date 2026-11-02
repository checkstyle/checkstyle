/*
ConstantName
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = false
applyToPrivate = false


*/

// non-compiled with javac: Compilable with Java25

static final int packageIgnored = 1;
private static final int privateIgnored = 1;
public static final int PUBLIC_GOOD = 1;
public static final int public_bad = 1; // violation 'Name 'public_bad' must match pattern'
protected static final int PROTECTED_GOOD = 1;
protected static final int protected_bad = 1; // violation 'Name 'protected_bad' must match pattern'

void main() {
}

class Inner {
    static final int innerPackageIgnored = 1;
    public static final int INNER_PUBLIC_GOOD = 1;
    public static final int innerBad = 1; // violation 'Name 'innerBad' must match pattern'
}
