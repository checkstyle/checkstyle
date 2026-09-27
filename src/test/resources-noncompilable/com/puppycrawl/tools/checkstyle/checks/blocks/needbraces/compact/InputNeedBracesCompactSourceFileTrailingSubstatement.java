/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE
allowSameLineTrailingSubstatement = true

*/

// non-compiled with javac: Compilable with Java25

void main() {
    int x = 0;
    if      (x == 0) x = 1;
    else if (x == 1) x = 2;
    else if (x == 2) x = 3;
    else             x = 4;
    for (int i = 0; i < 10; i++) if (i == 5) break;
    // violation below ''if' construct must use '{}'s'
    if (x == 1)
        x = 2;
}
