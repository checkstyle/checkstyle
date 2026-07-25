/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
validatePatternVariables = true
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
         LITERAL_FOR,VARIABLE_DEF,PATTERN_VARIABLE_DEF,EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariablePatternVariablesScope {
    public static boolean bigEnoughRect(String s) {
        if (!(s instanceof String r)) {
            return false;
        }
        r = "hello";
        return r.length() > 5;
    }

    public static int effectiveFinalRect(String s) {
        if (!(s instanceof String r)) { // violation
            return 0;
        }
        return r.length();
    }
    public static int testMultipleSameName(Object s) {
        if (s instanceof String r) { // violation
            System.out.println(r);
        }

        if (s instanceof Integer r) { // violation
            System.out.println(r);
        }

        return 0;
    }
}
