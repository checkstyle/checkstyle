/*
ConstantName
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

// non-compiled with javac: Compilable with Java25

static final int GOOD = 1;
static final int bad = 1; // violation 'Name 'bad' must match pattern'
public static final int PUBLIC_GOOD = 1;
public static final int public_bad = 1; // violation 'Name 'public_bad' must match pattern'
protected static final int PROTECTED_GOOD = 1;
protected static final int protected_bad = 1; // violation 'Name 'protected_bad' must match pattern'
private static final int PRIVATE_GOOD = 1;
private static final int private_bad = 1; // violation 'Name 'private_bad' must match pattern'

static int notFinal = 1;
final int notStatic = 1;

void main() {
    final int local = 1;
}

class Inner {
    static final int INNER_GOOD = 1;
    static final int inner_bad = 1; // violation 'Name 'inner_bad' must match pattern'
}

interface Shape {
    int INTERFACE_GOOD = 1;
    int interface_bad = 1; // violation 'Name 'interface_bad' must match pattern'
}
