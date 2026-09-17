/*
IllegalIdentifierName
format = (default)^(?!var$|\\S*\\$)\\S+$
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, ANNOTATION_DEF, ANNOTATION_FIELD_DEF, \
         PARAMETER_DEF, VARIABLE_DEF, METHOD_DEF, ENUM_CONSTANT_DEF, PATTERN_VARIABLE_DEF, \
         RECORD_DEF, RECORD_COMPONENT_DEF, LAMBDA


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.illegalidentifiername;

import java.util.function.Function;

public class InputIllegalIdentifierNameLambda {

    public static void main(String... args) {
        // violation below 'Name 'param\$' must match pattern'
        Function<String, String> f1 = param$ -> param$;
        // violation below 'Name 'var' must match pattern'
        Function<String, String> f2 = (var) -> var;
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
        // violation below 'Name 'var' must match pattern'
        var,
    }

    int yield(Day day) {
        return switch (day) {
            case MON, TUE -> Math.addExact(0, 1);
            case WED -> Math.addExact(1, 1);
            case THU, SAT, FRI, SUN -> 0;
            case var -> 23; // ok, caught above in initialization
            default -> {
                // violation below 'Name 'te\$t' must match pattern'
                Function<String, String> f4 = te$t -> te$t;
                yield Math.addExact(2, 1);
            }
        };
    }
}
