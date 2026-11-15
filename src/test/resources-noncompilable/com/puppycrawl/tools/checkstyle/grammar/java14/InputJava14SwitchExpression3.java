// non-compiled with eclipse: local variable x may not have been initialized
// non-compiled with javac: Compilable with Java21 individually
package com.puppycrawl.tools.checkstyle.grammar.java14;

public class InputJava14SwitchExpression3 {

    enum T {
        A, B, C;
    }

    private static boolean isTrue() {
        return true;
    }

    static {
        int x;

        int t7 = new DefiniteAssignment1().id(switch (0) {
            default -> isTrue();
        } && (x = 1) == 1 && x == 1 ? 2 : -1);

        if (t7 != 2) {
            throw new IllegalStateException("Unexpected result.");
        }
    }

    static {
        int x;
        T e = T.B;

        boolean t8 = (switch (e) {
            case A:
                x = 1;
                yield true;
            case B:
                yield (x = 1) == 1 || true;
            default:
                yield false;
        }) && x == 1;

        if (!t8) {
            throw new IllegalStateException("Unexpected result.");
        }
    }

    static {
        final int x;
        T e = T.C;

        boolean tA = (switch(e) {
            case A: x = 1; yield true;
            case B: yield (x = 2) == 2 || true;
            default: yield false;
        }) || (x = 3) == 3;

        if (x != 3) {
            throw new IllegalStateException("Unexpected result.");
        }
    }

    static {
        int x;
        T e = T.A;

        boolean tA = (switch (e) {
            case A:
                yield isTrue() && e != T.C ? (x = 1) == 1 && e != T.B : false;
            case B:
                yield (x = 1) == 1 || isTrue();
            default:
                yield false;
        }) && x == 1;

        if (!tA) {
            throw new IllegalStateException("Unexpected result.");
        }
    }

    static { //JDK-8221413: definite assignment for implicit default in switch expressions
        int x;
        T e = T.A;

        int v = (switch(e) {
            case A -> x = 0;
            case B -> x = 0;
            case C -> x = 0;
        });

        if (x != 0 || v != 0) {
            throw new IllegalStateException("Unexpected result.");
        }
    }

    { //JDK-8221413: definite assignment for implicit default in switch expressions
        int x;
        T e = T.A;

        boolean tA = (switch(e) {
            case A -> { x = 1; yield true; }
            case B -> { x = 1; yield true; }
            case C -> { x = 1; yield true; }
        }) && x == 1;

        if (!tA) {
            throw new IllegalStateException("Unexpected result.");
        }
    }

    private record DefiniteAssignment1() {
        static int id(int id){return id;}
    }
}
