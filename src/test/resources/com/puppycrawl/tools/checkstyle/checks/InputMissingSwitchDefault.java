package com.puppycrawl.tools.checkstyle.checks;

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
        switch (i) {
        case 1: i++; break;
        case 2: i--; break;
        }
    }
}
