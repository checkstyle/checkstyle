/*
EmptyBlock
option = (default)statement
tokens = LITERAL_DEFAULT, LITERAL_CASE, LITERAL_SWITCH


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

public class InputEmptyBlockSwitchExpressionsTwo {
    void howMany7(NumsTwo k) {
        switch (k) {
            case ONE -> System.out.println("case one");
            case TWO, THREE -> { System.out.println("case two");}
            case FOUR -> System.out.println("case three");
            default -> throw new IllegalStateException("Not a nums");
        }
    }

    void howMany8(NumsTwo k) {
        switch (k) {
            case ONE -> System.out.println("case two");
            case TWO, THREE -> {} // violation, 'Must have at least one statement'

            case FOUR -> {} // violation, 'Must have at least one statement'

            default -> throw new IllegalStateException("Not a nums");
        }
    }
}

enum NumsTwo {ONE, TWO, THREE, FOUR}
