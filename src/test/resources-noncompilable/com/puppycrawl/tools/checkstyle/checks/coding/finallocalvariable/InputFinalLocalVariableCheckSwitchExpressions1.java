/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR


*/
// non-compiled with javac: Compilable with Java21 individually
// non-compiled with eclipse: local variable may not have been initialized
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableCheckSwitchExpressions1 {

    void foo1() throws Exception {

        Exception e; // violation "Variable 'e' should be declared final"

        final int a = (int) Math.random();

        switch (a) {
            case 0 -> e = new Exception();
            case 1 -> e = new Exception();
            default -> e = new Exception();
        }

        throw e;
    }

    void foo2() {
        int x = 0;
        final int a = (int) Math.random();

        int result = switch (a) { // violation "Variable 'result' should be declared final"
            case 0 -> {
                x = 1;
                yield x;
            }
            default -> {
                x = 2;
                yield x;
            }
        };
    }
}

