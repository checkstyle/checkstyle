/*
FallThrough
checkLastCaseGroup = true
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughWithPatternMatchingCheckLastCase {

    void testSwitchStatements(Object o) {
        switch (o) {
            case Integer i when i > 0:
                System.out.println("Positive integer");
            // violation below, 'Fall\ through from previous branch of the switch'
            case String _ when o.toString().isEmpty():
                System.out.println("empty string");
            default:
            // 2 violations above:
            //                     'Fall\ through from previous branch of the switch'
            //                     'Fall\ through from the last branch of the switch'
                System.out.println("default");
        }

        switch (o) {
            case Integer i when i > 0: {
                System.out.println("Positive integer");
            }
            // violation below, 'Fall\ through from previous branch of the switch'
            case String _ when o.toString().isEmpty(): {
                System.out.println("empty string");
            }
            default: {
            // 2 violations above:
            //                     'Fall\ through from previous branch of the switch'
            //                     'Fall\ through from the last branch of the switch'
                System.out.println("default");
            }
        }

        int x = switch (o) {
            case Integer i when i > 0:
                System.out.println("Positive integer");
            // violation below, 'Fall\ through from previous branch of the switch'
            case String _ when o.toString().isEmpty():
                yield 2;
            default:
                yield 3;
        };

        switch (o) {
            case Integer i when i > 0:
                System.out.println("Positive integer");
                break;
            case String s when o.toString().isEmpty():
                System.out.println("empty string");
                break;
            default:   // violation 'Fall\ through from the last branch of the switch'
                System.out.println("default");
        }
    }

    void testSwitchRule(Object o) {
        switch (o) {
            case Integer i when i > 0 -> System.out.println("Positive integer");
            case String s when s.isEmpty() -> System.out.println("empty string");
            default -> System.out.println("default");
        }

        switch (o) {
            case Integer i when i > 0 -> {
                System.out.println("Positive integer");
            }
            case String s when s.isEmpty() -> {
                System.out.println("empty string");
            }
            default -> System.out.println("default");
        }

        int x = switch (o) {
            case Integer i when i > 0 -> {
                System.out.println("Positive integer");
                yield 2;
            }
            case String s when s.isEmpty() -> {
                System.out.println("empty string");
                yield 3;
            }
            default -> {
                System.out.println("default");
                yield 4;
            }
        };

    }
}
