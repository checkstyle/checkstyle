/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// non-compiled with javac: Compilable with Java25

import java.util.List;

// field named to collide with the first segment of the import above
int java = 1;

void increment() {
    java = java + 1;
    // 2 violations above:
    //  'Reference to instance variable 'java' needs "this.".'
    //  'Reference to instance variable 'java' needs "this.".'
}

void main() {
}
