/*
EmptyBlock
option = text
tokens = (default)LITERAL_WHILE, LITERAL_TRY, LITERAL_FINALLY, LITERAL_DO, \
         LITERAL_IF, LITERAL_ELSE, LITERAL_FOR, INSTANCE_INIT, STATIC_INIT, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED


*/

// non-compiled with javac: Compilable with Java25

void checkBlocks(boolean flag) {
    if (flag) {
    } // violation above 'Empty if block.'
    if (flag) { /* comment only */ }
    if (flag) {
        // comment only
    }
    while (flag) {
    } // violation above 'Empty while block.'
    try {
    } // violation above 'Empty try block.'
    catch (Exception exception) {
        System.out.println(exception);
    }
}

void main() {
}
