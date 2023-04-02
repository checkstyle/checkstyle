/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFallThroughCaseGroup {
    void foo() {
        int a; // ok
        switch ((int) Math.random()) {
            case 0:
                a = 0;
            default:
                a = 2;
                break;
        }
    }

    void foo2() {
        int a; // ok
        switch ((int) Math.random()) {
            case 0:
            case 1:
            case 2:
                a = 1;
            default:
                a = 2;
                break;
        }
    }

    void foo3() {
        int a; // ok
        switch ((int) Math.random()) {
            case 0:
                a = 0;
                break;
            case 1:
            case 2:
                a = 1;
            default:
                a = 2;
                break;
        }
    }
}
