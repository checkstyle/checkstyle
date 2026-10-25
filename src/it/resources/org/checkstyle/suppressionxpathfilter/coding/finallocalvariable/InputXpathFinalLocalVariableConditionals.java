/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT, CTOR_DEF, METHOD_DEF, SLIST, OBJBLOCK, COMPACT_COMPILATION_UNIT, \
         LITERAL_BREAK, LITERAL_FOR, VARIABLE_DEF, EXPR


*/

package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableConditionals {
    private void checkCodeBlock() {
        try {

            final int from;
            if (true) {
                from = 0;
            } else if (true) {
                // violation below 'Variable 'body' should be declared final.'
                boolean body = false;
                if (body) return;
                from = 0;
            } else {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
