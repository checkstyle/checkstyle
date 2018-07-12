package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;
/**
  * This test-input is intended to be checked using following configuration:
  *
  * option = NLOW
  *
  */
public class InputLeftCurlyNlowSwitch {

    public void doStuff() {
        int x = 1;
        switch (x) {
            case 0:
            { // warn
                break;
            }
            case (1
                + 0):
            { // OK, wrapped line
                break;
            }
            case 2: {
                break;
            }
            default
                :
            { // OK, wrapped line
                break;
            }
            case 3:
            case 4:
                x++;
                { // OK, standalone block
                }
                break;
            case 5: {
                    x++;
                }
                x++;
                break;
        }
        switch (x) {
            case
                0:
            { // OK, wrapped line
                break;
            }
            default:
                // do nothing
        }
    }

}
