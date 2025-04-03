/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF
validateUnnamedVariables = (default)false


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariableCheckSwitchExpressions {
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
                } else {
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

    void foo2() throws Exception {

        Exception e; // violation, "Variable 'e' should be declared final"

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> {
                e = new Exception();
            }
            case 1 -> {
                if (b == 0) {
                    e = new Exception();
                    break;
                }

                if (b == 1) {
                    e = new Exception();
                } else {
                    e = new Exception();
                }
            }
            case 2 -> {
                if (b == 0) {
                    return;
                }

                e = new Exception();
            }
            default -> {
                e = new Exception();
            }
        }

        throw e;
    }

    void foo3() throws Exception {

        Exception e; // violation, "Variable 'e' should be declared final"

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> e = new Exception();
            case 1 -> {
                if (b == 0) {
                    e = new Exception();
                    break;
                }

                if (b == 1) {
                    e = new Exception();
                } else {
                    e = new Exception();
                }
            }
            case 2 -> {
                if (b == 0) {
                    return;
                }

                e = new Exception();
            }
            default -> e = new Exception();
        }

        throw e;
    }

    void foo4() throws Exception {

        Exception e; // violation, "Variable 'e' should be declared final"

        final int a = (int) Math.random();
        final int b = (int) Math.random();

        switch (a) {
            case 0 -> e = new Exception();
            case 1 -> System.out.println("test!");
            default -> System.out.println("Exception!");

        }

    }

    enum T {
        A, B, C;
    }

    public void foo5() {
        int x;
        final T e = T.B;

        final boolean t8 = (switch (e) {
        case A:
            x = 1;
            yield isTrue();
        case B:
            yield (x = 1) == 1 || isTrue();
        default:
            yield false;
        }) && x == 1;

        {
            final T y = T.A;

            final boolean t9 = (switch (y) {
            case A:
                x = 1;
                yield true;
            case B:
                yield (x = 1) == 1 || true;
            default:
                yield false;
            }) && x == 1;
        }

        {
            final T y = T.A;

            final int v = switch (y) {
            case A -> x = 0;
            case B -> x = 0;
            case C -> x = 0;
            };

            if (x != 0 || v != 0) {
                throw new IllegalStateException("Unexpected result.");
            }
        }

        {
            final T y = T.A;

            final boolean tA = (switch (y) {
            case A -> {
                x = 1;
                yield true;
            }
            case B -> {
                x = 1;
                yield true;
            }
            case C -> {
                x = 1;
                yield true;
            }
            }) && x == 1;
        }
    }

    private boolean isTrue() {
        return true;
    }
}
