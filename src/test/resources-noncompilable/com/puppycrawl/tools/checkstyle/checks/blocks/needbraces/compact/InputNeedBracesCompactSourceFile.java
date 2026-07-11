/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int i = 0;
    if (i == 0) i++; // violation ''if' construct must use '{}'s'
    while (i < 3) i++; // violation ''while' construct must use '{}'s'
    for (int j = 0; j < 3; j++) i++; // violation ''for' construct must use '{}'s'
    do i++; while (i < 8); // violation ''do' construct must use '{}'s'
    if (i > 0) {
        i--;
    }
}
