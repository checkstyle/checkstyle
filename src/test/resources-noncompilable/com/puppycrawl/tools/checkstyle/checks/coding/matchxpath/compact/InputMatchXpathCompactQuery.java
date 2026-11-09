/*
MatchXpath
query = //METHOD_DEF[./IDENT[@text='foo']] | //VARIABLE_DEF[./IDENT[@text='unused']]


*/

// non-compiled with javac: Compilable with Java25

int field = 0;

int unused = 1; // violation 'Illegal code structure detected.'

void foo() { // violation 'Illegal code structure detected.'
}

void main() {
    int unused = 2; // violation 'Illegal code structure detected.'
    foo();
}

class Nested {
    void foo() { // violation 'Illegal code structure detected.'
    }

    void bar() {
    }
}

record Point(int x) {
    static int unused = 3; // violation 'Illegal code structure detected.'
}
