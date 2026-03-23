/*
GoogleRightCurly

*/

package com.puppycrawl.tools.checkstyle.checks.blocks.googlerightcurly;

public class InputGoogleRightCurlyInitializers {

    private static int counter;

    private int value;

    static {
        counter = 0;}
    // violation above ''}' at column 21 should be alone on a line'

    static {
        counter = 1;
    }

    static {}

    {
        value = 0;}
    // violation above ''}' at column 19 should be alone on a line'

    {
        value = 1;}
    // violation above ''}' at column 19 should be alone on a line'

    {
    }
}
