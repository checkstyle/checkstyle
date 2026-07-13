/*
EmptyStatement


*/

// non-compiled with javac: Compilable with Java25

; // no violation, empty declaration outside of method

void main() {
    ; // violation 'Empty statement'

    boolean cond = true;
    for (; cond;); // violation 'Empty statement'

    if (cond); // violation 'Empty statement'

    while (cond) {
        cond = false;
        ; // violation 'Empty statement'
    }
}
