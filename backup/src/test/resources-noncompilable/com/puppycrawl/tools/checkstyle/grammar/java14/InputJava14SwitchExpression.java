//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.java14;

import static java.time.Instant.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

public class InputJava14SwitchExpression {

    static class HardToParse {
        void foo() {
            Instant Ascii;
            byte[] decodabet = new byte[1];
            Arrays.fill(decodabet, (byte) -1);
            char[] chars = {'c','h','a','r','s'};
            for (
                    int i = 0;
                    i < chars.length; i++) {
                char c = chars[i];
                decodabet[c] = (byte) i;
            }
        }
        IntConsumer consumer = (IntConsumer) i -> {
        };

        static String yield = "yield";

        class yieldClass{
            int yield = 6;

            public void yield(){

            }
        }

    }

    enum Day {
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT,
        SUN,
    }

    String whatIsToday(Day day) {
        return switch (day) {
            case SAT, SUN -> "Weekend";
            case MON, TUE, WED, THU, FRI -> "Working day";
            default -> throw new IllegalArgumentException("Invalid day: " + day.name());
        };
    }

    Set<Day> days(String weekPart) {
        return switch (weekPart) {
            case "Weekend" -> EnumSet.of(Day.SAT, Day.SUN);
            case "Working day" -> EnumSet.of(Day.MON, Day.TUE, Day.WED, Day.THU, Day.FRI);
            default -> throw new IllegalArgumentException("Invalid weekPart: " + weekPart);
        };
    }

    String isDayNameLong(Day day) {
        return switch (day) {
            case MON, FRI, SUN -> 6;
            case TUE -> 7;
            case THU, SAT -> 8;
            case WED -> 9;
        } > 7 ? "long" : "short";
    }

    int assignement(Day day) {
        int numLetters = switch (day) {
            case MON, FRI, SUN -> 6;
            case TUE -> 7;
            case THU, SAT -> 8;
            case WED -> 9;
        };
        return numLetters;
    }

    static void howMany(int k) {
        switch (k) {
            case 1 -> System.out.println("one");
            case 2 -> System.out.println("two");
            case 3 -> System.out.println("many");
            default -> throw new IllegalArgumentException("Unknown");
        }
    }

    int methodCalls(Day day) {
        return switch (day) {
            case MON, TUE -> Math.addExact(0, 1);
            case WED -> Math.addExact(1, 1);
            default -> {
                yield Math.addExact(2, 1);
            }
        };
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
            //OK
        }
    }

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

    private String expression1(T t) {
        String help = "";
        return switch (t) {
            case A: help = "a";
            case B: help += "b";
            default: yield help;
        };
    }

    private String expression2(T t) {
        String help = "";
        return switch (t) {
            case A: help = "a";
            case B: help += "b";
            default: yield help;
        };
    }

    I<String> lambdaCapture1(int i) {
        int j = i + 1;
        I<String> r = switch (i) {
            case 0 -> () -> "0" + i; //capture parameter
            case 1 -> () -> "1" + j; //capture local variable
            default -> {
                String k = "D";
                yield () -> k; //capture local from the switch expr.
            }
        };

        return r;
    }

    I<String> lambdaCapture2(int i) {
        int j = i + 1;

        return switch (i) {
            case 0 -> () -> "0" + i; //capture parameter
            case 1 -> () -> "1" + j; //capture local variable
            default -> {
                String k = "D";
                yield () -> k; //capture local from the switch expr.
            }
        };
    }

    interface I<T> {
        public T t();
    }

    static {
        int i = 0;
        int dummy = 1 + switch (i) {
            case -1: yield 1;
            default:
                i++;
                yield 1;
        };
        if (i != 1) {
            throw new IllegalStateException("Side effects missing.");
        }
    }

    private static int getValueViaYield(String mode) {
        int yield = 0;
        return switch (mode) {
            case "a", "b":
                int x = yield + 2;
                yield x;
            default:
                yield - 1;
        };
    }

    private static int getValueViaYield2(String mode) {
        return switch (mode) {
            case "a", "b":
                int yield = 42;
                yield yield;
            default:
                yield - 1;
        };
    }
}
