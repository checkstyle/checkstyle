/*
ModifiedControlVariable
skipEnhancedForLoopVariable = (default)false
tokens = (default)OBJBLOCK, COMPACT_COMPILATION_UNIT, LITERAL_FOR, FOR_ITERATOR, FOR_EACH_CLAUSE, \
           ASSIGN, \
         PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, SR_ASSIGN, \
         BSR_ASSIGN, SL_ASSIGN, BAND_ASSIGN, BXOR_ASSIGN, BOR_ASSIGN, INC, \
         POST_INC, DEC, POST_DEC

*/

// non-compiled with javac: Compilable with Java25

void main() {
    for (int i = 0; i < 3; i++) {
        i++; // violation 'Control variable 'i' is modified.'
    }
}
