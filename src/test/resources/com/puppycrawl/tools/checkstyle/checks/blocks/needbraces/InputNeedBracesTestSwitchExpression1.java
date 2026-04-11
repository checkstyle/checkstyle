/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT, LAMBDA


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesTestSwitchExpression1 {
    void howMany1(NumsTwo k) {
        switch (k) {
            case ONE: // violation ''case' construct must use '{}'s'
                System.out.println("case two");
                MathOperationTwo case5 = (a, b) -> // violation ''->' construct must use '{}'s'
                        (a + b);
            case TWO, THREE: // violation ''case' construct must use '{}'s'
                System.out.println("case two");

            case FOUR: // violation ''case' construct must use '{}'s'
                System.out.println("case three");

            default: // violation ''default' construct must use '{}'s'
                throw new IllegalStateException("Not a nums");
        }
    }

    void howMany2(NumsTwo k) {
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
}
