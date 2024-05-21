/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughSwitchRules {

    int method(int a, int b) {
        top:
        switch (a) {
            case 1 -> {
                switch (b) {
                    case 10 -> {
                        break top; // ok as terminates outer 'top' switch
                    }
                    default -> {
                        return 11;
                    }
                }
            }
            case 2 -> {
                return 2;
            }
        }

        outer:
        switch (a) {
            case 1 -> {
                inner:
                switch (b) {
                    case 10 -> throw new IllegalArgumentException("Invalid input");
                    default -> {
                        break outer; // ok as terminates outer 'outer' switch
                    }
                }
            }
            case 2 -> {
                return 2;
            }
        }

        loop:
        for (int i = 0; i < 3; i++) {
            switch (a) {
                case 1 -> {
                    switch (b) {
                        case 10 -> {
                            break loop; // ok as terminates outer 'loop'
                        }
                        default -> System.out.println("Default case");
                    }
                }
                case 2 -> {
                    return 2;
                }
            }
        }

        return -1;
    }
}
