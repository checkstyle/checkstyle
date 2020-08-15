//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

/* Config:
 *
 * tokens = {LITERAL_WHILE , LITERAL_TRY , LITERAL_CATCH , LITERAL_FINALLY ,
 *  LITERAL_DO , LITERAL_IF , LITERAL_ELSE , LITERAL_FOR , INSTANCE_INIT ,
 *  STATIC_INIT , LITERAL_SWITCH , LITERAL_SYNCHRONIZED , LITERAL_CASE ,
 *  LITERAL_DEFAULT , ARRAY_INIT}
 * BlockOption = statement
 */
public class InputEmptyBlockSwitchExpressions {
    void howMany1(Nums k) {
        switch (k) {
            case ONE:
                System.out.println("case two");
            case TWO, THREE: { // violation

            }

            case FOUR: {
                System.out.println("case three");
            }

            default:
                throw new IllegalStateException("Not a nums");
        }
    }

    void howMany2(Nums k) {
        switch (k) {
            case ONE ->
                    System.out.println("case one");

            case TWO, THREE ->
                    System.out.println("case two");

            case FOUR ->
                    System.out.println("case three");

            default ->
                    throw new IllegalStateException("Not a nums");
        }
    }

    int howMany3(Nums k) {
        return switch (k) {
            case ONE:
                yield 3;
            case TWO, THREE:
                yield 5;

            case FOUR: {
                yield 9;
            }

            default:
                throw new IllegalStateException("Not a Nums");
        };
    }

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


    int howMany5(Nums k) {
        return switch (k) {
            case ONE -> 1;
            case TWO, THREE -> 3;
            case FOUR -> 4;
            default -> {throw new IllegalStateException("Not a Nums");}
        };
    }

    void howMany6(Nums k) {
        switch (k) {
            case ONE:
                System.out.println("case two");
                break;
            case TWO, THREE:
                System.out.println("case two");
                break;
            case FOUR:
                System.out.println("case three");
                break;
            default:
                throw new IllegalStateException("Not a nums");
        }
    }

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
            case TWO, THREE -> {} // ok, empty blocks are allowed for cases

            case FOUR -> {} // ok, empty blocks are allowed for cases

            default -> throw new IllegalStateException("Not a nums");
        }
    }
}

enum Nums {ONE, TWO, THREE, FOUR}
