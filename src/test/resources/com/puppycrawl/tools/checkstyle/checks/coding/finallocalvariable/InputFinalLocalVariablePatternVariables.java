/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
validatePatternVariables = true
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
         LITERAL_FOR,VARIABLE_DEF,PATTERN_VARIABLE_DEF,EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariablePatternVariables {
    public void run(String... arguments) {
        final Object o = 45;
        if (o instanceof String p) { // violation "Variable 'p' should be declared final"
            System.out.println(p);
        }
        if (o instanceof final String p) {
            System.out.println(p);
        }
        if (o instanceof String p) {
            p = "rewrite";
            System.out.println(p);
        }
        if (o instanceof String p) {
            p = new String("p");
        }
        final boolean value = o instanceof String p; // violation "Variable 'p' should be declared final"
    }
}
