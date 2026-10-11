/*
ModifiedControlVariable
skipEnhancedForLoopVariable = (default)false


*/
// non-compiled with javac: Compilable with Java25

void main() {
    for (int i = 0; i < 10; i++) {
        i++; // violation 'Control variable 'i' is modified'
    }

    for (int j = 0; j < 10; j++) {
        System.out.println(j);
    }

    for (int k = 0; k < 10; k++) {
        k += 2; // violation 'Control variable 'k' is modified'
    }

    for (int a = 0; a < 10; a++) {
        for (int b = 0; b < 10; b++) {
            a++; // violation 'Control variable 'a' is modified'
        }
    }
}

void second() {
    for (int i = 0; i < 5; i++) {
        i--; // violation 'Control variable 'i' is modified'
    }

    String[] items = {"x", "y"};
    for (String item : items) {
        item = "z"; // violation 'Control variable 'item' is modified'
    }
}

class Inner {
    void m() {
        for (int i = 0; i < 3; i++) {
            i++; // violation 'Control variable 'i' is modified'
        }
    }
}
