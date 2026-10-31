/*
OperatorWrap
option = (default)nl
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/

// non-compiled with javac: Compilable with Java25

void checkWrap(int first, int second, boolean flag) {
    int sum = first + // violation ''\+' should be on a new line.'
            second;
    boolean both = flag && // violation ''&&' should be on a new line.'
            flag;
    int choice = flag ? // violation ''?' should be on a new line.'
            first : second;
    int total = first
            + second;
    boolean either = flag
            || flag;
    int single = first + second;
    if (both || either) {
        System.out.println(sum + choice + total + single);
    }
}

void main() {
}
