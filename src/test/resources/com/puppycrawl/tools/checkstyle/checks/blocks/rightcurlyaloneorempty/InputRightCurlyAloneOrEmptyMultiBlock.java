/*
RightCurlyAloneOrEmpty
tokens = (default)LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, LITERAL_IF, LITERAL_ELSE

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

public class InputRightCurlyAloneOrEmptyMultiBlock {

    public void method() {
        int x = 0;
        int mode = 0;

        // violation below ''}' at column 25 should be alone on a line'
        if (mode == 1) {}
        else {}
        // violation above ''}' at column 15 should be alone on a line'

        // violation 3 lines below ''}' at column 9 should be alone on a line'
        if (mode == 1) {
            x = 1;
        } else if (mode == 0) { x = 2;
        } else {
            x = 3; }
        // violation 2 lines above ''}' at column 9 should be alone on a line'
        // violation 2 lines above ''}' at column 20 should be alone on a line'

        // violation 2 lines below ''}' at column 9 should be alone on a line'
        try {
        } catch (Exception e) {
        } finally { // do nothing
        }
        // violation 2 lines above ''}' at column 9 should be alone on a line'

        // violation 3 lines below ''}' at column 9 should be alone on a line'
        try {
            x = 1;
        } catch (Exception e) {
            x = 2;
        } finally { x = 3;
        }
        // violation 2 lines above ''}' at column 9 should be alone on a line'

        try {}
        // violation above ''}' at column 14 should be alone on a line'
        catch (Exception e) {}
        // violation above ''}' at column 30 should be alone on a line'
        finally {}
        // violation above ''}' at column 18 should be alone on a line'

        try {
        }
        catch (Exception e) {
        }
    }
}
