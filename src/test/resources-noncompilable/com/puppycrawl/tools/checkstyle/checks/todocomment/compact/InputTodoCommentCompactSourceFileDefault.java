/*
TodoComment
format = (default)TODO\:


*/

// non-compiled with javac: Compilable with Java25

// violation below 'matches to-do format'
// TODO: over the field
int fieldWithTodoComment = 0;

// a regular comment over the field
int fieldWithRegularComment = 0;

void checkComments() { // violation below 'matches to-do format'
    // TODO: do this later
    // FIXME: not matched by the default format
    // a regular comment
    // violation below 'matches to-do format'
    /* TODO: in a block comment */
}

void main() {
}
