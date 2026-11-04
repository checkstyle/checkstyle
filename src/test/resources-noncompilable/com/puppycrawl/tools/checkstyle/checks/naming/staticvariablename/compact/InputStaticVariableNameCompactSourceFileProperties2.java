/*
StaticVariableName
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = false
applyToPackage = false
applyToPrivate = (default)true


*/

// non-compiled with javac: Compilable with Java25

static int PackageIgnored;
protected static int ProtectedIgnored;
public static int Public_; // violation 'Name 'Public_' must match pattern'
private static int Private_; // violation 'Name 'Private_' must match pattern'
public static int good;

void main() {
}

class Inner {
    static int InnerPackageIgnored;
    public static int InnerPublic; // violation 'Name 'InnerPublic' must match pattern'
}
