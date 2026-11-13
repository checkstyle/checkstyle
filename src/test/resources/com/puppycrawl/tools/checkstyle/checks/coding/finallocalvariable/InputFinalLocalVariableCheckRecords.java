/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public record InputFinalLocalVariableCheckRecords(boolean t, boolean f) {
    public InputFinalLocalVariableCheckRecords {
        int a = 0;
        a = 1;
    }

    record bad(int i) {
        public bad {
            int b = 0; // violation "Variable 'b' should be declared final"
        }
    }

    static {
        int c; // violation "Variable 'c' should be declared final"
        c = 1;
        int d = 0;
        d = 2;
        int e = 0; // violation "Variable 'e' should be declared final"
        int h;
        h = 1;
        h = 2;
    }

    record NestedRecord(int x) {
        static {
            int f; // violation "Variable 'f' should be declared final"
            f = 1;
            int g = 0; // violation "Variable 'g' should be declared final"
        }
    }
}

