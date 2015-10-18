package com.puppycrawl.tools.checkstyle.checks.whitespace;

public class InputSwitchWhitespaceAround {

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
