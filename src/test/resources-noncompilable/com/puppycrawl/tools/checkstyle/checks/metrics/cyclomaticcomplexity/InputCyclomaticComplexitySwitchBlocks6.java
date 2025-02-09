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
            case Integer i1 when obj2 instanceof Integer i2 -> System.out.println("Integers: %d, %d");
            case String s1 when obj2 instanceof String s2 -> System.out.println("Strings: %s, %s");
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
        int j,k,l,m;
        switch (obj1) { // 1
            case Integer i1:
            case Integer i2:
            case Integer i3:
                break;
            case Integer i4:
                switch (obj2) { // ignored
                    case Integer i1:
                       j = 1;
                       break;
                    default:
                        break;
                }
                break;
            case Integer i5:
                if (j == 1) {
                    k = 1;
                }
                else {
                    l == 1;
                }
                break;
            case Integer i6:
                switch (obj3) { // ignored
                    case Integer i6:
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }
}
