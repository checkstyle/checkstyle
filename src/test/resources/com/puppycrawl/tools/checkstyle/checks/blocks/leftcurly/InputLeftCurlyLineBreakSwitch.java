package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

public class InputLeftCurlyLineBreakSwitch {

    public void doStuff() {
        int x = 1;
        switch (x) {
            case 0:
            {
                break;
            }
            case (1+0):
            {
                break;
            }
            case 2: {
                break;
            }
            default:
            {
                break;
            }
        }
        switch (x) {
            case 0: {
                break;
            }
            default:
                // do nothing
        }
    }
}
