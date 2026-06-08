/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false

*/

// non-compiled with javac: Compilable with Java25

int topLevelField = 99;

void m() {
    System.out.println(topLevelField);
}

void main() {
    m();
}

class Test {
    int x;
    void foo() {
        x = 5; // violation 'Reference to instance variable 'x' needs "this.".'
    }
}
