/*
EmptyStatement
allowEmptyLoopBody = true


*/

// non-compiled with javac: Compilable with Java25

void main() {
    boolean cond = true;
    for (; cond;);
    while (cond);
}
