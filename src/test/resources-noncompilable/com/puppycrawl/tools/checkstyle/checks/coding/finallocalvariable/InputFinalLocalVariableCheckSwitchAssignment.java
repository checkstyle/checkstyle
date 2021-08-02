/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableCheckSwitchAssignment {
    private static final int staticValue = 2;
    private static final int staticField = switch(staticValue) {
        case 0 -> -1;
        case 2-> 2;
        default -> 3;
    };

    public InputFinalLocalVariableCheckSwitchAssignment() throws Exception {
        int a = 0; // violation
        final int b = 0;
        int c = 10;
        int d = 0;

        c = switch (a) {
            case 0 -> 5;
            case 1 -> 10;
            default -> switch(b) {
                    case 2 -> {
                        if (a == 0) {
                            d = 1; // reassign d
                        }
                        throw new Exception();
                    }
                    default -> 2;
                };
        };
    }

    public void foo() throws Exception {
        final int a = 0;

        int b = switch(a) { // violation
            case 0 -> {
                int x = 5; // violation
                int y = 6;
                if (a == 2) {
                    y = 7;
                }
                throw new Exception();
            }
            default -> 2;
        };

        int c = switch(b) {
            case 0 -> 1;
            default -> 2;
        };

        c = switch(a) {
            case 0 -> switch (b) {
                    case 0 -> 1;
                    case 1 -> 2;
                    default -> 3;
                };
            default -> 1;
        };
    }

}
