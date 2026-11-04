/*
StaticVariableName
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

// non-compiled with javac: Compilable with Java25

static int good;
static int Bad; // violation 'Name 'Bad' must match pattern'
public static int Public_; // violation 'Name 'Public_' must match pattern'
protected static int Protected_; // violation 'Name 'Protected_' must match pattern'
private static int Private_; // violation 'Name 'Private_' must match pattern'
static final int FINAL_IGNORED = 1;
int InstanceIgnored;

void main() {
    int LocalIgnored = 0;
}

class Inner {
    static int InnerBad; // violation 'Name 'InnerBad' must match pattern'
    int InnerInstance;
}

interface Shape {
    int CONSTANT = 1;
}

enum Color {
    RED;
    static int EnumStatic_; // violation 'Name 'EnumStatic_' must match pattern'
}

record Point(int x) {
    static int RecordStatic_; // violation 'Name 'RecordStatic_' must match pattern'
}
