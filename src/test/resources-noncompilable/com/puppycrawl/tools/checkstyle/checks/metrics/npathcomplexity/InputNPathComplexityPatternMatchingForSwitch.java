/*
NPathComplexity
max = 1


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityPatternMatchingForSwitch {

    // violation below, 'NPath Complexity is 3 (max allowed is 1)'
    void testPatternWithRule(Object o) {
        switch (o) {
            case Integer _ -> {}
            case String _ -> {}
            default -> {}
        }
    }

    // violation below, 'NPath Complexity is 3 (max allowed is 1)'
    void testPatternWithStatement(Object o) {
        switch (o) {
            case Integer _: {}
            case String _: {}
            default : {}
        }
    }

    // violation below, 'NPath Complexity is 3 (max allowed is 1)'
    void testRecordPatternWithRule(Object o) {
        switch (o) {
            case A(int x) -> {}
            case B(String s) -> {}
            default -> {}
        }
    }

    // violation below, 'NPath Complexity is 3 (max allowed is 1)'
    void testRecordPatternWithStatement(Object o) {
        switch (o) {
            case A(int x) : {} break;
            case B(String s) : {}
            default : {}
        }
    }

    // violation below, 'NPath Complexity is 5 (max allowed is 1)'
    void testGuardsInRule(Object o) {
        switch (o) {
            case Integer i when i > 0 -> {}
            case String s when s.length() > 0 -> {}
            default -> {}
        }
    }

    // violation below, 'NPath Complexity is 5 (max allowed is 1)'
    void testGuardsInStatement(Object o) {
        switch (o) {
            case Integer i when i > 0 : {} break;
            case String s when s.length() > 0 : {}
            default : {}
        }
    }

    // violation below, 'NPath Complexity is 4 (max allowed is 1)'
    void testMultiCaseLabelWithRule(Object o) {
        switch (o) {
            case Integer _, String _ , Double _ -> {}
            default -> {}
        }
    }

    // violation below, 'NPath Complexity is 4 (max allowed is 1)'
    void testMultiCaseLabelWithStatement(Object o) {
        switch (o) {
            case Integer _ :
            case String _  :
            case Double _  : {}
            default : {}
        }
    }

    // violation below, 'NPath Complexity is 3 (max allowed is 1)'
    void testExprInRule(int x) {
        switch (x) {
            case 1 -> {}
            case 2 -> {}
            default -> {}
        }
    }

    // violation below, 'NPath Complexity is 3 (max allowed is 1)'
    void testExprInStatement(int x) {
        switch (x) {
            case 1 : {}
            case 2 : {}
            default : {}
        }
    }

    record A(int x) {}
    record B(String s) {}
}
