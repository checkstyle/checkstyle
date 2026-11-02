/*
ConstantName
format = ^k[A-Z][a-zA-Z0-9]*$
applyToPublic = false
applyToProtected = false
applyToPackage = (default)true
applyToPrivate = (default)true


*/

// non-compiled with javac: Compilable with Java25

static final int kGood = 1;
static final int bad = 1; // violation 'Name 'bad' must match pattern'
public static final int publicIgnored = 1;
protected static final int protectedIgnored = 1;
private static final int privateBad = 1; // violation 'Name 'privateBad' must match pattern'
private static final int kPrivateGood = 1;

void main() {
}

class Inner {
    public static final int innerPublicIgnored = 1;
    static final int innerBad = 1; // violation 'Name 'innerBad' must match pattern'
}
