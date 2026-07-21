/*
FinalClass


*/

// non-compiled with javac: Compilable with Java25

Runnable r = new Runnable() {
    public void run() { }
};

Foo f = new Foo() { };

class Foo {
    private Foo() { }
}

class Bar { // violation 'Class Bar should be declared as final'
    private Bar() { }
}

void main() { }
