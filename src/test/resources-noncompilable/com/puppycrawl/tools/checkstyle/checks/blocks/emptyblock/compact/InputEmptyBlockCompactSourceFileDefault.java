/*
EmptyBlock
option = (default)statement
tokens = (default)LITERAL_WHILE, LITERAL_TRY, LITERAL_FINALLY, LITERAL_DO, \
         LITERAL_IF, LITERAL_ELSE, LITERAL_FOR, INSTANCE_INIT, STATIC_INIT, \
         LITERAL_SWITCH, LITERAL_SYNCHRONIZED


*/

// non-compiled with javac: Compilable with Java25

void checkBlocks(boolean flag) {
    if (flag) { // violation 'Must have at least one statement.'
    }
    if (flag) { /* comment only */ } // violation 'Must have at least one statement.'
    if (flag) {
        System.out.println("not empty");
    }
    while (flag) { // violation 'Must have at least one statement.'
    }
}

void main() {
}
