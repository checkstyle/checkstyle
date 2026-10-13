/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = true
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
// non-compiled with javac: Compilable with Java25

void main() {
    int _ = sideEffect(); // violation 'Variable '_' should be declared final'
    final int named = sideEffect();
}

int sideEffect() {
    return 0;
}
