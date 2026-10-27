/*
MagicNumber
ignoreNumbers = -1, 0, 1, 2, 3.5
ignoreHashCodeMethod = true
ignoreAnnotation = true
ignoreFieldDeclaration = (default)false
ignoreAnnotationElementDefaults = false
constantWaiverParentToken = ASSIGN, ARRAY_INIT, EXPR, UNARY_PLUS, UNARY_MINUS, TYPECAST, \
                            ELIST, METHOD_CALL, LITERAL_NEW, DIV, PLUS, MINUS
tokens = (default)NUM_DOUBLE, NUM_FLOAT, NUM_INT, NUM_LONG

*/

// non-compiled with javac: Compilable with Java25

int field = 8; // violation ''8' is a magic number.'

void main() {
    final int waived = 3 + 4;
    final int notWaived = 3 * 4;
    // 2 violations above:
    // ''3' is a magic number.'
    // ''4' is a magic number.'
    double ratio = 3.5;
    double other = 9.5; // violation ''9.5' is a magic number.'
}

@Override
public int hashCode() {
    int hash = 31;
    return hash + 17;
}

@MyAnnotation(42)
void annotatedMethod() {
}

@interface MyAnnotation {
    int value() default 10; // violation ''10' is a magic number.'
}
