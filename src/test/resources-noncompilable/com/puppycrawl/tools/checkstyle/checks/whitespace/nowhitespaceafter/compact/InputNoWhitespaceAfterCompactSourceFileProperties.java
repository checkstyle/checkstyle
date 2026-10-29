/*
NoWhitespaceAfter
allowLineBreaks = false
tokens = (default)ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, \
         DOT, ARRAY_DECLARATOR, INDEX_OP


*/

// non-compiled with javac: Compilable with Java25

void checkWhitespace(String text, boolean flag, int value, int[] numbers) {
    String trimmed = text. trim(); // violation ''.' is followed by whitespace.'
    boolean negated = ! flag; // violation ''!' is followed by whitespace.'
    int negative = - value; // violation ''-' is followed by whitespace.'
    int shifted = ~ value; // violation ''~' is followed by whitespace.'
    int decremented = -- value; // violation ''--' is followed by whitespace.'
    int indexed = numbers [0]; // violation ''numbers' is followed by whitespace.'
    String chained = text.
        // violation above ''.' is followed by whitespace.'
        trim();
    int negativeAtBreak = -
        // violation above ''-' is followed by whitespace.'
        value;
    boolean ok = !flag;
    System.out.println(trimmed + chained);
    System.out.println(negated);
    System.out.println(negative + shifted + decremented + indexed);
    System.out.println(negativeAtBreak);
    System.out.println(ok);
}

void main() {
}
