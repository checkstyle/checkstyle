/*
NPathComplexity
max = 1


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.metrics.npathcomplexity;

public class InputNPathComplexityCheckSwitchExpression {
    void howMany1(Nums k) { // violation
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

    void howMany2(Nums k) { // violation
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

    int howMany3(Nums k) { // violation
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

    int howMany4(Nums k) { // violation
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
