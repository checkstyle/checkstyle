/*
ModifiedControlVariable
skipEnhancedForLoopVariable = (default)false
tokens = (default)OBJBLOCK, LITERAL_FOR, FOR_ITERATOR, FOR_EACH_CLAUSE, ASSIGN, \
         PLUS_ASSIGN, MINUS_ASSIGN, STAR_ASSIGN, DIV_ASSIGN, MOD_ASSIGN, SR_ASSIGN, \
         BSR_ASSIGN, SL_ASSIGN, BAND_ASSIGN, BXOR_ASSIGN, BOR_ASSIGN, INC, \
         POST_INC, DEC, POST_DEC


*/

package com.puppycrawl.tools.checkstyle.checks.coding.modifiedcontrolvariable;

public class InputModifiedControlVariableEnhancedForLoopVariable3 {

    public void method2()
    {
        final String[] lines = {"line1", "line2", "line3"};
        for (String line: lines) {
            line = line.trim(); // violation
        }
    }
}
