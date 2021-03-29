//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

import java.util.function.Function;
/* Config:
 * format = "(?i)^(?!(record|yield|var|permits|sealed|_)$).+$"
 */
public class InputIllegalIdentifierNameLambda {

    public static void main(String... args) {
        Function<String, String> f1 = var -> var; // violation
        Function<String, String> f2 = (var) -> var; // violation
        Function<String, String> f3 = myLambdaParam -> myLambdaParam; // ok
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

    int yield(Day day) { // violation
        return switch (day) {
            case MON, TUE -> Math.addExact(0, 1);
            case WED -> Math.addExact(1, 1);
            case THU, SAT, FRI, SUN -> 0;
            default -> {
                Function<String, String> f4 = var -> var; // violation
                yield Math.addExact(2, 1); // ok, yield statement
            }
        };
    }
}
