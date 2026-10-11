/*
FinalClass


*/

// non-compiled with javac: Compilable with Java25

Inner obj = new Inner() { };

class Outer {
    static class Inner { // violation 'Class Inner should be declared as final'
        private Inner() { }
    }
}

class Inner {
    private Inner() { }
}

void main() { }
