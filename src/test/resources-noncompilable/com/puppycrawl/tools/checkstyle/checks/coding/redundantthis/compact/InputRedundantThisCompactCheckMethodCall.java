/*
RedundantThis
checkMethods=true
allowAdjacentToRequiredThis=false

*/

// non-compiled with javac: Compilable with Java25

int x = this.hashCode();
// violation above 'Redundant "this", method 'hashCode' can be accessed directly.'
int y = hashCode();

void method() {
}

void main() {
    this.method();
    // violation above 'Redundant "this", method 'method' can be accessed directly.'
    method();
}
