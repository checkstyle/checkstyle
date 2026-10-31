/*
LocalVariableName
allowOneCharVarInForLoop = (default)false
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int good = 0;
    int Bad = 0; // violation 'Name 'Bad' must match pattern'
    for (int I = 0; I < 1; I++) { // violation 'Name 'I' must match pattern'
    }
}
