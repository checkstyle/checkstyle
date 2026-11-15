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

    private record DefiniteAssignment1() {
        static int id(int id){return id;}
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

}
