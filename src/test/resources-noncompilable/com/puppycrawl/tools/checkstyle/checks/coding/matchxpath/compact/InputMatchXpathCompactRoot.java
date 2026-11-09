/*
MatchXpath
query = /COMPACT_COMPILATION_UNIT/METHOD_DEF[./IDENT[@text='foo']]


*/

// non-compiled with javac: Compilable with Java25

// The query only matches methods declared at the top level of a compact source file.
void foo() { // violation 'Illegal code structure detected.'
}

void main() {
    foo();
}

class Nested {
    void foo() {
    }
}
