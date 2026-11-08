/*
ConstructorsDeclarationGrouping
orderByIncreasingParameterCount = true


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

class Decreasing {
    Decreasing(int first, int second) {
    }

    Decreasing(int value) {
    } // violation above 'Constructors should be ordered by increasing parameter count.'
}

class Increasing {
    Increasing() {
    }

    Increasing(int value) {
    }
}
