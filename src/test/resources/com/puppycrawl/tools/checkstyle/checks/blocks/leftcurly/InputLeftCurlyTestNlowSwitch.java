package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;

/*
  * Config:
  * option = NLOW
  */
public class InputLeftCurlyTestNlowSwitch {

    public void doStuff() {
        int x = 1;
        switch (x) {
            case 0:
            { // violation
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
