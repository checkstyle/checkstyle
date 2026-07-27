/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
validatePatternVariables = true
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
         LITERAL_FOR,VARIABLE_DEF,PATTERN_VARIABLE_DEF,EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariablePatternVariablesScopeNonCompilable {
    public void test9(Object obj) {
        String s6 = "1";
        s6 = "2";
        if (obj instanceof String s6) {
            s6 = "3";
        }
    }

    public void test11(Object obj) {
        if (!(obj instanceof String s9)) { // violation
            s9 = "1";
        } else {
            System.out.println(s9);
        }
    }

    public void test12() {
        String s10 = "1";
        if (new Object() instanceof String s10) { // violation
        }
    }

    public void test16(Object obj) {
        if (obj instanceof String s18) { // violation
            return;
        }
        s18 = "1";
    }

    public void test14(Object obj) {
        switch (obj instanceof String s14 ? 1 : 2) {
            case 1 -> {
                s14 = "1";
            }
            default -> {}
        }
    }

    public void test18(Object obj) {
        while (obj instanceof String s21) { // violation
        }
        s21 = "1";
    }

    public void test19(Object obj) {
        if (!(obj instanceof String s22)) { // violation
            System.out.println("not string");
        }
        s22 = "1";
    }
}
