/*
ModifiedControlVariable
skipEnhancedForLoopVariable = true
tokens = (default)OBJBLOCK, LITERAL_FOR, FOR_ITERATOR, FOR_EACH_CLAUSE, ASSIGN, \
         PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, SR_ASSIGN, \
         BSR_ASSIGN, SL_ASSIGN, BAND_ASSIGN, BXOR_ASSIGN, BOR_ASSIGN, INC, \
         POST_INC, DEC, POST_DEC


*/

package com.puppycrawl.tools.checkstyle.checks.coding.modifiedcontrolvariable;

public class InputModifiedControlVariableEnhancedForLoopVariable2 {
    void m(int[] a) {
        for (int i = 0, j = 1; ; i++, j++) {
            for (int k : a) {
            }
        }
    }

    void m2(int[] a) {
        for (int i = 0, j = 1; ; i++, j++) {
            for (int k : a) {
                i++; // violation 'Control variable 'i' is modified.'
            }
        }
    }
}
