/*
GoogleMethodName

*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = "Hello".length();
}

// violation below 'Method name 'InvalidMethodName' must be .* start lowercase'
int InvalidMethodName() {
    return 1;
}

int validMethodName() {
    return 0;
}
