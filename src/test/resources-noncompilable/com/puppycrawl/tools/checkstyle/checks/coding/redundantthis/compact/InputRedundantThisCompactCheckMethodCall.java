/*
RedundantThis
checkMethods=true
allowAdjacentToRequiredThis=false

*/

// non-compiled with javac: Compilable with Java25

void method() {
}

void main() {
    this.method();
    // violation above 'Redundant "this", method 'method' can be accessed directly.'
}
