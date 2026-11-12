/*
OperatorWrap
option = (default)nl
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF
shouldWrapAtHigherLevel = true

*/

// non-compiled with javac: Compilable with Java25

void checkWrap(int first, int second, int third) {
    int higher = first
            + second * third;
    int lower = first + second
            * third; // violation above ''\+' should be on a new line.'
    System.out.println(higher + lower);
}

void main() {
}
