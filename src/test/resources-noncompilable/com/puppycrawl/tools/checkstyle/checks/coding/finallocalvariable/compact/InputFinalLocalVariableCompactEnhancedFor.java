/*
FinalLocalVariable
validateEnhancedForLoopVariable = true
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
// non-compiled with javac: Compilable with Java25

void main() {
    final int[] values = {1, 2, 3};

    for (int value : values) { // violation 'Variable 'value' should be declared final'
        System.out.println(value);
    }
    for (int reassigned : values) {
        reassigned++;
        System.out.println(reassigned);
    }
}
