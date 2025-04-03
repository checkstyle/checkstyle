/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF
validateUnnamedVariables = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;


public class InputFinalLocalVariableBreak {

    void foo1() throws Exception {
        Exception e; // violation, "Variable 'e' should be declared final"
        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
        case 0:
            e = new Exception();
            break;
        case 1:
            if (b == 0) {
                e = new Exception();
                break;
            }

            if (b == 1) {
                e = new Exception();
            }
            else {
                e = new Exception();
            }
            break;
        case 2:
            if (b == 0) {
                return;
            }

            e = new Exception();
            break;
        default:
            e = new Exception();
            break;
        }

        throw e;
    }

    int foo2() {
        int a;
        if (true) {
            a = 1;
        } else {
            a = 2;
            if (a == 3) {
                return a;
            }
            a = 4;
        }
        return a;
    }
}
