/*
NoWhitespaceBefore
allowLineBreaks = true
tokens = (default)COMMA, SEMI, POST_INC, POST_DEC, ELLIPSIS, LABELED_STAT


*/

// non-compiled with javac: Compilable with Java25

void checkWhitespace(int value) {
    int incremented = value;
    incremented ++; // violation ''\+\+' is preceded with whitespace.'
    int copy = incremented ; // violation '';' is preceded with whitespace.'
    incremented
        ++;
    System.out.println(copy);
}

void main() {
}
