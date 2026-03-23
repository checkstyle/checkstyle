/*
RightCurlyAloneOrEmpty
tokens = LITERAL_SWITCH, LITERAL_TRY, LITERAL_CATCH, LITERAL_FINALLY, METHOD_DEF

*/
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurlyaloneorempty;

public class InputRightCurlyAloneOrEmptySwitchCaseTwo {
    public static void test1() {
        int mode = 0;
        switch (mode) { case 0: } // violation '}' at column 33 should be alone on a line'
        switch (mode) {}
    }

    public void m() {}

    public void test2() {
        try {

        }
        catch(Exception e) {

        }
        finally {

        }
    }

    public void test3(){
        try {

        }
        catch(Exception e) {} // violation '}' at column 29 should be alone on a line'

        try {

        } catch(Exception e) { // violation '}' at column 9 should be alone on a line'

        }
        finally {} // violation '}' at column 18 should be alone on a line'

        try {

        }
        catch(Exception e) {

        } int a = 10; // violation '}' at column 9 should be alone on a line'

        try {

        } catch(Exception e) { // violation '}' at column 9 should be alone on a line'

        }
    }


}
