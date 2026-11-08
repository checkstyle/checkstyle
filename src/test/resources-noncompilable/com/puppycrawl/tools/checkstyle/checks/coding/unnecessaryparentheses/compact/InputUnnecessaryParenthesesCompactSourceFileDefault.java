/*
UnnecessaryParentheses
tokens = (default)EXPR, IDENT, NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG, \
         STRING_LITERAL, LITERAL_NULL, LITERAL_FALSE, LITERAL_TRUE, ASSIGN, \
         BAND_ASSIGN, BOR_ASSIGN, BSR_ASSIGN, BXOR_ASSIGN, DIV_ASSIGN, \
         MINUS_ASSIGN, MOD_ASSIGN, PLUS_ASSIGN, SL_ASSIGN, SR_ASSIGN, STAR_ASSIGN, \
         LAMBDA, TEXT_BLOCK_LITERAL_BEGIN, LAND, LITERAL_INSTANCEOF, GT, LT, GE, \
         LE, EQUAL, NOT_EQUAL, UNARY_MINUS, UNARY_PLUS, INC, DEC, LNOT, BNOT, \
         POST_INC, POST_DEC, INDEX_OP, DOT, LOR, TYPECAST


*/

// non-compiled with javac: Compilable with Java25

void main() {
}

int number = (10); // violation 'parentheses around literal '10''
String text = ("text"); // violation 'parentheses around string "text"'
boolean flag = (number > 5); // violation 'parentheses around assignment right-hand side'
int plain = number + 1;

int compute(int value) {
    int result = (value * 2); // violation 'parentheses around assignment right-hand side'
    result = (result + 1); // violation 'parentheses around assignment right-hand side'
    return (result); // violation 'parentheses around identifier 'result''
}

int clean(int value) {
    return value * 2;
}

Runnable task = (() -> { }); // violation 'parentheses around assignment right-hand side'
