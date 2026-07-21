/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int i = 0;
    if (i == 0) i++;
    if (i == 1) // violation ''if' construct must use '{}'s'
        i++;
}
