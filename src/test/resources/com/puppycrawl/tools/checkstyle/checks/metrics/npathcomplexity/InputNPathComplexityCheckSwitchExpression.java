/*
NPathComplexity
max = 1


*/


package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityCheckSwitchExpression {
    // violation below 'NPath Complexity is 5 (max allowed is 1).'
    void howMany1(Nums k) {
        switch (k) {
            case ONE: {
                System.out.println("case two");
            }
            case TWO:
            case THREE:{
                System.out.println("case two");
            }
            case FOUR: {
                System.out.println("case three");
            }
            default:
                throw new IllegalStateException("Not a nums");
        }
    }

    // violation below 'NPath Complexity is 5 (max allowed is 1).'
    void howMany2(Nums k) {
        switch (k) {
            case ONE -> {
                System.out.println("case one");
            }
            case TWO, THREE -> {
                System.out.println("case two");
            }
            case FOUR -> {
                System.out.println("case three");
            }
            default -> throw new IllegalStateException("Not a nums");
        }
    }

    // violation below 'NPath Complexity is 6 (max allowed is 1).'
    int howMany3(Nums k) {
        return switch (k) {
            case ONE:
                yield 3;
            case TWO:
            case THREE:{
                yield 5;
            }
            case FOUR: {
                yield 9;
            }
            default:
                throw new IllegalStateException("Not a Nums");
        };
    }

    // violation below 'NPath Complexity is 6 (max allowed is 1).'
    int howMany4(Nums k) {
        return switch (k) {
            case ONE -> {
                yield 4;
            }
            case TWO, THREE -> {
                yield 42;
            }
            case FOUR -> {
                yield 99;
            }
            default -> throw new IllegalStateException("Not a Nums");
        };
    }


    enum Nums {ONE, TWO, THREE, FOUR}
}
