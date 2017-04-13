package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespacearound;

public class InputWhitespaceAroundSwitch {

    public void switchTest(int k) {
        switch(k) {
        default:
            break;
        }
        switch (k) {
        case 7:
            break;
        }
    }
}
