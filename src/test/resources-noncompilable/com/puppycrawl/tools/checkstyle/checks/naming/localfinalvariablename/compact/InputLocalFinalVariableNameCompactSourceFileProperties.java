/*
LocalFinalVariableName
format = ^[a-z]+$
tokens = (default)VARIABLE_DEF, PARAMETER_DEF, RESOURCE


*/

// non-compiled with javac: Compilable with Java25

void main() {
    final int good = 0;
    final int Bad = 0; // violation 'Name 'Bad' must match pattern'
    final int foo1 = 0; // violation 'Name 'foo1' must match pattern'
}
