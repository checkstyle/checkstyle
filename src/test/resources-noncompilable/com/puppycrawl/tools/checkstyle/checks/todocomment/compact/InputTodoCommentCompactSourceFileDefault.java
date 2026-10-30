/*
TodoComment
format = (default)TODO\:


*/

// non-compiled with javac: Compilable with Java25

void checkComments() { // violation below 'matches to-do format'
    // TODO: do this later
    // FIXME: not matched by the default format
    // a regular comment
    // violation below 'matches to-do format'
    /* TODO: in a block comment */
}

void main() {
}
