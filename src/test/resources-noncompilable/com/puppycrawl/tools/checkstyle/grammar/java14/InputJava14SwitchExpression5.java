// non-compiled with eclipse: local variable x may not have been initialized
// non-compiled with javac: Compilable with Java21 individually
package com.puppycrawl.tools.checkstyle.grammar.java14;

import java.util.function.Supplier;

public class InputJava14SwitchExpression5 {

    enum T {
        A, B, C;
    }

    private static boolean isTrue() {
        return true;
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

    private String print2(int i, int j, int k) {
        return switch (i) {
            case 0:
                String r;
                OUTER: switch (j) {
                    case 0:
                        String res;
                        INNER: switch (k) {
                            case 0: res = "0-0-0"; break;
                            case 1: res = "0-0-1"; break;
                            case 2: res = "0-0-2"; break INNER;
                            default: r = "0-0-X"; break OUTER;
                        }
                        r = res;
                        break;
                    case 1:
                        r = "0-1";
                        break;
                    default:
                        r = "0-X";
                        break;
                }
                yield r;
            case 1:
                yield "1";
            case 2:
                LOP: while (j-- > 0) {
                    if (k == 5) {
                        k--;
                        continue;
                    }
                    break LOP;
                }
                Supplier<String> getter = () -> { return "2-X-5"; };
                yield getter.get();
            default:
                yield "X";
        };
    }
}
