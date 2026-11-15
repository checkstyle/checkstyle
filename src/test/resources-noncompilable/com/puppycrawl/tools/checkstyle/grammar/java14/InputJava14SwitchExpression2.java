// non-compiled with eclipse: local variable x may not have been initialized
// non-compiled with javac: Compilable with Java21 individually
package com.puppycrawl.tools.checkstyle.grammar.java14;

public class InputJava14SwitchExpression2 {

    enum Day {
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT,
        SUN,
    }

    int yieldAcceptsExpressions(Day day) {
        return switch (day) {
            case MON, TUE -> 0;
            case WED -> 1;
            default -> day.toString().length() > 5 ? 1 : 0;
        };
    }

    int complexCase(Day day) {
        return switch (day) {
            case MON, TUE -> {
                int l = day.toString().length();
                yield Math.addExact(l, 0);
            }
            case WED -> {
                int l = day.toString().length();
                yield Math.addExact(l, 1);
            }
            default -> {
                int l = day.toString().length();
                yield Math.addExact(l, 2);
            }
        };
    }

    int arithmetic(Day day) {
        return switch (day) {
            case MON, FRI, SUN -> 6;
            case TUE -> 7;
            case THU, SAT -> 8;
            case WED -> 9;
        } % 2;
    }

    int signArithmetic(Day day) {
        return -switch (day) {
            case MON, FRI, SUN -> 6;
            case TUE -> 7;
            case THU, SAT -> 8;
            case WED -> 9;
        };
    }

    int usedOnBothSidesOfArithmeticExpression(Day day) {
        return switch (day) {
            case MON, TUE -> 0;
            case WED -> 1;
            default -> 2;
        } * switch (day) {
            case WED, THU -> 3;
            case FRI -> 4;
            default -> 5;
        };
    }

    {
        Integer i = 0;
        Object o1 = "o1";

        if (!switch (i) {
            default:
                if (!(o1 instanceof String str)) {
                    yield false;
                }
                if (str.isEmpty()) {
                    yield true;
                }
                yield true;
        }) {
            throw new AssertionError();
        }
    }

    public static void main(String... args) {
        T t = T.B;
        int a = 8;
        int x;

        System.out.println("works!");
        boolean t1b = (switch (a) {
            case 0:
                yield (x = 1) == 1;
            default:
                yield false;
        }) && x == 1;

        try {
            int ii = switch (t) {
                case A -> 0;
                default -> throw new IllegalStateException();
            };
            throw new AssertionError("Expected exception not thrown.");
        } catch (IllegalStateException ex) {

        }
    }

    enum T {
        A, B, C;
    }
}
