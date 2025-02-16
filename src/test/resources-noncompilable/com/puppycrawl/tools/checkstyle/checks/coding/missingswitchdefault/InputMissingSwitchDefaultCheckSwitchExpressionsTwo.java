/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCheckSwitchExpressionsTwo {
    enum Nums {ONE, TWO, THREE}

    int howMany1(Nums k) {
        switch (k) { // violation 'switch without "default" clause'
            case ONE:
                System.out.println("One!");
            case TWO:
                System.out.println("Two!");
            case THREE:
                System.out.println("Three!");
        }
        return 5;
    }

    int howMany2(Nums k) {
        switch (k) { // violation 'switch without "default" clause'
            case ONE -> System.out.println("One!");
            case TWO -> System.out.println("Two!");
            case THREE -> System.out.println("Three!");
        }
        return 5;
    }

    int howMany3(Nums k) {
        int x;
        boolean bool = (switch (k) {
            case ONE -> {
                x = 1;
                yield true;
            }
            case TWO -> {
                x = 2;
                yield true;
            }
            case THREE -> {
                x = 3;
                yield false;
            }
        });
        return 5;
    }

    int howMany4(Nums k) {
        int x;
        boolean bool = (switch (k) {
            case ONE: {
                x = 1;
                yield true;
            }
            case TWO: {
                x = 2;
                yield true;
            }
            case THREE: {
                x = 3;
                yield false;
            }
        });
        return 5;
    }

    int howMany5(Nums k) {
        return switch (k) {
            case ONE -> {
                yield 4;
            }
            case TWO -> {
                yield 42;
            }
            case THREE -> {
                yield 99;
            }
            default -> {
                yield 67;
            }
        };
    }

    int howMany6(Nums k) {
        return switch (k) {
            case ONE: {
                yield 4;
            }
            case TWO: {
                yield 42;
            }
            case THREE: {
                yield 99;
            }
        };
    }

}
