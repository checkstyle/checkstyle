/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

class InputMissingSwitchDefaultCheckSwitchExpressionsFour {
    public void switchWithNullTest(SwitchInput i) {
        switch (i) {
            case FIRST: i++;
            case SECOND:  i++;
            case null: i++;
            case null: i++;
        }
    }
    public void Test(){

        return switch (k) {
            case ONE -> {
                yield 4;
            }
            case TWO -> {
                yield 42;
            }
            case null -> {
                yield 99;
            }
        };
    }

    enum SwitchInput {
        FIRST,
        SECOND
    }
}
