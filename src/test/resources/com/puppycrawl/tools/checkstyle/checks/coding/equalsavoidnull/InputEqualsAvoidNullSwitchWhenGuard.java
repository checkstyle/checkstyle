/*
EqualsAvoidNull
ignoreEqualsIgnoreCase = (default)false


*/


package com.puppycrawl.tools.checkstyle.checks.coding.equalsavoidnull;

public class InputEqualsAvoidNullSwitchWhenGuard {

    void switchWhenGuard(Object obj) {
        switch (obj) {
            // violation below 'left .* of .* equals'
            case String s when s.equals("hello") -> System.out.println("match");
            case String s -> System.out.println(s);
            default -> System.out.println("other");
        }
    }

    String switchExpressionWhenGuard(Object obj) {
        return switch (obj) {
            // violation below 'left .* of .* equals'
            case String s when s.equals("hello") -> "greeting";
            case String s -> s;
            default -> "unknown";
        };
    }

    void singleCaseNoConflict(Object obj) {
        switch (obj) {
            // violation below 'left .* of .* equals'
            case String s when s.equals("test") -> System.out.println("match");
            default -> System.out.println("other");
        }
    }
}
