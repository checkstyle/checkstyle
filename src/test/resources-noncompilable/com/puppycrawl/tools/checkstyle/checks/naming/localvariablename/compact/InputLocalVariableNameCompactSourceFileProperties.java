/*
LocalVariableName
allowOneCharVarInForLoop = true
format = ^[a-z][a-zA-Z]+$


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int x = 0; // violation 'Name 'x' must match pattern'
    int ok = 0;
    for (int i = 0; i < 1; i++) {
    }
    for (int Bad = 0; Bad < 1; Bad++) { // violation 'Name 'Bad' must match pattern'
    }
}
