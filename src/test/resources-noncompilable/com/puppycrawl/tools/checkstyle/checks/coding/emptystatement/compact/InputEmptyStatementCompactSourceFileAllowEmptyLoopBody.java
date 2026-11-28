/*
EmptyStatement
allowEmptyLoopBody = true

*/

// non-compiled with javac: Compilable with Java25

; // violation 'Empty statement'

void main() {
    boolean cond = true;
    for (; cond;); // ok, empty loop body is allowed
    while (cond); // ok, empty loop body is allowed
    if (cond); // violation 'Empty statement'
}
