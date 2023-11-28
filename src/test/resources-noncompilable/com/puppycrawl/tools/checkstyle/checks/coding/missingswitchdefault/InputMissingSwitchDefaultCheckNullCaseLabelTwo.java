/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCheckNullCaseLabelTwo {
    public void switchWithNullTest(SwitchInput i) {
        int counter=0;
        switch (i) {
            case FIRST -> counter++;
            case SECOND -> counter++;
            case null -> counter++;
        }
    }
    enum SwitchInput {
        FIRST,
        SECOND
    }
}
