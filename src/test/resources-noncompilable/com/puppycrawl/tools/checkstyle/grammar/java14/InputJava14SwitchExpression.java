// non-compiled with eclipse: local variable x may not have been initialized
// non-compiled with javac: Compilable with Java21 individually
package com.puppycrawl.tools.checkstyle.grammar.java14;
import static java.time.Instant.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.IntConsumer;

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

    int assignment(Day day) {
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
}
