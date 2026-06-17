/*
OuterTypeNumber
max = (default)1


*/

// non-compiled with javac: Compilable with Java25

// Every type declared below is a member of the implicit class synthesized for
// this compact source file, not an outer type. The only outer type is the
// implicit class itself, so the check must stay silent even though several
// types of every kind, plus nested types, are declared.

void main() {
    System.out.println("Hello!");
}

class A {
    class Inner {
    }
}

interface B {
}

enum C {
    ONE,
    TWO
}

record D(int x) {
}

@interface E {
}
