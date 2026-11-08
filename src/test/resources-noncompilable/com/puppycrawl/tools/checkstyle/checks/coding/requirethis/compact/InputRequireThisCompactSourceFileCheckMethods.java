/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

// non-compiled with javac: Compilable with Java25

int field = 1;

void increment() {
    field = field + 1;
    // 2 violations above:
    //  'Reference to instance variable 'field' needs "this.".'
    //  'Reference to instance variable 'field' needs "this.".'
}

void main() {
    increment();
}
