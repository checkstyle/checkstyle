/*
IllegalIdentifierName
format = (default)^(?!var$|.*\\$).+
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

import java.util.function.Function;

public class InputIllegalIdentifierNameLambda {

    public static void main(String... args) {
        Function<String, String> f1 = param$ -> param$; // violation, 'must match pattern'
        Function<String, String> f2 = (var) -> var; // violation, 'Name 'var' must match pattern'
        Function<String, String> f3 = myLambdaParam -> myLambdaParam;
    }

    enum Day {
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT,
        SUN,
        var, // violation, 'Name 'var' must match pattern'
    }

    int yield(Day day) {
        return switch (day) {
            case MON, TUE -> Math.addExact(0, 1);
            case WED -> Math.addExact(1, 1);
            case THU, SAT, FRI, SUN -> 0;
            case var -> 23; // ok, caught above in initialization
            default -> {
                Function<String, String> f4 = te$t -> te$t; // violation, 'must match pattern'
                yield Math.addExact(2, 1);
            }
        };
    }
}
