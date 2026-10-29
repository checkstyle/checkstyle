/*
TodoComment
format = FIXME\:


*/

// non-compiled with javac: Compilable with Java25

void checkComments() { // violation below 'matches to-do format'
    // FIXME: do this later
    // TODO: not matched by the custom format
    // a regular comment
    // violation below 'matches to-do format'
    /* FIXME: in a block comment */
}

void main() {
}
