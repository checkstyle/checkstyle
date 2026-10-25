/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT, CTOR_DEF, METHOD_DEF, SLIST, OBJBLOCK, COMPACT_COMPILATION_UNIT, \
         LITERAL_BREAK, LITERAL_FOR, VARIABLE_DEF, EXPR


*/

package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableTryBlock {
    private void checkCodeBlock() {
        try {
            // violation below 'Variable 'start' should be declared final.'
            int start = 0;

            final int from;
            if (true) {
                from = 0;
            } else {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
