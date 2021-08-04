/*
IllegalIdentifierName
format = (default)(?i)^(?!(record|yield|var|permits|sealed|_)$).+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

import java.util.function.Function;

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
        var, // violation
    }

    int yield(Day day) { // violation
        return switch (day) {
            case MON, TUE -> Math.addExact(0, 1);
            case WED -> Math.addExact(1, 1);
            case THU, SAT, FRI, SUN -> 0;
            case var -> 23; // ok, caught above in initialization
            default -> {
                Function<String, String> f4 = var -> var; // violation
                yield Math.addExact(2, 1); // ok, yield statement
            }
        };
    }
}
