/*
EmptyBlock
option = (default)statement
tokens = (default)LITERAL_WHILE,LITERAL_TRY,LITERAL_FINALLY,LITERAL_DO,LITERAL_IF,LITERAL_ELSE,LITERAL_FOR, \
         INSTANCE_INIT,STATIC_INIT,LITERAL_SWITCH,LITERAL_SYNCHRONIZED

*/

package com.puppycrawl.tools.checkstyle.checks.block.emptyblocks;

public class InputEmptyBlockSwitchExpressionsTwo {
    void howMany7(Nums k) {
        switch (k) {
            case ONE -> System.out.println("case one");
            case TWO, THREE -> { System.out.println("case two");}
            case FOUR -> System.out.println("case three");
            default -> throw new IllegalStateException("Not a nums");
        }
    }

    void howMany8(Nums k) {
        switch (k) {
            case ONE -> System.out.println("case two");
            case TWO, THREE -> {} // violation, 'Must have at least one statement'

            case FOUR -> {} // violation, 'Must have at least one statement'

            default -> throw new IllegalStateException("Not a nums");
        }
    }
}