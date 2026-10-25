/*
FinalLocalVariable
validateEnhancedForLoopVariable = true
validateUnnamedVariables = (default)false
tokens = (default)IDENT, CTOR_DEF, METHOD_DEF, SLIST, OBJBLOCK, COMPACT_COMPILATION_UNIT, \
         LITERAL_BREAK, LITERAL_FOR, VARIABLE_DEF, EXPR


*/

package org.checkstyle.suppressionxpathfilter.coding.finallocalvariable;

public class InputXpathFinalLocalVariableEnhancedFor {
    public void method1()
    {
        final java.util.List<Object> list = new java.util.ArrayList<>();

        // violation below 'Variable 'a' should be declared final.'
        for(Object a : list){
        }
    }
}
