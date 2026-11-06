/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = (default)COMMA, SEMI, POST_INC, POST_DEC, ELLIPSIS, LABELED_STAT


*/

// non-compiled with javac: Compilable with Java25

void checkWhitespace(int value) {
    int incremented = value;
    incremented ++; // violation ''\+\+' is preceded with whitespace.'
    int decremented = value;
    decremented --; // violation ''\-\-' is preceded with whitespace.'
    int copy = incremented ; // violation '';' is preceded with whitespace.'
    incremented
        ++; // violation ''\+\+' is preceded with whitespace.'
    System.out.println(copy + decremented);
}

void main() {
}
