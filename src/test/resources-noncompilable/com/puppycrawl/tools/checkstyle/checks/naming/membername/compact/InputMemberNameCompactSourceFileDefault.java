/*
MemberName
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

// non-compiled with javac: Compilable with Java25

int good;
int Bad; // violation 'Name 'Bad' must match pattern'
public int Public_; // violation 'Name 'Public_' must match pattern'
protected int Protected_; // violation 'Name 'Protected_' must match pattern'
private int Private_; // violation 'Name 'Private_' must match pattern'
final int Final_ = 1; // violation 'Name 'Final_' must match pattern'
static int StaticIgnored;

void main() {
    int LocalIgnored = 0;
    Runnable r = new Runnable() {
        int AnonField; // violation 'Name 'AnonField' must match pattern'

        public void run() {
        }
    };
}

class Inner {
    int InnerBad; // violation 'Name 'InnerBad' must match pattern'
    static int InnerStatic;
}

interface Shape {
    int CONSTANT = 1;
}

enum Color {
    RED;
    int EnumField_; // violation 'Name 'EnumField_' must match pattern'
}

record Point(int X) {
    static int RecordStatic;
}
