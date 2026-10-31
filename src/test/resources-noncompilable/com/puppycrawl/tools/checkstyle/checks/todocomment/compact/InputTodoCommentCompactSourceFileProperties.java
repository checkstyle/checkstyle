/*
TodoComment
format = FIXME\:


*/

// non-compiled with javac: Compilable with Java25

// violation below 'matches to-do format'
// FIXME: over the field
int fieldWithFixmeComment = 0;

// a regular comment over the field
int fieldWithRegularComment = 0;

void checkComments() { // violation below 'matches to-do format'
    // FIXME: do this later
    // TODO: not matched by the custom format
    // a regular comment
    // violation below 'matches to-do format'
    /* FIXME: in a block comment */
}

void main() {
}
