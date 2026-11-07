/*
RequireThis
checkFields = false
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// non-compiled with javac: Compilable with Java25

int field = 1;

void increment() {
    field = field + 1;
}

void main() {
    increment(); // violation 'Method call to 'increment' needs "this.".'
}
