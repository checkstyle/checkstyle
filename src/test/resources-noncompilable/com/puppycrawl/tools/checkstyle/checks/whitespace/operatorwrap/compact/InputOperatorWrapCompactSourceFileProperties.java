/*
OperatorWrap
option = eol
tokens = (default)QUESTION, COLON, EQUAL, NOT_EQUAL, DIV, PLUS, MINUS, STAR, MOD, \
         SR, BSR, GE, GT, SL, LE, LT, BXOR, BOR, LOR, BAND, LAND, TYPE_EXTENSION_AND, \
         LITERAL_INSTANCEOF


*/

// non-compiled with javac: Compilable with Java25

void checkWrap(int first, int second, boolean flag) {
    int sum = first
            + second; // violation ''\+' should be on the previous line.'
    boolean both = flag
            && flag; // violation ''&&' should be on the previous line.'
    int choice = flag
            ? first : second; // violation ''?' should be on the previous line.'
    int total = first +
            second;
    boolean either = flag ||
            flag;
    int single = first + second;
    if (both || either) {
        System.out.println(sum + choice + total + single);
    }
}

void main() {
}
