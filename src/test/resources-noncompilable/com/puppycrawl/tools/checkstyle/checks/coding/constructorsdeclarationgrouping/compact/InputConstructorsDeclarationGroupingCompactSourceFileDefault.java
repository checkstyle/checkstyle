/*
ConstructorsDeclarationGrouping
orderByIncreasingParameterCount = (default)false


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

class Split {
    Split() {
    }

    void work() {
    }

    Split(int value) { // violation 'Constructors should be grouped together.'
    }
}

class Grouped {
    Grouped() {
    }

    Grouped(int value) {
    }

    void work() {
    }
}
