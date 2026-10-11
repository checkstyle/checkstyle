/*
InnerTypeLast


*/

// non-compiled with javac: Compilable with Java25

void main() { }

class Outer {
    class Nested { }

    // violation below 'Init blocks, constructors, fields and methods should be before inner types'
    void process() { }
}
