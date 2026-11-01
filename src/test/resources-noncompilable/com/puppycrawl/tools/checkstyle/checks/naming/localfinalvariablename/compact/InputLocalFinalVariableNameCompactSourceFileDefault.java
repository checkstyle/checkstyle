/*
LocalFinalVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

// non-compiled with javac: Compilable with Java25

void main() {
    final int good = 0;
    final int Bad = 0; // violation 'Name 'Bad' must match pattern'
    for (final int i = 0; i < 1; i++) {
    }
    for (final int I = 0; I < 1; I++) { // violation 'Name 'I' must match pattern'
    }
}
