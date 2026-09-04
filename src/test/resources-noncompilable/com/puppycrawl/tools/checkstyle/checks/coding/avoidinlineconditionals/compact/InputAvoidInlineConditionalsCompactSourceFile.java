/*
AvoidInlineConditionals


*/

// non-compiled with javac: Compilable with Java25

void main() {
    boolean condition = true;
    int result = condition ? 1 : 0; // violation 'Avoid inline conditionals'
}
