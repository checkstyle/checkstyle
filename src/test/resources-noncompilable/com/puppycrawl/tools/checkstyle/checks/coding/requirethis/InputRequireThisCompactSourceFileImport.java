/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

// non-compiled with javac: package name 'field' collides with a field, used for testing

import field.foo.Bar; // ok, the import segment 'field' is not a reference to the field

int field = 1;

void increment() {
    field = field + 1;
    // 2 violations above:
    //  'Reference to instance variable 'field' needs "this.".'
    //  'Reference to instance variable 'field' needs "this.".'
}

void main() {
}
