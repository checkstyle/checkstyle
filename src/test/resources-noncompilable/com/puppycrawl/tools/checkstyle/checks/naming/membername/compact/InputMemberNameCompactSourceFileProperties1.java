/*
MemberName
format = ^m[A-Z][a-zA-Z0-9]*$
applyToPublic = false
applyToProtected = false
applyToPackage = (default)true
applyToPrivate = (default)true


*/

// non-compiled with javac: Compilable with Java25

int mGood;
int bad; // violation 'Name 'bad' must match pattern'
public int publicIgnored;
protected int protectedIgnored;
private int privateBad; // violation 'Name 'privateBad' must match pattern'
private int mPrivate;

void main() {
}

class Inner {
    public int publicInnerIgnored;
    int innerBad; // violation 'Name 'innerBad' must match pattern'
}
