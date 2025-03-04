/*
EmptyBlock
option = (default)statement
tokens = LITERAL_DEFAULT, LITERAL_CASE, LITERAL_SWITCH


*/

//non-compiled with javac: Compilable with Java17
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
enum Nums {ONE, TWO, THREE, FOUR}