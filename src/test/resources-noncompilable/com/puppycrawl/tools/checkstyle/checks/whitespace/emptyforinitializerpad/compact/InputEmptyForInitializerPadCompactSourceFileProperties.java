/*
EmptyForInitializerPad
option = space


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int i = 0;
    for ( ; i < 1; i++) {
        i++;
    }
    for (; i < 1; i++) { // violation "';' is not preceded with whitespace"
        i++;
    }
}
