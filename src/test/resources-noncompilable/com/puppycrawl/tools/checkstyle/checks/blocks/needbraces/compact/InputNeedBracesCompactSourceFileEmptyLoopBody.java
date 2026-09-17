/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = true
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

// non-compiled with javac: Compilable with Java25

void main() {
    int i = 0;
    while (i++ < 3);
    for (int j = 0; j < 3; j++);
    if (i > 0) i--; // violation ''if' construct must use '{}'s'
}
