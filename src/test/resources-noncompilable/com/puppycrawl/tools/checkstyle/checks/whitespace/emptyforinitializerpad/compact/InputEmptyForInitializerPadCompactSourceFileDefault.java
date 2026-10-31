/*
EmptyForInitializerPad
option = (default)nospace


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int i = 0;
    for (; i < 1; i++) {
    }
    for ( ; i < 1; i++) { // violation "';' is preceded with whitespace"
        i++;
    }
}
