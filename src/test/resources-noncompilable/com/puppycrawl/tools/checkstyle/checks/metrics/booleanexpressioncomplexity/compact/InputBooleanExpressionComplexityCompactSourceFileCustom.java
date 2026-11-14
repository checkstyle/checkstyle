/*
BooleanExpressionComplexity
max = 1
tokens = (default)CTOR_DEF,METHOD_DEF,EXPR,LAND,BAND,LOR,BOR,BXOR,COMPACT_CTOR_DEF
treatUniformExpressionsAsOne = false

*/

// non-compiled with javac: Compilable with Java25

void main() {
}

void logicalOperators(boolean a, boolean b, boolean c) {
    // violation below 'Boolean expression complexity is 2 (max allowed is 1).'
    if (a && b || c) {
    }
}

void bitwiseOperators(boolean a, boolean b, boolean c) {
    // violation below 'Boolean expression complexity is 2 (max allowed is 1).'
    if (a & b | c) {
    }
}
