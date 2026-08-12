/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlyMultiBlock {

    public void method() {
        int x = 0;
        int mode = 0;

        if (mode == 1) {}
        else {}
        // violation 2 lines above ''}' at column 25 should be on the same line as .*/else'
        // violation 2 lines above ''}' at column 15 should be alone on a line'

        if (mode == 1) {
            x = 1;
        } else if (mode == 0) { x = 2;
        } else if (mode == 2) {
            x = 3;
        } else {
            x = 3; }
        // violation above ''}' at column 20 should be alone on a line'

        try {
        } catch (NullPointerException ex) {
        } catch (Exception e) {
        } finally { // do nothing
        }

        // violation 3 lines below ''}' at column 9 should be alone on a line'
        if (mode == 1) {
            x = 3;
        } if (mode == 2) {
            x = 4;
        } else if (mode == 3) {
            x = 1;
        } else if (mode == 4) {
            x = 2;
        }

        if (mode == 3) {
            x++;
        } else {
            x--;
        } if (mode == 5) {
            x = 1;
        }
        // violation 3 lines above '}' at column 9 should be alone on a line'

        try {
            x = 1;
        } catch (Exception e) {
            x = 2;
        } finally { x = 3;
        }

        try {}
        catch (Exception e) {}
        finally {}
        // violation 3 lines above ''}' at column 14 should be on the same line as .*/catch'
        // violation 3 lines above ''}' at column 30 should be on the same line as .*/finally'
        // violation 3 lines above ''}' at column 18 should be alone on a line'

        try {
        }
        catch (Exception e) {
        }
        // violation 3 lines above ''}' at column 9 should be on the same line as .*/catch'

        int k = 1;
        do {} while (k < 0);

        // violation below ''}' at column 17 should have line break before'
        do {k++;} while (k < 0);

        if (x > 0) {
            x++;
        } else if (x < 0) {
        }
    }
}
