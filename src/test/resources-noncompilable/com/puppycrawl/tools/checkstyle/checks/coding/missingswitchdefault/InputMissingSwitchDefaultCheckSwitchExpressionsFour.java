/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

class InputMissingSwitchDefaultCheckSwitchExpressionsFour {
    public void switchWithNullTest(SwitchInput i) {
        switch (i) {
            case FIRST -> System.out.println("FIRST");
            case SECOND -> System.out.println("SECOND");
            case null -> System.out.println("NULL");
        }
    }

    enum SwitchInput {
        FIRST,
        SECOND
    }
}
