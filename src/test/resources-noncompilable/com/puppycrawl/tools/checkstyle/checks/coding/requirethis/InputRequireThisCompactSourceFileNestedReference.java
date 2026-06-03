/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// non-compiled with javac: Compilable with Java25

int outerField = 1;

class Inner {
    int innerField = 2;

    void useOuter() {
        outerField = 2;
    }

    void useInner() {
        innerField = 3; // violation 'Reference to instance variable 'innerField' needs "this.".'
    }
}

void main() {
}
