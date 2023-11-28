/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultCheckSwitchExpressionsFive{

    public void nestedSwitch1() {
        int i = 1;
        switch (i) {
            case 1:
                i++;
                break;
            case 2:
                i--;
                break;
            default:
                switch (i++) {
                    case 2:
                        i++;
                        break;
                    case 3:
                        i--;
                        break;
                    case null:
                }
        }
    }

    public void nestedSwitch2() {
        int i = 1, j = 2;
        switch (i) {
            case 1:
                switch (j) {
                    case 2:
                        break;
                    case 3:
                        break;
                    case null:
                }
            default:
                break;
        }

        switch (i) { // violation

        }
    }
}
