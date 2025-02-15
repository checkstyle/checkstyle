/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT, LAMBDA


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesTestSwitchExpressionNoSingleLine {
    void howMany1(Nums k) {
        switch (k) {
            case ONE: // violation ''case' construct must use '{}'s'
                System.out.println("case two");
                MathOperation2 case5 = (a, b) -> // violation ''->' construct must use '{}'s'
                    (a + b);
            case TWO, THREE: // violation ''case' construct must use '{}'s'
                System.out.println("case two");

            case FOUR: // violation ''case' construct must use '{}'s'
                System.out.println("case three");

            default: // violation ''default' construct must use '{}'s'
                throw new IllegalStateException("Not a nums");
        }
    }

    void howMany2(Nums k) {
        switch (k) { // cannot have more than one statement without block
            case ONE -> // violation ''case' construct must use '{}'s'
                System.out.println("case one");

            case TWO, THREE -> // violation ''case' construct must use '{}'s'
                System.out.println("case two");

            case FOUR -> // violation ''case' construct must use '{}'s'
                System.out.println("case three");

            default -> // violation ''default' construct must use '{}'s'
                throw new IllegalStateException("Not a nums");
        }
    }

    int howMany3(Nums k) {
        return switch (k) {
            case ONE: // violation ''case' construct must use '{}'s'
                MathOperation2 case5 = (a, b) -> // violation ''->' construct must use '{}'s'
                    (a + b);
                yield 3;
            case TWO, THREE: // violation ''case' construct must use '{}'s'
                yield 5;

            case FOUR: // violation ''case' construct must use '{}'s'
                yield 9;

            default: // violation ''default' construct must use '{}'s'
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
                MathOperation2 case5 = (a, b) -> // violation ''->' construct must use '{}'s'
                    (a + b);
                yield 42;
            }
            case FOUR -> {
                yield 99;
            }
            // violation below ''default' construct must use '{}'s'
            default -> throw new IllegalStateException("Not a Nums");

        };
    }

    int howMany5(Nums k) {
        return switch (k) {
            case ONE -> 1; // violation ''case' construct must use '{}'s'
            case TWO, THREE -> 3; // violation ''case' construct must use '{}'s'
            case FOUR -> 4; // violation ''case' construct must use '{}'s'
            default -> {
                throw new IllegalStateException("Not a Nums");
            }
        };
    }
}

enum Nums {ONE, TWO, THREE, FOUR}

interface MathOperation2 {
    int operation(int a, int b);
}


