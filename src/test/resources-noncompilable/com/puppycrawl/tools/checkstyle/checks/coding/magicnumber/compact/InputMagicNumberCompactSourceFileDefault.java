/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW, \
                            SR, BSR, SL, BXOR, BOR, BAND, BNOT, QUESTION, COLON, EQUAL, NOT_EQUAL, \
                            GE, GT, LE, LT, MOD
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG

*/

// non-compiled with javac: Compilable with Java25

int field = 123; // violation ''123' is a magic number.'

void main() {
    final int constant = 24 * 60;
    int local = 7; // violation ''7' is a magic number.'
    System.out.println(456);
    // violation above ''456' is a magic number.'
    int ignored = 2;
}

@Override
public int hashCode() {
    int hash = 31; // violation ''31' is a magic number.'
    return hash + 17; // violation ''17' is a magic number.'
}

@MyAnnotation(42) // violation ''42' is a magic number.'
void annotatedMethod() {
}

@interface MyAnnotation {
    int value() default 10;
}
