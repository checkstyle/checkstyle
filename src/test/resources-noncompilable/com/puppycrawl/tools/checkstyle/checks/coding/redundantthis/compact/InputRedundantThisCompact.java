/*
RedundantThis
checkMethods=(default)false
allowAdjacentToRequiredThis=(default)true

*/

// non-compiled with javac: Compilable with Java25

int a;
int b = this.a;
// violation above, 'Redundant "this", field 'a' can be accessed directly.'
int c = a;

void main() {
    this.a = 1;
    // violation above, 'Redundant "this", field 'a' can be accessed directly.'
    int d = a;
}
