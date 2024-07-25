/*
NeedBraces
allowSingleLineStatement = false
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;


public class InputNeedBracesSwitchLabeledExpression {

    int m1(int x, int z) {
        int a;

        a = switch (x) {
            case 1 -> 1;
            case 2 -> 2;
            default -> 4;
        };

        int y = switch (x) {
            case 1 -> switch (z) {
                case 1 -> 1;
                case 2 -> 2;
                default -> 4;
            };
            case 2 -> 2;
            default -> 4;
        };

        return switch (x) {
            case 1 -> 1;
            case 2 -> 2;
            default -> throw new IllegalStateException();
            // violation above, ''default' construct must use '{}'s'
        };
    }

    int m2(int x, int z) {

        int y = switch (x) {
            case 1 -> {
                yield switch (z) {
                    case 1 -> 1;
                    case 2 -> 2;
                    default -> 4;
                };
            }
            case 2 -> 2;
            default -> 4;
        };

        return switch (x) {
            case 1 -> {
                yield 1;
            }
            case 2 -> {
                yield 2;
            }
            default -> {
                throw new IllegalStateException();
            }
        };
    }
}