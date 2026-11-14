/*
DuplicateConditionIfChain

*/

// non-compiled with javac: Compilable with Java25

void main() {
    int x = 5;
    if (x > 0) {
        System.out.println("positive");
    // violation below 'Duplicate condition expression'
    } else if (x > 0) {
        System.out.println("positive again");
    }
}
