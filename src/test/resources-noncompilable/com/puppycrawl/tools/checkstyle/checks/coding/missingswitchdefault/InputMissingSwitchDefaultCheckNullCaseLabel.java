/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCheckNullCaseLabel{
    public void switchWithNullLabelTest(SwitchInput i) {
        Integer counter=0;
        switch (i) {
            case FIRST: counter++;
            case SECOND: counter--;
            case null: counter=counter+2;
        }
    }
    enum SwitchInput {
        FIRST,
        SECOND
    }
}

