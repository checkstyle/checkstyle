/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCheckSwitchExpressionsThree {
    public enum Options {
        ONE,
        TWO,
        THREE
    }

    public enum Day {
        MON,
        TUE,
        WED,
        THU,
        FRI,
        SAT,
        SUN,
    }

    public void foo1(Options option) {
        int num = switch (option) { // ok
            case ONE:
                yield 1;
            case TWO:
                yield 2;
            case THREE:
                yield 3;
        };
    }

    public void foo2(Options option) {
        assert Integer.valueOf(1).equals(switch (option) { // ok
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
        });
    }

    public void bar1(Options option) {
        int num;
        num = switch (option) { // ok
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
        };
    }

    public void bar2(Options option) {
        assert 1 < switch (option) { // ok
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
        };
    }

    public void bar3(Options option) {
        int num = 0;
        num += switch (option) { // ok
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
        };
    }

    int usedOnBothSidesOfArithmeticExpression(Day day) {
        return switch (day) { // ok
            case MON, TUE -> 0;
            case WED -> 1;
            default -> 2;
        } * switch (day) { // ok
            case WED, THU -> 3;
            case FRI -> 4;
            default -> 5;
        };
    }

}
