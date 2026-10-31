/*
EmptyForIteratorPad
option = space


*/

// non-compiled with javac: Compilable with Java25

void main() {
    for (int i = 0; i < 1; ) {
        i++;
    }
    for (int i = 0; i < 1;) { // violation "';' is not followed by whitespace"
        i++;
    }
}
