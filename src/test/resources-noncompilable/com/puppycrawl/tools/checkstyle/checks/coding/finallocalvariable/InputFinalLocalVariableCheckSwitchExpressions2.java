/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)VARIABLE_DEF

*/

// non-compiled with eclipse: local variable may not have been initialized
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableCheckSwitchExpressions2 {

    void foo1() throws Exception {

        Exception e; // violation, "Variable 'e' should be declared final"

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> {
                if (b == 0) {
                    e = new Exception();
                } else {
                    e = new Exception();
                }
            }
            default -> e = new Exception();
        }

        throw e;
    }

    void foo2() {
        int x = 0; // violation, "Variable 'x' should be declared final"
        final int a = (int) Math.random();

        switch (a) {
            case 0:
                x = 1;
                break;
            default:
                x = 2;
                break;
        }
    }
}

