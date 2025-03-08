/*
CyclomaticComplexity
max = 0
switchBlockAsSingleDecisionPoint = true
tokens = (default)LITERAL_WHILE, LITERAL_DO, LITERAL_FOR, LITERAL_IF, LITERAL_SWITCH, \
         LITERAL_CASE, LITERAL_CATCH, QUESTION, LAND, LOR


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.metrics.cyclomaticcomplexity;

public class InputCyclomaticComplexitySwitchBlocks6 {

    // violation below, 'Cyclomatic Complexity is 2 (max allowed is 0)'
    void test1(Object obj1, Object obj2) { // 1, method declaration
        switch (obj1) { // 2, switch
            case Integer i1 when obj2 instanceof Integer i2 -> System.out.println("1");
            case String s1 when obj2 instanceof String s2 -> System.out.println("2");
            default -> System.out.println("none");
        }
    }

    // violation below, 'Cyclomatic Complexity is 2 (max allowed is 0)'
    void test2(Object obj1, Object obj2) { // 1, method declaration
        switch (obj1) { // 2, switch
            case Integer i1 -> {
                if (obj2 instanceof Integer i2)   // if ignored
                    System.out.println("Integers: %d, %d".formatted(i1, i2));
            }
            case String s1 -> {
                if (obj2 instanceof String s2)  // if ignored
                    System.out.println("Strings: %s, %s".formatted(s1, s2));
            }
            default -> System.out.println("none");
        }
    }

    // violation below, 'Cyclomatic Complexity is 2 (max allowed is 0)'
    void test3(Object obj1, Object obj2, Object obj3) {
        int j1 = 0, k1 = 0, l1 = 0, m1 = 0; // Initialized variables

        switch (obj1) { // 1
            case Integer i -> {
                if (i == 1) { // ignored
                    System.out.println("Integers");
                }
                else if (i == 2) {
                    switch (obj2) { // ignored
                        case Integer s1 -> System.out.println("Integers");
                        default -> System.out.println("none");
                    }
                }
                else if (i == 3) {
                    if (j1 == 1) { // ignored
                        k1 = 1;
                    } else {
                        l1 = 1;
                    }
                }
                else if (i == 4) {
                    switch (obj3) { // ignored
                        case Integer s2 -> j1++;
                        default -> System.out.println("none");
                    }
                }
                else {
                    System.out.println("none");
                }
            }
            default -> System.out.println("none");
        }
    }
}
