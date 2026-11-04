/*
StaticVariableName
format = ^s[A-Z][a-zA-Z0-9]*$
applyToPublic = false
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = false


*/

// non-compiled with javac: Compilable with Java25

static int sGood;
static int bad; // violation 'Name 'bad' must match pattern'
public static int publicIgnored;
protected static int protectedBad; // violation 'Name 'protectedBad' must match pattern'
private static int privateIgnored;

void main() {
}

class Inner {
    public static int publicInnerIgnored;
    static int innerBad; // violation 'Name 'innerBad' must match pattern'
}
