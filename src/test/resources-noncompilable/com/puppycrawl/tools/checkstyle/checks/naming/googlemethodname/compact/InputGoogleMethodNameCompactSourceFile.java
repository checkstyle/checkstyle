/*
GoogleMethodName

*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = "Hello".length();
}

// violation 2 lines below """Method name 'InvalidMethodName' must be more than a character, start
// lowercase, and not have a single lowercase followed by uppercase, or consecutive uppercase."""
int InvalidMethodName() {
    return 1;
}

int validMethodName() {
    return 0;
}
