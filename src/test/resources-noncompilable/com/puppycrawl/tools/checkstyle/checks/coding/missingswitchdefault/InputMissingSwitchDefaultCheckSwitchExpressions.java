/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCheckSwitchExpressions {
    enum Nums {ONE, TWO, THREE}

    int howMany1(Nums k) {
        switch (k) { // violation 'switch without "default" clause'
            case ONE:
            case TWO:
            case THREE:
        }
        return 5;
    }

    int howMany2(Nums k) {
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
        };
    }

    int howMany3(Nums k) {
        return switch (k) {
            case ONE:
                yield 3;
            case TWO:
                yield 5;
            case THREE:
                yield 9;
        };
    }

    int howMany4(Nums k) {
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

    int howMany5(Nums k) {
        return switch (k) {
            case ONE:
                yield 3;
            case TWO:
                yield 5;
            case THREE:
                yield 9;
            default:
                yield 22;
        };
    }

}

