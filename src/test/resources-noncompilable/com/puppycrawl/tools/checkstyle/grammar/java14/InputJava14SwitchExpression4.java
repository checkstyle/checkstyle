// non-compiled with eclipse: local variable x may not have been initialized
// non-compiled with javac: Compilable with Java21 individually
package com.puppycrawl.tools.checkstyle.grammar.java14;

public class InputJava14SwitchExpression4 {

    enum T {
        A, B, C;
    }

    private static boolean isTrue() {
        return true;
    }

    static {
        int x;
        T e = T.B;

        boolean t8 = (switch (e) {
            case A:
                x = 1;
                yield isTrue();
            case B:
                yield (x = 1) == 1 || isTrue();
            default:
                yield false;
        }) && x == 1;

        if (!t8) {
            throw new IllegalStateException("Unexpected result.");
        }

        {
            T y = T.A;

            boolean t9 = (switch (y) {
                case A:
                    x = 1;
                    yield true;
                case B:
                    yield (x = 1) == 1 || true;
                default:
                    yield false;
            }) && x == 1;

            if (!t9) {
                throw new IllegalStateException("Unexpected result.");
            }
        }


        { //JDK-8221413: definite assignment for implicit default in switch expressions
            T y = T.A;

            int v = switch (y) {
                case A -> x = 0;
                case B -> x = 0;
                case C -> x = 0;
            };

            if (x != 0 || v != 0) {
                throw new IllegalStateException("Unexpected result.");
            }
        }

        { //JDK-8221413: definite assignment for implicit default in switch expressions
            T y = T.A;

            boolean tA = (switch (y) {
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

            if (!tA) {
                throw new IllegalStateException("Unexpected result.");
            }
        }
    }
}
