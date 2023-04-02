/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableFallThroughCaseGroup {
    void foo() {
        int value; // ok
        final int field = switch ((int) Math.random()) {
            case 1, 2:
                value = 1;
            default:
                value = 2;
                yield 3;
        };
    }

    void foo2() {
        int value;
        final int field = switch ((int) Math.random()) {
            case 1:
            case 2:
                value = 1;
            default:
                value = 2;
                yield 3;
        };
    }

    void foo3() {
        int value;
        final int field = switch ((int) Math.random()) {
            case 1:
                value = 1;
                yield 1;
            case 2:
                value = 4;
            default:
                value = 2;
                yield 3;
        };
    }
}
