/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT, LAMBDA


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesTestSwitchExpression {
    void howMany1(Nums k) {
        switch (k) {
            case ONE: // violation, 'case' construct must use '{}'s
                System.out.println("case two");
                MathOperation2 case5 = (a, b) -> // violation, '->' construct must use '{}'s
                        (a + b);
            case TWO, THREE: // violation, 'case' construct must use '{}'s
                System.out.println("case two");

            case FOUR: // violation, 'case' construct must use '{}'s
                System.out.println("case three");

            default: // violation, 'default' construct must use '{}'s
                throw new IllegalStateException("Not a nums");
        }
    }

    void howMany2(Nums k) {
        switch (k) { // cannot have more than one statement without block
            case ONE -> // violation, 'case' construct must use '{}'s
                    System.out.println("case one");

            case TWO, THREE -> // violation, 'case' construct must use '{}'s
                    System.out.println("case two");

            case FOUR -> // violation, 'case' construct must use '{}'s
                    System.out.println("case three");

            default -> // violation, 'default' construct must use '{}'s
                    throw new IllegalStateException("Not a nums");
        }
    }

    int howMany3(Nums k) {
        return switch (k) {
            case ONE: // violation, 'case' construct must use '{}'s
                MathOperation2 case5 = (a, b) -> // violation, '->' construct must use '{}'s
                        (a + b);
                yield 3;
            case TWO, THREE: // violation, 'case' construct must use '{}'s
                yield 5;

            case FOUR: // violation, 'case' construct must use '{}'s
                yield 9;

            default: // violation, 'default' construct must use '{}'s
                throw new IllegalStateException("Not a Nums");
        };
    }

    /**
     * Braces required in switch expression with switch labled block
     */
    int howMany4(Nums k) {
        return switch (k) {
            case ONE -> {
                yield 4;
            }
            case TWO, THREE -> {
                MathOperation2 case5 = (a, b) -> // violation, '->' construct must use '{}'s
                        (a + b);
                yield 42;
            }
            case FOUR -> {
                yield 99;
            }
            default -> throw new IllegalStateException("Not a Nums");

        };
    }

    /**
     * Braces not allowed in switch expression with switch labeled expression
     */
    int howMany5(Nums k) {
        return switch (k) {
            case ONE -> 1; // braces not allowed, ok
            case TWO, THREE -> 3; // braces not allowed, ok
            case FOUR -> 4; // braces not allowed, ok
            default -> {throw new IllegalStateException("Not a Nums");}
        };
    }

    void howMany6(Nums k) {
        switch (k) {
            case ONE: System.out.println("case two");
            case TWO, THREE: System.out.println("case two");
            case FOUR: System.out.println("case three");
            default: throw new IllegalStateException("Not a nums");
        }
    }

    void howMany7(Nums k) {
        switch (k) {
            case ONE -> System.out.println("case one");
            case TWO, THREE -> System.out.println("case two");
            case FOUR -> System.out.println("case three");
            default -> throw new IllegalStateException("Not a nums");
        }
    }
}

enum Nums {ONE, TWO, THREE, FOUR}

interface MathOperation2 {
    int operation(int a, int b);
}


