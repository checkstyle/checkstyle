/*
ClassMemberImpliedModifier
violateImpliedStaticOnNestedEnum = (default)true
violateImpliedStaticOnNestedInterface = (default)true
violateImpliedStaticOnNestedRecord = (default)true

*/

// non-compiled with javac: Compilable with Java25

// violation below 'Implied modifier 'static' should be explicit.'
enum Color { RED, GREEN, BLUE }

interface Printable { // violation 'Implied modifier 'static' should be explicit.'
    void print();
}
record Point(int x, int y) { } // violation 'Implied modifier 'static' should be explicit.'

@Deprecated enum Status { ACTIVE } // violation 'Implied modifier 'static' should be explicit.'

static enum StaticColor { RED }
static interface StaticPrintable { }
static record StaticPoint(int x, int y) { }

class OrdinaryClass { }

void main() {
    enum LocalColor { RED }
    interface LocalPrintable { }
}
