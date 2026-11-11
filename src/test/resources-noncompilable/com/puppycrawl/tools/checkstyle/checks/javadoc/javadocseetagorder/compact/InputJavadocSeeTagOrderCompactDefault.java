/*
JavadocSeeTagOrder
violateExecutionOnNonTightHtml = (default)false


*/

// non-compiled with javac: Compilable with Java25

/**
 * Valid order.
 *
 * @see #field
 * @see String
 */
void validMethod() {
}

// violation 5 lines below '@see tag '#field' should be placed before 'String'.'
/**
 * Invalid order.
 *
 * @see String
 * @see #field
 */
void invalidMethod() {
}

int field;

void main() {
}
