/*
DuplicateConditionIfChain

*/

// non-compiled with javac: Compilable with Java25

void main() {
    int x = 5;
    if (x > 0) {
        System.out.println("positive");
    } else if (x > 0) { // violation 'Duplicate condition expression identical to the condition at line '10'.'
        System.out.println("positive again");
    }
}
