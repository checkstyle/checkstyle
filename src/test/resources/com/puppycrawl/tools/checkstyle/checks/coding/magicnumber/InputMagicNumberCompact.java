/*
MagicNumber
ignoreNumbers = (default)-1, 0, 1, 2
ignoreHashCodeMethod = (default)false
ignoreAnnotation = (default)false
ignoreFieldDeclaration = true
ignoreAnnotationElementDefaults = (default)true
constantWaiverParentToken = (default)TYPECAST, METHOD_CALL, EXPR, ARRAY_INIT, UNARY_MINUS, \
                            UNARY_PLUS, ELIST, STAR, ASSIGN, PLUS, MINUS, DIV, LITERAL_NEW, \
                            SR, BSR, SL, BXOR, BOR, BAND, BNOT, QUESTION, COLON, EQUAL, NOT_EQUAL, \
                            GE, GT, LE, LT, MOD
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG

*/

int magic = 42;

void main() {
    System.out.println(magic);
}
