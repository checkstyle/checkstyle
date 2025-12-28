/*
UnusedLocalVariable
allowUnnamedVariables = (default)true

*/

// non-compiled with javac: Compilable with Java25

String greeting = "Hello, compact source file!";

void main() {
    int assignedOnly = 1; // violation, 'Unused named local variable'
    assignedOnly = 2;

    int neverAssigned; // violation, 'Unused named local variable'

    int used = 10; // violation, 'Unused named local variable'
    used++;

    int[] arr = {1, 2, 3}; // violation, 'Unused named local variable'

    int[] usedArr = {0};
    usedArr[0] = 5;

    String local = greeting.toLowerCase(); // violation, 'Unused named local variable'

    String _ = greeting.toUpperCase();

    for (int i = 0; i < 2; i++) {
        int innerUsed = i * 2;
        IO.println(innerUsed);
    }

    for (int p = 0; p < 2; p++) {
        IO.println(p);
    }

    try {
        throw new IllegalStateException("x");
    } catch (RuntimeException e) {
        IO.println(e.getMessage());
    }

    Runnable r = () -> {
        int lambdaUnused = 7; // violation, 'Unused named local variable'
        int lambdaUsed = 8;
        IO.println(lambdaUsed);
    };
    r.run();
}
