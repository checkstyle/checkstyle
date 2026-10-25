/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT, CTOR_DEF, METHOD_DEF, SLIST, OBJBLOCK, COMPACT_COMPILATION_UNIT, \
         LITERAL_BREAK, LITERAL_FOR, VARIABLE_DEF, EXPR


*/

package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableForLoop {
    public void method2() {
        for (int i = 0; i < 5; i++) {
            // violation below 'Variable 'x' should be declared final.'
            int x;
            x = 3;
        }
        int y;
        for (int i = 0; i < 5; i++) {
            y = 3;
        }
        for (int i = 0; i < 5; i++) {
            int z;
            for (int j = 0; j < 5; j++) {
                z = 3;
            }
        }
    }
}
