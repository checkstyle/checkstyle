/*
ModifiedControlVariable
skipEnhancedForLoopVariable = true


*/
// non-compiled with javac: Compilable with Java25

void main() {
    String[] items = {"x", "y"};
    for (String item : items) {
        item = "z"; // ok, enhanced for-loop variable is skipped
    }

    for (int i = 0; i < 5; i++) {
        i++; // violation 'Control variable 'i' is modified'
    }
}
