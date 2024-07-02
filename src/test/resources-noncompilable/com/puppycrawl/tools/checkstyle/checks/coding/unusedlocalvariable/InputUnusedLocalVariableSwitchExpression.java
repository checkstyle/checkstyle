/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

public class InputUnusedLocalVariableSwitchExpression {

    public int complexSwitch(int input) {
        int check = 8;
        int something = 0;
        int line = 1;
        int line2 = 9; // violation 'Unused local variable 'line2''
        return switch (check++) {
            case 0 -> {
                int x = 2;
                line2++;
                yield switch (x) {
                    case 1 -> x++;
                    case 2 -> x--;
                    default -> 0;
                };
            }
            case 1 -> {
                int y = input * 2;
                yield switch (++line) {
                    case 2, 4 -> y / 2;
                    case 6 -> y - 1;
                    default -> -1;
                };
            }
            case 2 -> {
                int checks = switch (something++) {
                    case 1 -> input/2;
                    default -> input;
                };
                yield checks;
            }
            default -> 0;
        };
    }
}
