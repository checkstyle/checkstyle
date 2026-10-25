/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT, CTOR_DEF, METHOD_DEF, SLIST, OBJBLOCK, COMPACT_COMPILATION_UNIT, \
         LITERAL_BREAK, LITERAL_FOR, VARIABLE_DEF, EXPR


*/

package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableMethodDef {
    public void testMethod() {
        // violation below 'Variable 'x' should be declared final.'
        int x;
        x = 3;
    }
}
