/*
OpenjdkMethodParameterAlignment
tokens = (default)METHOD_DEF, CTOR_DEF


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int a = 1;
}

void listedVertically(int a,
                      int b,
                      int c) {
}

void twoOnOneLine(int a,
                  int b, int c) {
    // violation above 'Only one parameter is allowed per line in a vertical list.'
}

void twoOnFirstLine(int a, int b,
                    int c) {
    // violation 2 lines above 'Only one parameter is allowed per line in a vertical list.'
}

void wrappedByEightSpaces(int a, int b,
        int c) {
}
