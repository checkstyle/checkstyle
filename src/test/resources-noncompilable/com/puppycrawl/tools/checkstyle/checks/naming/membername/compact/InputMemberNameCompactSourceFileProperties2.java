/*
MemberName
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = false
applyToPrivate = false


*/

// non-compiled with javac: Compilable with Java25

int PackageIgnored;
private int PrivateIgnored;
public int Public_; // violation 'Name 'Public_' must match pattern'
protected int Protected_; // violation 'Name 'Protected_' must match pattern'
public int good;

void main() {
}

class Inner {
    int InnerPackageIgnored;
    public int InnerPublic; // violation 'Name 'InnerPublic' must match pattern'
}
