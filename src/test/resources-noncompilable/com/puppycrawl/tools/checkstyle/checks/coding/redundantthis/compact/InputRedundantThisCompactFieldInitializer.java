/*
RedundantThis
checkMethods=true
allowAdjacentToRequiredThis=(default)true

*/

// non-compiled with javac: Compilable with Java25

int a = 0;
int x = this.a;
// violation above 'Redundant "this", field 'a' can be accessed directly.'
int y = this.hashCode();
// violation above 'Redundant "this", method 'hashCode' can be accessed directly.'

void main() {
}
