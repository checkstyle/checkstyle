//non-compiled with javac: Compilable with Java12
package com.puppycrawl.tools.checkstyle.javaparser;

import java.util.EnumSet;

public class InputJavaParserSwitchExpressionTest {

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
        };
    }

    int breakAcceptsExpressions(Day day) {
        return switch (day) {
            case MON, TUE -> 0;
            case WED -> 1;
            default -> break day.toString().length() > 5 ? 1 : 0;
        };
    }

    int complexCase(Day day) {
        return switch (day) {
            case MON, TUE -> {
                int l = day.toString().length();
                break Math.addExact(l, 0);
            }
            case WED -> {
                int l = day.toString().length();
                break Math.addExact(l, 1);
            }
            default -> {
                int l = day.toString().length();
                break Math.addExact(l, 2);
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
         return - switch (day) {
             case MON, FRI, SUN -> 6;
             case TUE -> 7;
             case THU, SAT -> 8;
             case WED -> 9;
         };
     }

    int usedOnBothSidesOfAriphmeticExpression(Day day) {
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

}

