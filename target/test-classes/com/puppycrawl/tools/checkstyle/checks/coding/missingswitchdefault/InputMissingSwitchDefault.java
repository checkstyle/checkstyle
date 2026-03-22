/*
MissingSwitchDefault


*/

package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefault {
    public void foo() {
        int i = 1;
        switch (i) {
            case 1: i++; break;
            case 2: i--; break;
            default: return;
        }
    }
}

class bad_test {
    public void foo() {
        int i = 1;
        switch (i) { // violation 'switch without "default" clause'
            case 1: i++; break;
            case 2: i--; break;
        }
    }

    public void nestedSwitch1() {
        int i = 1;
        switch (i) {
            case 1: i++; break;
            case 2: i--; break;
            default:
                switch (i++) { // violation 'switch without "default" clause'
                    case 2: i++; break;
                    case 3: i--; break;
            }
        }
    }

    public void nestedSwitch2() {
        int i = 1, j = 2;
        switch(i) {
            case 1:
                switch (j) { // violation 'switch without "default" clause'
                    case 2: break;
                    case 3: break;
            }
            default: break;
        }

        switch(i) { // violation 'switch without "default" clause'

        }
    }
}
